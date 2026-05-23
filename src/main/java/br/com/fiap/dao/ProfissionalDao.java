package br.com.fiap.dao;

import br.com.fiap.conexoes.ConexaoFactory;
import br.com.fiap.entities.Profissional;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProfissionalDao {

    public String inserir(Profissional profissional) throws SQLException, ClassNotFoundException {
        try (Connection conn = new ConexaoFactory().conexao();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO T_FIAP_PROFISSIONAIS (nome, cpf, cro, especializacao) VALUES (?,?,?,?)"
             )) {
            stmt.setString(1, profissional.getNome());
            stmt.setString(2, profissional.getCpf());
            stmt.setString(3, profissional.getCro());
            stmt.setString(4, profissional.getEspecializacao());
            stmt.execute();
        }
        return "Cadastrado com sucesso!";
    }

    public List<Profissional> selecionar() throws SQLException, ClassNotFoundException {
        List<Profissional> lista = new ArrayList<>();
        try (Connection conn = new ConexaoFactory().conexao();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM T_FIAP_PROFISSIONAIS");
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(new Profissional(
                        rs.getString("nome"), rs.getString("cpf"),
                        rs.getString("cro"), rs.getString("especializacao")
                ));
            }
        }
        return lista;
    }

    public Profissional buscarPorCpf(String cpf) throws SQLException, ClassNotFoundException {
        try (Connection conn = new ConexaoFactory().conexao();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT * FROM T_FIAP_PROFISSIONAIS WHERE CPF = ?"
             )) {
            stmt.setString(1, cpf);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Profissional(
                            rs.getString("nome"), rs.getString("cpf"),
                            rs.getString("cro"), rs.getString("especializacao")
                    );
                }
            }
        }
        return null;
    }

    public String atualizar(Profissional profissional) throws SQLException, ClassNotFoundException {
        try (Connection conn = new ConexaoFactory().conexao();
             PreparedStatement stmt = conn.prepareStatement(
                     "UPDATE T_FIAP_PROFISSIONAIS SET NOME = ?, ESPECIALIZACAO = ? WHERE CPF = ?"
             )) {
            stmt.setString(1, profissional.getNome());
            stmt.setString(2, profissional.getEspecializacao());
            stmt.setString(3, profissional.getCpf());
            stmt.executeUpdate();
        }
        return "Profissional atualizado com sucesso!";
    }

    public String deletar(String cpf) throws SQLException, ClassNotFoundException {
        try (Connection conn = new ConexaoFactory().conexao();
             PreparedStatement stmt = conn.prepareStatement(
                     "DELETE FROM T_FIAP_PROFISSIONAIS WHERE CPF = ?"
             )) {
            stmt.setString(1, cpf);
            stmt.execute();
        }
        return "Profissional excluído com sucesso!";
    }
}