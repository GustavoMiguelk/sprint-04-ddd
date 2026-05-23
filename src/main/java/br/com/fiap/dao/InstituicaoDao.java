package br.com.fiap.dao;

import br.com.fiap.conexoes.ConexaoFactory;
import br.com.fiap.entities.Instituicao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InstituicaoDao {

    public String inserir(Instituicao instituicao) throws SQLException, ClassNotFoundException {
        try (Connection conn = new ConexaoFactory().conexao();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO T_FIAP_INSTITUICOES (nome, cnpj) VALUES (?,?)"
             )) {
            stmt.setString(1, instituicao.getNome());
            stmt.setString(2, instituicao.getCnpj());
            stmt.execute();
        }
        return "Cadastrado com sucesso!";
    }

    public List<Instituicao> selecionar() throws SQLException, ClassNotFoundException {
        List<Instituicao> lista = new ArrayList<>();
        try (Connection conn = new ConexaoFactory().conexao();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM T_FIAP_INSTITUICOES");
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(new Instituicao(rs.getString("nome"), rs.getString("cnpj")));
            }
        }
        return lista;
    }

    public Instituicao buscarPorCnpj(String cnpj) throws SQLException, ClassNotFoundException {
        try (Connection conn = new ConexaoFactory().conexao();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT * FROM T_FIAP_INSTITUICOES WHERE CNPJ = ?"
             )) {
            stmt.setString(1, cnpj);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Instituicao(rs.getString("nome"), rs.getString("cnpj"));
                }
            }
        }
        return null;
    }

    public String atualizar(Instituicao instituicao) throws SQLException, ClassNotFoundException {
        try (Connection conn = new ConexaoFactory().conexao();
             PreparedStatement stmt = conn.prepareStatement(
                     "UPDATE T_FIAP_INSTITUICOES SET NOME = ? WHERE CNPJ = ?"
             )) {
            stmt.setString(1, instituicao.getNome());
            stmt.setString(2, instituicao.getCnpj());
            stmt.executeUpdate();
        }
        return "Instituição atualizada com sucesso!";
    }

    public String deletar(String cnpj) throws SQLException, ClassNotFoundException {
        try (Connection conn = new ConexaoFactory().conexao();
             PreparedStatement stmt = conn.prepareStatement(
                     "DELETE FROM T_FIAP_INSTITUICOES WHERE CNPJ = ?"
             )) {
            stmt.setString(1, cnpj);
            stmt.execute();
        }
        return "Instituição excluída com sucesso!";
    }
}