package br.com.fiap.dao;

import br.com.fiap.conexoes.ConexaoFactory;
import br.com.fiap.entities.Beneficiario;
import br.com.fiap.entities.Consulta;
import br.com.fiap.entities.Local;
import br.com.fiap.entities.Profissional;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class ConsultaDao {

    Connection minhaConexao;

    public ConsultaDao() throws SQLException, ClassNotFoundException {
        this.minhaConexao = new ConexaoFactory().conexao();
    }

    public String inserir(Consulta consulta) throws SQLException {
        PreparedStatement stmt = minhaConexao.prepareStatement(
                "INSERT INTO T_FIAP_CONSULTAS (codigo_consulta, cpf_prof, cpf_benef, id_local, tipo, descricao) VALUES (?,?,?,?,?,?)"
        );

        stmt.setString(1, consulta.getCodigoConsulta());
        stmt.setString(2, consulta.getProfissional().getCpf());
        stmt.setString(3, consulta.getBeneficiario().getCpf());
        stmt.setInt(4,    consulta.getLocal().getIdLocal());
        stmt.setString(5, consulta.getTipo());
        stmt.setString(6, consulta.getDescricao());

        stmt.execute();
        stmt.close();

        return "Cadastrado com sucesso!";
    }

    public List<Consulta> selecionar() throws SQLException {
        List<Consulta> lista = new ArrayList<>();
        PreparedStatement stmt = minhaConexao.prepareStatement("SELECT * FROM T_FIAP_CONSULTAS");
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Profissional prof = new Profissional("", rs.getString("cpf_prof"), "", "");
            Beneficiario benef = new Beneficiario("", 0, rs.getString("cpf_benef"), "", null, null);
            Local local = new Local("", "", "", "");
            local.setIdLocal(rs.getInt("id_local"));
            Consulta c = new Consulta(
                    rs.getString("codigo_consulta"), prof, benef,
                    local, rs.getString("tipo"), rs.getString("descricao")
            );
            lista.add(c);
        }
        rs.close();
        stmt.close();
        return lista;
    }

    public Consulta buscarPorCodigo(String codigo) throws SQLException {
        PreparedStatement stmt = minhaConexao.prepareStatement(
                "SELECT * FROM T_FIAP_CONSULTAS WHERE CODIGO_CONSULTA = ?"
        );
        stmt.setString(1, codigo);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            Profissional prof = new Profissional("", rs.getString("cpf_prof"), "", "");
            Beneficiario benef = new Beneficiario("", 0, rs.getString("cpf_benef"), "", null, null);
            Local local = new Local("", "", "", "");
            local.setIdLocal(rs.getInt("id_local"));
            Consulta c = new Consulta(
                    rs.getString("codigo_consulta"), prof, benef,
                    local, rs.getString("tipo"), rs.getString("descricao")
            );
            rs.close();
            stmt.close();
            return c;
        }
        rs.close();
        stmt.close();
        return null;
    }

    public String atualizar(Consulta consulta) throws SQLException {
        PreparedStatement stmt = minhaConexao.prepareStatement(
                "UPDATE T_FIAP_CONSULTAS SET TIPO = ?, DESCRICAO = ? WHERE CODIGO_CONSULTA = ?"
        );
        stmt.setString(1, consulta.getTipo());
        stmt.setString(2, consulta.getDescricao());
        stmt.setString(3, consulta.getCodigoConsulta());
        stmt.executeUpdate();
        stmt.close();
        return "Consulta atualizada com sucesso!";
    }

    public String deletar(String codigo) throws SQLException {
        PreparedStatement stmt = minhaConexao.prepareStatement(
                "DELETE FROM T_FIAP_CONSULTAS WHERE CODIGO_CONSULTA = ?"
        );
        stmt.setString(1, codigo);
        stmt.execute();
        stmt.close();
        return "Consulta excluída com sucesso!";
    }
}
