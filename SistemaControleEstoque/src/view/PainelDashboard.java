package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.ListSelectionModel;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import dao.DashboardDAO;
import model.Produto;
import util.Formatador;

public class PainelDashboard extends JPanel {

    private static final long serialVersionUID = 1L;

    private JLabel lblTotalProdutos;
    private JLabel lblTotalClientes;
    private JLabel lblEstoqueBaixo;
    private JLabel lblValorEstoque;

    private JTable tabelaEstoqueBaixo;
    private DefaultTableModel modeloTabela;

    public PainelDashboard() {
        setLayout(null);

        JLabel lblTitulo = new JLabel("Dashboard do Sistema");
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 24));
        lblTitulo.setBounds(250, 20, 400, 35);
        add(lblTitulo);

        lblTotalProdutos = new JLabel("0");
        criarCard("Produtos Ativos", lblTotalProdutos, 50, 80, 190, 100);

        lblTotalClientes = new JLabel("0");
        criarCard("Clientes Ativos", lblTotalClientes, 260, 80, 190, 100);

        lblEstoqueBaixo = new JLabel("0");
        criarCard("Estoque Baixo", lblEstoqueBaixo, 470, 80, 190, 100);

        lblValorEstoque = new JLabel("R$ 0,00");
        criarCard("Valor do Estoque", lblValorEstoque, 680, 80, 220, 100);

        JLabel lblTabela = new JLabel("Produtos com Estoque Baixo");
        lblTabela.setFont(new Font("Tahoma", Font.BOLD, 16));
        lblTabela.setBounds(50, 210, 300, 25);
        add(lblTabela);

        JScrollPane scrollTabela = new JScrollPane();
        scrollTabela.setBounds(50, 245, 850, 260);
        add(scrollTabela);

        tabelaEstoqueBaixo = new JTable();
        tabelaEstoqueBaixo.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaEstoqueBaixo.getTableHeader().setReorderingAllowed(false);
        scrollTabela.setViewportView(tabelaEstoqueBaixo);

        JButton btnAtualizar = new JButton("Atualizar Dashboard");
        btnAtualizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                carregarDashboard();
            }
        });
        btnAtualizar.setBounds(50, 525, 180, 35);
        add(btnAtualizar);

        configurarTabela();
        carregarDashboard();
    }

    private void criarCard(String titulo, JLabel lblValor, int x, int y, int largura, int altura) {

        JPanel card = new JPanel();
        card.setLayout(null);
        card.setBorder(new LineBorder(Color.LIGHT_GRAY));
        card.setBounds(x, y, largura, altura);
        add(card);

        JLabel lblTituloCard = new JLabel(titulo);
        lblTituloCard.setHorizontalAlignment(SwingConstants.CENTER);
        lblTituloCard.setFont(new Font("Tahoma", Font.PLAIN, 14));
        lblTituloCard.setBounds(10, 10, largura - 20, 25);
        card.add(lblTituloCard);

        lblValor.setHorizontalAlignment(SwingConstants.CENTER);
        lblValor.setFont(new Font("Tahoma", Font.BOLD, 22));
        lblValor.setBounds(10, 45, largura - 20, 35);
        card.add(lblValor);
    }

    private void configurarTabela() {

        modeloTabela = new DefaultTableModel() {

            private static final long serialVersionUID = 1L;

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        modeloTabela.addColumn("ID");
        modeloTabela.addColumn("Produto");
        modeloTabela.addColumn("Estoque Atual");
        modeloTabela.addColumn("Estoque Mínimo");
        modeloTabela.addColumn("Valor Venda");
        modeloTabela.addColumn("Situação");

        tabelaEstoqueBaixo.setModel(modeloTabela);

        tabelaEstoqueBaixo.getColumnModel().getColumn(0).setPreferredWidth(50);
        tabelaEstoqueBaixo.getColumnModel().getColumn(1).setPreferredWidth(250);
        tabelaEstoqueBaixo.getColumnModel().getColumn(2).setPreferredWidth(110);
        tabelaEstoqueBaixo.getColumnModel().getColumn(3).setPreferredWidth(110);
        tabelaEstoqueBaixo.getColumnModel().getColumn(4).setPreferredWidth(120);
        tabelaEstoqueBaixo.getColumnModel().getColumn(5).setPreferredWidth(130);
    }

    private void carregarDashboard() {

        DashboardDAO dashboardDAO = new DashboardDAO();

        int totalProdutos = dashboardDAO.contarProdutosAtivos();
        int totalClientes = dashboardDAO.contarClientesAtivos();
        int totalEstoqueBaixo = dashboardDAO.contarProdutosEstoqueBaixo();

        lblTotalProdutos.setText(String.valueOf(totalProdutos));
        lblTotalClientes.setText(String.valueOf(totalClientes));
        lblEstoqueBaixo.setText(String.valueOf(totalEstoqueBaixo));
        lblValorEstoque.setText(Formatador.moeda(dashboardDAO.calcularValorTotalEstoque()));

        carregarTabelaEstoqueBaixo(dashboardDAO.listarProdutosEstoqueBaixo());
    }

    private void carregarTabelaEstoqueBaixo(List<Produto> produtos) {

        modeloTabela.setRowCount(0);

        for (Produto produto : produtos) {

            modeloTabela.addRow(new Object[] {
                    produto.getIdProduto(),
                    produto.getNome(),
                    produto.getQuantidadeEstoque(),
                    produto.getEstoqueMinimo(),
                    Formatador.moeda(produto.getValorVenda()),
                    "Estoque Baixo"
            });
        }
    }
}
