package br.com.fiap.entities;

public class Programa {

    private int idPrograma; // setado manualmente na Main (1 ou 2)
    private String nome;
    private String descricao;

    public Programa(String nome, String descricao) {
        this.nome = nome;
        this.descricao = descricao;
    }

    public int getIdPrograma() { return idPrograma; }
    public void setIdPrograma(int idPrograma) { this.idPrograma = idPrograma; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }
}
