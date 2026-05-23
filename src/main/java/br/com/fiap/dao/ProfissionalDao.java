package br.com.fiap.dao;

import br.com.fiap.conexoes.ConexaoFactory;
import br.com.fiap.entities.Profissional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ProfissionalDao {

    Connection minhaConexao;

    public ProfissionalDao() throws SQLException, ClassNotFoundException {
        this.minhaConexao = new ConexaoFactory().conexao();
    }

    public String inserir(Profissional profissional) throws SQLException {
        PreparedStatement stmt = minhaConexao.prepareStatement(
                "INSERT INTO T_FIAP_PROFISSIONAIS (nome, cpf, cro, especializacao) VALUES (?,?,?,?)"
        );

        stmt.setString(1, profissional.getNome());
        stmt.setString(2, profissional.getCpf());
        stmt.setString(3, profissional.getCro());
        stmt.setString(4, profissional.getEspecializacao());

        stmt.execute();
        stmt.close();

        return "Cadastrado com sucesso!";
    }

    public List<Profissional> selecionar() throws SQLException {
        List<Profissional> lista = new ArrayList<>();
        PreparedStatement stmt = minhaConexao.prepareStatement("SELECT * FROM T_FIAP_PROFISSIONAIS");
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            lista.add(new Profissional(
                    rs.getString("nome"), rs.getString("cpf"),
                    rs.getString("cro"), rs.getString("especializacao")
            ));
        }
        rs.close();
        stmt.close();
        return lista;
    }

    public Profissional buscarPorCpf(String cpf) throws SQLException {
        PreparedStatement stmt = minhaConexao.prepareStatement(
                "SELECT * FROM T_FIAP_PROFISSIONAIS WHERE CPF = ?"
        );
        stmt.setString(1, cpf);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            Profissional p = new Profissional(
                    rs.getString("nome"), rs.getString("cpf"),
                    rs.getString("cro"), rs.getString("especializacao")
            );
            rs.close();
            stmt.close();
            return p;
        }
        rs.close();
        stmt.close();
        return null;
    }

    public String atualizar(Profissional profissional) throws SQLException {
        PreparedStatement stmt = minhaConexao.prepareStatement(
                "UPDATE T_FIAP_PROFISSIONAIS SET NOME = ?, ESPECIALIZACAO = ? WHERE CPF = ?"
        );
        stmt.setString(1, profissional.getNome());
        stmt.setString(2, profissional.getEspecializacao());
        stmt.setString(3, profissional.getCpf());
        stmt.executeUpdate();
        stmt.close();
        return "Profissional atualizado com sucesso!";
    }

    public String deletar(String cpf) throws SQLException {
        PreparedStatement stmt = minhaConexao.prepareStatement(
                "DELETE FROM T_FIAP_PROFISSIONAIS WHERE CPF = ?"
        );
        stmt.setString(1, cpf);
        stmt.execute();
        stmt.close();
        return "Profissional excluído com sucesso!";
    }
}
