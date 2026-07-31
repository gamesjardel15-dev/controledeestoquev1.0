package model;

public class Usuario {

    private int idUsuario;
    private String nome;
    private String login;
    private String senha;
    private String perfil;
    private boolean ativo;

    public Usuario() {

    }

    public Usuario(String nome, String login, String senha, String perfil) {
        this.nome = nome;
        this.login = login;
        this.senha = senha;
        this.perfil = perfil;
    }

    public Usuario(int idUsuario, String nome, String login, String perfil, boolean ativo) {
        this.idUsuario = idUsuario;
        this.nome = nome;
        this.login = login;
        this.perfil = perfil;
        this.ativo = ativo;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }


    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }


    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }


    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}
