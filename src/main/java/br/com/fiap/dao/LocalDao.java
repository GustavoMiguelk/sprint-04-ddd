package br.com.fiap.dao;

import br.com.fiap.conexoes.ConexaoFactory;
import br.com.fiap.entities.Local;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LocalDao {

    public String inserir(Local local) throws SQLException, ClassNotFoundException {
        try (Connection conn = new ConexaoFactory().conexao();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO T_FIAP_LOCAIS (cnpj_inst, nome, endereco, tipo) VALUES (?,?,?,?)",
                     new String[]{"ID_LOCAL"}
             )) {
            stmt.setString(1, local.getCnpjInstituicao());
            stmt.setString(2, local.getNome());
            stmt.setString(3, local.getEndereco());
            stmt.setString(4, local.getTipo());
            stmt.execute();
            try (ResultSet rs = stmt.getGeneratedKeys()) {
                if (rs.next()) {
                    local.setIdLocal(rs.getInt(1));
                }
            }
        }
        return "Cadastrado com sucesso!";
    }

    public List<Local> selecionar() throws SQLException, ClassNotFoundException {
        List<Local> lista = new ArrayList<>();
        try (Connection conn = new ConexaoFactory().conexao();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM T_FIAP_LOCAIS");
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Local l = new Local(
                        rs.getString("cnpj_inst"), rs.getString("nome"),
                        rs.getString("endereco"), rs.getString("tipo")
                );
                l.setIdLocal(rs.getInt("id_local"));
                lista.add(l);
            }
        }
        return lista;
    }

    public Local buscarPorId(int idLocal) throws SQLException, ClassNotFoundException {
        try (Connection conn = new ConexaoFactory().conexao();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT * FROM T_FIAP_LOCAIS WHERE ID_LOCAL = ?"
             )) {
            stmt.setInt(1, idLocal);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Local l = new Local(
                            rs.getString("cnpj_inst"), rs.getString("nome"),
                            rs.getString("endereco"), rs.getString("tipo")
                    );
                    l.setIdLocal(rs.getInt("id_local"));
                    return l;
                }
            }
        }
        return null;
    }

    public String atualizar(Local local) throws SQLException, ClassNotFoundException {
        try (Connection conn = new ConexaoFactory().conexao();
             PreparedStatement stmt = conn.prepareStatement(
                     "UPDATE T_FIAP_LOCAIS SET NOME = ?, ENDERECO = ?, TIPO = ? WHERE ID_LOCAL = ?"
             )) {
            stmt.setString(1, local.getNome());
            stmt.setString(2, local.getEndereco());
            stmt.setString(3, local.getTipo());
            stmt.setInt(4,    local.getIdLocal());
            stmt.executeUpdate();
        }
        return "Local atualizado com sucesso!";
    }

    public String deletar(int idLocal) throws SQLException, ClassNotFoundException {
        try (Connection conn = new ConexaoFactory().conexao();
             PreparedStatement stmt = conn.prepareStatement(
                     "DELETE FROM T_FIAP_LOCAIS WHERE ID_LOCAL = ?"
             )) {
            stmt.setInt(1, idLocal);
            stmt.execute();
        }
        return "Local excluído com sucesso!";
    }
}