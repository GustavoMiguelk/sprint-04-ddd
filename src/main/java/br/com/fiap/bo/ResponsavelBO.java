package br.com.fiap.bo;

import br.com.fiap.dao.ResponsavelDao;
import br.com.fiap.entities.Responsavel;
import java.sql.SQLException;
import java.util.List;

public class ResponsavelBO {

    public List<Responsavel> selecionarBo() throws ClassNotFoundException, SQLException {
        ResponsavelDao dao = new ResponsavelDao();
        return dao.selecionar();
    }

    public Responsavel buscarPorCpfBo(String cpf) throws ClassNotFoundException, SQLException {
        ResponsavelDao dao = new ResponsavelDao();
        return dao.buscarPorCpf(cpf);
    }

    public String inserirBo(Responsavel responsavel) throws ClassNotFoundException, SQLException {
        ResponsavelDao dao = new ResponsavelDao();
        return dao.inserir(responsavel);
    }

    public String atualizarBo(Responsavel responsavel) throws ClassNotFoundException, SQLException {
        ResponsavelDao dao = new ResponsavelDao();
        return dao.atualizar(responsavel);
    }

    public String deletarBo(String cpf) throws ClassNotFoundException, SQLException {
        ResponsavelDao dao = new ResponsavelDao();
        return dao.deletar(cpf);
    }
}