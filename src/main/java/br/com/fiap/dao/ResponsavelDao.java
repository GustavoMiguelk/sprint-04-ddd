package br.com.fiap.dao;

import br.com.fiap.conexoes.ConexaoFactory;
import br.com.fiap.entities.Responsavel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ResponsavelDao {

    public String inserir(Responsavel responsavel) throws SQLException, ClassNotFoundException {
        try (Connection conn = new ConexaoFactory().conexao();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO T_FIAP_RESPONSAVEIS (cpf, nome, contato) VALUES (?,?,?)"
             )) {
            stmt.setString(1, responsavel.getCpf());
            stmt.setString(2, responsavel.getNome());
            stmt.setString(3, responsavel.getContato());
            stmt.execute();
        }
        return "Cadastrado com sucesso!";
    }

    public List<Responsavel> selecionar() throws SQLException, ClassNotFoundException {
        List<Responsavel> lista = new ArrayList<>();
        try (Connection conn = new ConexaoFactory().conexao();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM T_FIAP_RESPONSAVEIS");
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(new Responsavel(rs.getString("nome"), rs.getString("cpf"), rs.getString("contato")));
            }
        }
        return lista;
    }

    public Responsavel buscarPorCpf(String cpf) throws SQLException, ClassNotFoundException {
        try (Connection conn = new ConexaoFactory().conexao();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT * FROM T_FIAP_RESPONSAVEIS WHERE CPF = ?"
             )) {
            stmt.setString(1, cpf);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Responsavel(rs.getString("nome"), rs.getString("cpf"), rs.getString("contato"));
                }
            }
        }
        return null;
    }

    public String atualizar(Responsavel responsavel) throws SQLException, ClassNotFoundException {
        try (Connection conn = new ConexaoFactory().conexao();
             PreparedStatement stmt = conn.prepareStatement(
                     "UPDATE T_FIAP_RESPONSAVEIS SET NOME = ?, CONTATO = ? WHERE CPF = ?"
             )) {
            stmt.setString(1, responsavel.getNome());
            stmt.setString(2, responsavel.getContato());
            stmt.setString(3, responsavel.getCpf());
            stmt.executeUpdate();
        }
        return "Responsável atualizado com sucesso!";
    }

    public String deletar(String cpf) throws SQLException, ClassNotFoundException {
        try (Connection conn = new ConexaoFactory().conexao();
             PreparedStatement stmt = conn.prepareStatement(
                     "DELETE FROM T_FIAP_RESPONSAVEIS WHERE CPF = ?"
             )) {
            stmt.setString(1, cpf);
            stmt.execute();
        }
        return "Responsável excluído com sucesso!";
    }
}