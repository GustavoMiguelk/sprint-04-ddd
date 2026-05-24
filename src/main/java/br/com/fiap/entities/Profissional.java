package br.com.fiap.entities;

public class Profissional {

    private String nome;
    private String cpf;
    private String cro;
    private String especializacao;
    private Agenda agenda;
    private String senha;

    public Profissional(String nome, String cpf, String cro, String especializacao, String senha) {
        this.nome = nome;
        this.cpf = cpf;
        this.cro = cro;
        this.especializacao = especializacao;
        this.senha = senha;
    }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public String getCro() { return cro; }
    public void setCro(String cro) { this.cro = cro; }

    public String getEspecializacao() { return especializacao; }
    public void setEspecializacao(String especializacao) { this.especializacao = especializacao; }

    public Agenda getAgenda() { return agenda; }
    public void setAgenda(Agenda agenda) { this.agenda = agenda; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
}
