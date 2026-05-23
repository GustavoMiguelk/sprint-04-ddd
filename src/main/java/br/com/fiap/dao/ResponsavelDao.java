package br.com.fiap.dao;

import br.com.fiap.conexoes.ConexaoFactory;
import br.com.fiap.entities.Responsavel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ResponsavelDao {

    Connection minhaConexao;

    public ResponsavelDao() throws SQLException, ClassNotFoundException {
        this.minhaConexao = new ConexaoFactory().conexao();
    }

    public String inserir(Responsavel responsavel) throws SQLException {
        PreparedStatement stmt = minhaConexao.prepareStatement(
                "INSERT INTO T_FIAP_RESPONSAVEIS (cpf, nome, contato) VALUES (?,?,?)"
        );

        stmt.setString(1, responsavel.getCpf());
        stmt.setString(2, responsavel.getNome());
        stmt.setString(3, responsavel.getContato());

        stmt.execute();
        stmt.close();

        return "Cadastrado com sucesso!";
    }

    public List<Responsavel> selecionar() throws SQLException {
        List<Responsavel> lista = new ArrayList<>();
        PreparedStatement stmt = minhaConexao.prepareStatement("SELECT * FROM T_FIAP_RESPONSAVEIS");
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            lista.add(new Responsavel(rs.getString("nome"), rs.getString("cpf"), rs.getString("contato")));
        }
        rs.close();
        stmt.close();
        return lista;
    }

    public Responsavel buscarPorCpf(String cpf) throws SQLException {
        PreparedStatement stmt = minhaConexao.prepareStatement(
                "SELECT * FROM T_FIAP_RESPONSAVEIS WHERE CPF = ?"
        );
        stmt.setString(1, cpf);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            Responsavel r = new Responsavel(rs.getString("nome"), rs.getString("cpf"), rs.getString("contato"));
            rs.close();
            stmt.close();
            return r;
        }
        rs.close();
        stmt.close();
        return null;
    }

    public String atualizar(Responsavel responsavel) throws SQLException {
        PreparedStatement stmt = minhaConexao.prepareStatement(
                "UPDATE T_FIAP_RESPONSAVEIS SET NOME = ?, CONTATO = ? WHERE CPF = ?"
        );
        stmt.setString(1, responsavel.getNome());
        stmt.setString(2, responsavel.getContato());
        stmt.setString(3, responsavel.getCpf());
        stmt.executeUpdate();
        stmt.close();
        return "Responsável atualizado com sucesso!";
    }

    public String deletar(String cpf) throws SQLException {
        PreparedStatement stmt = minhaConexao.prepareStatement(
                "DELETE FROM T_FIAP_RESPONSAVEIS WHERE CPF = ?"
        );
        stmt.setString(1, cpf);
        stmt.execute();
        stmt.close();
        return "Responsável excluído com sucesso!";
    }
}
