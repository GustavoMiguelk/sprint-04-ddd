package br.com.fiap.dao;

import br.com.fiap.conexoes.ConexaoFactory;
import br.com.fiap.entities.Beneficiario;
import br.com.fiap.entities.Consulta;
import br.com.fiap.entities.Local;
import br.com.fiap.entities.Profissional;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ConsultaDao {

    public String inserir(Consulta consulta) throws SQLException, ClassNotFoundException {
        try (Connection conn = new ConexaoFactory().conexao();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO T_FIAP_CONSULTAS (codigo_consulta, cpf_prof, cpf_benef, id_local, tipo, descricao) VALUES (?,?,?,?,?,?)"
             )) {
            stmt.setString(1, consulta.getCodigoConsulta());
            stmt.setString(2, consulta.getProfissional().getCpf());
            stmt.setString(3, consulta.getBeneficiario().getCpf());
            stmt.setInt(4,    consulta.getLocal().getIdLocal());
            stmt.setString(5, consulta.getTipo());
            stmt.setString(6, consulta.getDescricao());
            stmt.execute();
        }
        return "Cadastrado com sucesso!";
    }

    public List<Consulta> selecionar() throws SQLException, ClassNotFoundException {
        List<Consulta> lista = new ArrayList<>();
        try (Connection conn = new ConexaoFactory().conexao();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM T_FIAP_CONSULTAS");
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                Profissional prof = new Profissional("", rs.getString("cpf_prof"), "", "", "");
                Beneficiario benef = new Beneficiario("", 0, rs.getString("cpf_benef"), "", null, null);
                Local local = new Local("", "", "", "");
                local.setIdLocal(rs.getInt("id_local"));
                lista.add(new Consulta(
                        rs.getString("codigo_consulta"), prof, benef,
                        local, rs.getString("tipo"), rs.getString("descricao")
                ));
            }
        }
        return lista;
    }

    public Consulta buscarPorCodigo(String codigo) throws SQLException, ClassNotFoundException {
        try (Connection conn = new ConexaoFactory().conexao();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT * FROM T_FIAP_CONSULTAS WHERE CODIGO_CONSULTA = ?"
             )) {
            stmt.setString(1, codigo);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Profissional prof = new Profissional("", rs.getString("cpf_prof"), "", "", "");
                    Beneficiario benef = new Beneficiario("", 0, rs.getString("cpf_benef"), "", null, null);
                    Local local = new Local("", "", "", "");
                    local.setIdLocal(rs.getInt("id_local"));
                    return new Consulta(
                            rs.getString("codigo_consulta"), prof, benef,
                            local, rs.getString("tipo"), rs.getString("descricao")
                    );
                }
            }
        }
        return null;
    }

    public String atualizar(Consulta consulta) throws SQLException, ClassNotFoundException {
        try (Connection conn = new ConexaoFactory().conexao();
             PreparedStatement stmt = conn.prepareStatement(
                     "UPDATE T_FIAP_CONSULTAS SET TIPO = ?, DESCRICAO = ? WHERE CODIGO_CONSULTA = ?"
             )) {
            stmt.setString(1, consulta.getTipo());
            stmt.setString(2, consulta.getDescricao());
            stmt.setString(3, consulta.getCodigoConsulta());
            stmt.executeUpdate();
        }
        return "Consulta atualizada com sucesso!";
    }

    public String deletar(String codigo) throws SQLException, ClassNotFoundException {
        try (Connection conn = new ConexaoFactory().conexao();
             PreparedStatement stmt = conn.prepareStatement(
                     "DELETE FROM T_FIAP_CONSULTAS WHERE CODIGO_CONSULTA = ?"
             )) {
            stmt.setString(1, codigo);
            stmt.execute();
        }
        return "Consulta excluída com sucesso!";
    }
}