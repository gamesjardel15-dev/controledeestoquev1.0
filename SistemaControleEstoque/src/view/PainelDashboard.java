package view;

import java.awt.Font;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class PainelDashboard extends JPanel {

    private static final long serialVersionUID = 1L;

    public PainelDashboard() {
        setLayout(null);

        JLabel lblTitulo = new JLabel("Dashboard do Sistema");
        lblTitulo.setHorizontalAlignment(SwingConstants.CENTER);
        lblTitulo.setFont(new Font("Tahoma", Font.BOLD, 24));
        lblTitulo.setBounds(250, 100, 400, 40);
        add(lblTitulo);

        JLabel lblTexto = new JLabel("Bem-vindo ao Sistema de Controle de Estoque");
        lblTexto.setHorizontalAlignment(SwingConstants.CENTER);
        lblTexto.setFont(new Font("Tahoma", Font.PLAIN, 16));
        lblTexto.setBounds(220, 160, 460, 30);
        add(lblTexto);
    }
}
