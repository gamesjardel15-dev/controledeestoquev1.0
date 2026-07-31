package view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import dao.UsuarioDAO;
import model.Usuario;

public class NovoUsuario extends JPanel {

    private static final long serialVersionUID = 1L;

    private JTextField txtNome;
    private JTextField txtLogin;
    private JPasswordField txtSenha;
    private JPasswordField txtConfirmarSenha;
    private JComboBox<String> comboPerfil;

    public NovoUsuario() {
        setLayout(null);

        JLabel lblTitulo = new JLabel("Cadastrar Usuário");
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 22));
        lblTitulo.setBounds(240, 20, 350, 30);
        add(lblTitulo);

        JLabel lblNome = new JLabel("Nome");
        lblNome.setBounds(100, 90, 120, 20);
        add(lblNome);

        txtNome = new JTextField();
        txtNome.setBounds(100, 115, 350, 30);
        add(txtNome);

        JLabel lblLogin = new JLabel("Login");
        lblLogin.setBounds(100, 165, 120, 20);
        add(lblLogin);

        txtLogin = new JTextField();
        txtLogin.setBounds(100, 190, 250, 30);
        add(txtLogin);

        JLabel lblPerfil = new JLabel("Perfil");
        lblPerfil.setBounds(380, 165, 120, 20);
        add(lblPerfil);

        comboPerfil = new JComboBox<String>();
        comboPerfil.addItem("OPERADOR");
        comboPerfil.addItem("ADMIN");
        comboPerfil.setBounds(380, 190, 180, 30);
        add(comboPerfil);

        JLabel lblSenha = new JLabel("Senha");
        lblSenha.setBounds(100, 240, 120, 20);
        add(lblSenha);

        txtSenha = new JPasswordField();
        txtSenha.setBounds(100, 265, 250, 30);
        add(txtSenha);

        JLabel lblConfirmarSenha = new JLabel("Confirmar Senha");
        lblConfirmarSenha.setBounds(380, 240, 150, 20);
        add(lblConfirmarSenha);

        txtConfirmarSenha = new JPasswordField();
        txtConfirmarSenha.setBounds(380, 265, 250, 30);
        add(txtConfirmarSenha);

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                salvarUsuario();
            }
        });
        btnSalvar.setBounds(100, 330, 120, 35);
        add(btnSalvar);

        JButton btnLimpar = new JButton("Limpar");
        btnLimpar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                limparCampos();
            }
        });
        btnLimpar.setBounds(240, 330, 120, 35);
        add(btnLimpar);
    }

    private void salvarUsuario() {

        String nome = txtNome.getText().trim();
        String login = txtLogin.getText().trim();
        String senha = new String(txtSenha.getPassword()).trim();
        String confirmarSenha = new String(txtConfirmarSenha.getPassword()).trim();
        String perfil = comboPerfil.getSelectedItem().toString();

        if (nome.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o nome.");
            txtNome.requestFocus();
            return;
        }

        if (login.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe o login.");
            txtLogin.requestFocus();
            return;
        }

        if (senha.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Informe a senha.");
            txtSenha.requestFocus();
            return;
        }

        if (senha.length() < 6) {
            JOptionPane.showMessageDialog(this, "A senha deve ter pelo menos 6 caracteres.");
            txtSenha.requestFocus();
            return;
        }

        if (!senha.equals(confirmarSenha)) {
            JOptionPane.showMessageDialog(this, "As senhas não conferem.");
            txtConfirmarSenha.requestFocus();
            return;
        }

        Usuario usuario = new Usuario(nome, login, senha, perfil);

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        boolean cadastrado = usuarioDAO.cadastrar(usuario);

        if (cadastrado) {
            JOptionPane.showMessageDialog(this, "Usuário cadastrado com sucesso.");
            limparCampos();
        } else {
            JOptionPane.showMessageDialog(
                    this,
                    "Não foi possível cadastrar o usuário.\nVerifique se o login já existe.",
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void limparCampos() {
        txtNome.setText("");
        txtLogin.setText("");
        txtSenha.setText("");
        txtConfirmarSenha.setText("");
        comboPerfil.setSelectedIndex(0);
        txtNome.requestFocus();
    }
}
