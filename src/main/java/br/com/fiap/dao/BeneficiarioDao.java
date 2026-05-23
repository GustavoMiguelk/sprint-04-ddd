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

    public String inserir(Beneficiario beneficiario) throws SQLException, ClassNotFoundException {
        try (Connection conn = new ConexaoFactory().conexao();
             PreparedStatement stmt = conn.prepareStatement(
                     "INSERT INTO T_FIAP_BENEFICIARIOS (cpf, nome, idade, sexo, cpf_resp, id_programa) VALUES (?,?,?,?,?,?)"
             )) {
            stmt.setString(1, beneficiario.getCpf());
            stmt.setString(2, beneficiario.getNome());
            stmt.setInt(3,    beneficiario.getIdade());
            stmt.setString(4, beneficiario.getSexo());
            stmt.setString(5, beneficiario.getResponsavel().getCpf());
            stmt.setInt(6,    beneficiario.getPrograma().getIdPrograma());
            stmt.execute();
        }
        return "Cadastrado com sucesso!";
    }

    public List<Beneficiario> selecionar() throws SQLException, ClassNotFoundException {
        List<Beneficiario> lista = new ArrayList<>();
        try (Connection conn = new ConexaoFactory().conexao();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM T_FIAP_BENEFICIARIOS");
             ResultSet rs = stmt.executeQuery()) {
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
        }
        return lista;
    }

    public Beneficiario buscarPorCpf(String cpf) throws SQLException, ClassNotFoundException {
        try (Connection conn = new ConexaoFactory().conexao();
             PreparedStatement stmt = conn.prepareStatement(
                     "SELECT * FROM T_FIAP_BENEFICIARIOS WHERE CPF = ?"
             )) {
            stmt.setString(1, cpf);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Responsavel resp = new Responsavel("", rs.getString("cpf_resp"), "");
                    Programa prog = new Programa("", "");
                    prog.setIdPrograma(rs.getInt("id_programa"));
                    return new Beneficiario(
                            rs.getString("nome"), rs.getInt("idade"),
                            rs.getString("cpf"), rs.getString("sexo"), resp, prog
                    );
                }
            }
        }
        return null;
    }

    public String atualizar(Beneficiario beneficiario) throws SQLException, ClassNotFoundException {
        try (Connection conn = new ConexaoFactory().conexao();
             PreparedStatement stmt = conn.prepareStatement(
                     "UPDATE T_FIAP_BENEFICIARIOS SET NOME = ?, IDADE = ?, SEXO = ? WHERE CPF = ?"
             )) {
            stmt.setString(1, beneficiario.getNome());
            stmt.setInt(2,    beneficiario.getIdade());
            stmt.setString(3, beneficiario.getSexo());
            stmt.setString(4, beneficiario.getCpf());
            stmt.executeUpdate();
        }
        return "Beneficiário atualizado com sucesso!";
    }

    public String deletar(String cpf) throws SQLException, ClassNotFoundException {
        try (Connection conn = new ConexaoFactory().conexao();
             PreparedStatement stmt = conn.prepareStatement(
                     "DELETE FROM T_FIAP_BENEFICIARIOS WHERE CPF = ?"
             )) {
            stmt.setString(1, cpf);
            stmt.execute();
        }
        return "Beneficiário excluído com sucesso!";
    }
}