package br.com.fiap.dao;

import br.com.fiap.conexoes.ConexaoFactory;
import br.com.fiap.entities.Instituicao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class InstituicaoDao {

    Connection minhaConexao;

    public InstituicaoDao() throws SQLException, ClassNotFoundException {
        this.minhaConexao = new ConexaoFactory().conexao();
    }

    public String inserir(Instituicao instituicao) throws SQLException {
        PreparedStatement stmt = minhaConexao.prepareStatement(
                "INSERT INTO T_FIAP_INSTITUICOES (nome, cnpj) VALUES (?,?)"
        );

        stmt.setString(1, instituicao.getNome());
        stmt.setString(2, instituicao.getCnpj());

        stmt.execute();
        stmt.close();

        return "Cadastrado com sucesso!";
    }

    public List<Instituicao> selecionar() throws SQLException {
        List<Instituicao> lista = new ArrayList<>();
        PreparedStatement stmt = minhaConexao.prepareStatement("SELECT * FROM T_FIAP_INSTITUICOES");
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            lista.add(new Instituicao(rs.getString("nome"), rs.getString("cnpj")));
        }
        rs.close();
        stmt.close();
        return lista;
    }

    public Instituicao buscarPorCnpj(String cnpj) throws SQLException {
        PreparedStatement stmt = minhaConexao.prepareStatement(
                "SELECT * FROM T_FIAP_INSTITUICOES WHERE CNPJ = ?"
        );
        stmt.setString(1, cnpj);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            Instituicao i = new Instituicao(rs.getString("nome"), rs.getString("cnpj"));
            rs.close();
            stmt.close();
            return i;
        }
        rs.close();
        stmt.close();
        return null;
    }

    public String atualizar(Instituicao instituicao) throws SQLException {
        PreparedStatement stmt = minhaConexao.prepareStatement(
                "UPDATE T_FIAP_INSTITUICOES SET NOME = ? WHERE CNPJ = ?"
        );
        stmt.setString(1, instituicao.getNome());
        stmt.setString(2, instituicao.getCnpj());
        stmt.executeUpdate();
        stmt.close();
        return "Instituição atualizada com sucesso!";
    }

    public String deletar(String cnpj) throws SQLException {
        PreparedStatement stmt = minhaConexao.prepareStatement(
                "DELETE FROM T_FIAP_INSTITUICOES WHERE CNPJ = ?"
        );
        stmt.setString(1, cnpj);
        stmt.execute();
        stmt.close();
        return "Instituição excluída com sucesso!";
    }
}
