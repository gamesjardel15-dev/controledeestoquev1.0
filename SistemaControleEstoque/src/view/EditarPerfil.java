package view;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import dao.UsuarioDAO;
import model.Usuario;

public class EditarPerfil extends JPanel {

    private static final long serialVersionUID = 1L;

    private JTextField txtNome;
    private JTextField txtLogin;
    private JPasswordField txtSenha;
    private JPasswordField txtConfirmarSenha;

    private Usuario usuarioLogado;

    public EditarPerfil() {
        setLayout(null);

        JLabel lblTitulo = new JLabel("Editar Perfil");
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

        JLabel lblSenha = new JLabel("Nova Senha");
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

        JButton btnSalvar = new JButton("Salvar Alterações");
        btnSalvar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                salvarPerfil();
            }
        });
        btnSalvar.setBounds(100, 330, 180, 35);
        add(btnSalvar);
    }

    public EditarPerfil(Usuario usuarioLogado) {
        this();
        this.usuarioLogado = usuarioLogado;
        carregarDados();
    }

    private void carregarDados() {

        if (usuarioLogado != null) {
            txtNome.setText(usuarioLogado.getNome());
            txtLogin.setText(usuarioLogado.getLogin());
        }
    }

    private void salvarPerfil() {

        if (usuarioLogado == null) {
            JOptionPane.showMessageDialog(this, "Usuário logado não identificado.");
            return;
        }

        String nome = txtNome.getText().trim();
        String login = txtLogin.getText().trim();
        String senha = new String(txtSenha.getPassword()).trim();
        String confirmarSenha = new String(txtConfirmarSenha.getPassword()).trim();

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

        boolean alterarSenha = !senha.isEmpty() || !confirmarSenha.isEmpty();

        if (alterarSenha) {

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
        }

        Usuario usuario = new Usuario();
        usuario.setIdUsuario(usuarioLogado.getIdUsuario());
        usuario.setNome(nome);
        usuario.setLogin(login);
        usuario.setSenha(senha);

        UsuarioDAO usuarioDAO = new UsuarioDAO();
        boolean atualizado = usuarioDAO.atualizarPerfil(usuario, alterarSenha);

        if (atualizado) {
            usuarioLogado.setNome(nome);
            usuarioLogado.setLogin(login);

            JOptionPane.showMessageDialog(this, "Perfil atualizado com sucesso.");

            txtSenha.setText("");
            txtConfirmarSenha.setText("");

        } else {
            JOptionPane.showMessageDialog(this, "Não foi possível atualizar o perfil.");
        }
    }
}
