package view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;

import dao.UsuarioDAO;
import model.Usuario;

public class ListarUsuarios extends JPanel {

    private static final long serialVersionUID = 1L;

    private JTextField txtPesquisa;
    private JTable tabelaUsuarios;
    private DefaultTableModel modeloTabela;

    public ListarUsuarios() {
        setLayout(null);

        JLabel lblTitulo = new JLabel("Listar Usuários");
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 22));
        lblTitulo.setBounds(240, 20, 350, 30);
        add(lblTitulo);

        JLabel lblPesquisar = new JLabel("Pesquisar Usuário");
        lblPesquisar.setBounds(50, 80, 150, 20);
        add(lblPesquisar);

        txtPesquisa = new JTextField();
        txtPesquisa.setBounds(50, 105, 330, 30);
        add(txtPesquisa);

        JButton btnPesquisar = new JButton("Pesquisar");
        btnPesquisar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pesquisarUsuarios();
            }
        });
        btnPesquisar.setBounds(400, 105, 120, 30);
        add(btnPesquisar);

        JButton btnAtualizar = new JButton("Atualizar");
        btnAtualizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                carregarUsuarios();
            }
        });
        btnAtualizar.setBounds(540, 105, 120, 30);
        add(btnAtualizar);

        JButton btnExcluir = new JButton("Excluir");
        btnExcluir.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                excluirUsuario();
            }
        });
        btnExcluir.setBounds(680, 105, 120, 30);
        add(btnExcluir);

        JScrollPane scrollTabelaUsuarios = new JScrollPane();
        scrollTabelaUsuarios.setBounds(50, 160, 780, 330);
        add(scrollTabelaUsuarios);

        tabelaUsuarios = new JTable();
        tabelaUsuarios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tabelaUsuarios.getTableHeader().setReorderingAllowed(false);
        scrollTabelaUsuarios.setViewportView(tabelaUsuarios);

        configurarTabela();
        carregarUsuarios();
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
        modeloTabela.addColumn("Nome");
        modeloTabela.addColumn("Login");
        modeloTabela.addColumn("Perfil");
        modeloTabela.addColumn("Status");

        tabelaUsuarios.setModel(modeloTabela);

        tabelaUsuarios.getColumnModel().getColumn(0).setPreferredWidth(50);
        tabelaUsuarios.getColumnModel().getColumn(1).setPreferredWidth(220);
        tabelaUsuarios.getColumnModel().getColumn(2).setPreferredWidth(160);
        tabelaUsuarios.getColumnModel().getColumn(3).setPreferredWidth(120);
        tabelaUsuarios.getColumnModel().getColumn(4).setPreferredWidth(100);
    }

    private void carregarUsuarios() {

        modeloTabela.setRowCount(0);

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        List<Usuario> usuarios = usuarioDAO.listar();

        preencherTabela(usuarios);

        txtPesquisa.setText("");
        txtPesquisa.requestFocus();
    }

    private void pesquisarUsuarios() {

        String pesquisa = txtPesquisa.getText().trim();

        if (pesquisa.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Digite o nome ou login para pesquisar.");
            txtPesquisa.requestFocus();
            return;
        }

        modeloTabela.setRowCount(0);

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        List<Usuario> usuarios = usuarioDAO.pesquisar(pesquisa);

        preencherTabela(usuarios);

        if (usuarios.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nenhum usuário encontrado.");
        }
    }

    private void preencherTabela(List<Usuario> usuarios) {

        for (Usuario usuario : usuarios) {

            String status = usuario.isAtivo() ? "Ativo" : "Inativo";

            modeloTabela.addRow(new Object[] {
                    usuario.getIdUsuario(),
                    usuario.getNome(),
                    usuario.getLogin(),
                    usuario.getPerfil(),
                    status
            });
        }
    }

    private void excluirUsuario() {

        int linhaSelecionada = tabelaUsuarios.getSelectedRow();

        if (linhaSelecionada == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um usuário para excluir.");
            return;
        }

        int idUsuario = Integer.parseInt(modeloTabela.getValueAt(linhaSelecionada, 0).toString());
        String nomeUsuario = modeloTabela.getValueAt(linhaSelecionada, 1).toString();

        int resposta = JOptionPane.showConfirmDialog(
                this,
                "Deseja realmente excluir o usuário " + nomeUsuario + "?",
                "Confirmar exclusão",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (resposta == JOptionPane.YES_OPTION) {

            UsuarioDAO usuarioDAO = new UsuarioDAO();
            boolean excluido = usuarioDAO.excluirLogico(idUsuario);

            if (excluido) {
                JOptionPane.showMessageDialog(this, "Usuário excluído com sucesso.");
                carregarUsuarios();
            } else {
                JOptionPane.showMessageDialog(this, "Não foi possível excluir o usuário.");
            }
        }
    }
}
