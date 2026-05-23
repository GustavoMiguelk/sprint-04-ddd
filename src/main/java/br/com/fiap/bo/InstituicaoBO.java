package br.com.fiap.bo;

import br.com.fiap.dao.InstituicaoDao;
import br.com.fiap.entities.Instituicao;
import java.sql.SQLException;
import java.util.List;

public class InstituicaoBO {

    public List<Instituicao> selecionarBo() throws ClassNotFoundException, SQLException {
        InstituicaoDao dao = new InstituicaoDao();
        return dao.selecionar();
    }

    public Instituicao buscarPorCnpjBo(String cnpj) throws ClassNotFoundException, SQLException {
        InstituicaoDao dao = new InstituicaoDao();
        return dao.buscarPorCnpj(cnpj);
    }

    public String inserirBo(Instituicao instituicao) throws ClassNotFoundException, SQLException {
        InstituicaoDao dao = new InstituicaoDao();
        return dao.inserir(instituicao);
    }

    public String atualizarBo(Instituicao instituicao) throws ClassNotFoundException, SQLException {
        InstituicaoDao dao = new InstituicaoDao();
        return dao.atualizar(instituicao);
    }

    public String deletarBo(String cnpj) throws ClassNotFoundException, SQLException {
        InstituicaoDao dao = new InstituicaoDao();
        return dao.deletar(cnpj);
    }
}