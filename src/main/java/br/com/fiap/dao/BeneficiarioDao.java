package br.com.fiap.dao;

import br.com.fiap.conexoes.ConexaoFactory;
import br.com.fiap.entities.Beneficiario;
import br.com.fiap.entities.Programa;
import br.com.fiap.entities.Responsavel;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class BeneficiarioDao {

    Connection minhaConexao;

    public BeneficiarioDao() throws SQLException, ClassNotFoundException {
        this.minhaConexao = new ConexaoFactory().conexao();
    }

    public String inserir(Beneficiario beneficiario) throws SQLException {
        PreparedStatement stmt = minhaConexao.prepareStatement(
                "INSERT INTO T_FIAP_BENEFICIARIOS (cpf, nome, idade, sexo, cpf_resp, id_programa) VALUES (?,?,?,?,?,?)"
        );

        stmt.setString(1, beneficiario.getCpf());
        stmt.setString(2, beneficiario.getNome());
        stmt.setInt(3,    beneficiario.getIdade());
        stmt.setString(4, beneficiario.getSexo());
        stmt.setString(5, beneficiario.getResponsavel().getCpf());
        stmt.setInt(6,    beneficiario.getPrograma().getIdPrograma());

        stmt.execute();
        stmt.close();

        return "Cadastrado com sucesso!";
    }

    public List<Beneficiario> selecionar() throws SQLException {
        List<Beneficiario> lista = new ArrayList<>();
        PreparedStatement stmt = minhaConexao.prepareStatement("SELECT * FROM T_FIAP_BENEFICIARIOS");
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Responsavel resp = new Responsavel("", rs.getString("cpf_resp"), "");
            Programa prog = new Programa("", "");
            prog.setIdPrograma(rs.getInt("id_programa"));
            Beneficiario b = new Beneficiario(
                    rs.getString("nome"), rs.getInt("idade"),
                    rs.getString("cpf"), rs.getString("sexo"), resp, prog
            );
            lista.add(b);
        }
        rs.close();
        stmt.close();
        return lista;
    }

    public Beneficiario buscarPorCpf(String cpf) throws SQLException {
        PreparedStatement stmt = minhaConexao.prepareStatement(
                "SELECT * FROM T_FIAP_BENEFICIARIOS WHERE CPF = ?"
        );
        stmt.setString(1, cpf);
        ResultSet rs = stmt.executeQuery();
        if (rs.next()) {
            Responsavel resp = new Responsavel("", rs.getString("cpf_resp"), "");
            Programa prog = new Programa("", "");
            prog.setIdPrograma(rs.getInt("id_programa"));
            Beneficiario b = new Beneficiario(
                    rs.getString("nome"), rs.getInt("idade"),
                    rs.getString("cpf"), rs.getString("sexo"), resp, prog
            );
            rs.close();
            stmt.close();
            return b;
        }
        rs.close();
        stmt.close();
        return null;
    }

    public String atualizar(Beneficiario beneficiario) throws SQLException {
        PreparedStatement stmt = minhaConexao.prepareStatement(
                "UPDATE T_FIAP_BENEFICIARIOS SET NOME = ?, IDADE = ?, SEXO = ? WHERE CPF = ?"
        );
        stmt.setString(1, beneficiario.getNome());
        stmt.setInt(2,    beneficiario.getIdade());
        stmt.setString(3, beneficiario.getSexo());
        stmt.setString(4, beneficiario.getCpf());
        stmt.executeUpdate();
        stmt.close();
        return "Beneficiário atualizado com sucesso!";
    }

    public String deletar(String cpf) throws SQLException {
        PreparedStatement stmt = minhaConexao.prepareStatement(
                "DELETE FROM T_FIAP_BENEFICIARIOS WHERE CPF = ?"
        );
        stmt.setString(1, cpf);
        stmt.execute();
        stmt.close();
        return "Beneficiário excluído com sucesso!";
    }
}
