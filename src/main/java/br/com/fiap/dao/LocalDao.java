package br.com.fiap.dao;

import br.com.fiap.conexoes.ConexaoFactory;
import br.com.fiap.entities.Local;

import java.sql.*;

import java.util.ArrayList;
import java.util.List;

public class LocalDao {

    Connection minhaConexao;

    public LocalDao() throws SQLException, ClassNotFoundException {
        this.minhaConexao = new ConexaoFactory().conexao();
    }

    public String inserir(Local local) throws SQLException {
        PreparedStatement stmt = minhaConexao.prepareStatement(
                "INSERT INTO T_FIAP_LOCAIS (cnpj_inst, nome, endereco, tipo) VALUES (?,?,?,?)",
                new String[]{"ID_LOCAL"} // retorna o ID gerado pelo banco
        );

        stmt.setString(1, local.getCnpjInstituicao());
        stmt.setString(2, local.getNome());
        stmt.setString(3, local.getEndereco());
        stmt.setString(4, local.getTipo());

        stmt.execute();

        // Seta o ID gerado no objeto para uso posterior nas consultas
        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()) {
            local.setIdLocal(rs.getInt(1));
        }

        stmt.close();
        return "Cadastrado com sucesso!";
    }

    public List<Local> selecionar() throws SQLException {
        List<Local> lista = new ArrayList<>();
        PreparedStatement stmt = minhaConexao.prepareStatement("SELECT * FROM T_FIAP_LOCAIS");
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Local l = new Local(
                    rs.getString("cnpj_inst"), rs.getString("nome"),
                    rs.getString("endereco"), rs.getString("tipo")
            );
            l.setIdLocal(rs.getInt("id_local"));
            lista.add(l);
        }
        rs.close();
        stmt.close();
        return lista;
    }

    public Local buscarPorId(int idLocal) throws SQLException {
        PreparedStatement stmt = minhaConexao.prepareStatement(
                "SELECT * FROM T_FIAP_LOCAIS WHERE ID_LOCAL = ?"
        );
        stmt.setInt(1, idLocal);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            Local l = new Local(
                    rs.getString("cnpj_inst"), rs.getString("nome"),
                    rs.getString("endereco"), rs.getString("tipo")
            );
            l.setIdLocal(rs.getInt("id_local"));
            rs.close();
            stmt.close();
            return l;
        }
        rs.close();
        stmt.close();
        return null;
    }

    public String atualizar(Local local) throws SQLException {
        PreparedStatement stmt = minhaConexao.prepareStatement(
                "UPDATE T_FIAP_LOCAIS SET NOME = ?, ENDERECO = ?, TIPO = ? WHERE ID_LOCAL = ?"
        );
        stmt.setString(1, local.getNome());
        stmt.setString(2, local.getEndereco());
        stmt.setString(3, local.getTipo());
        stmt.setInt(4,    local.getIdLocal());
        stmt.executeUpdate();
        stmt.close();
        return "Local atualizado com sucesso!";
    }

    public String deletar(int idLocal) throws SQLException {
        PreparedStatement stmt = minhaConexao.prepareStatement(
                "DELETE FROM T_FIAP_LOCAIS WHERE ID_LOCAL = ?"
        );
        stmt.setInt(1, idLocal);
        stmt.execute();
        stmt.close();
        return "Local excluído com sucesso!";
    }
}
