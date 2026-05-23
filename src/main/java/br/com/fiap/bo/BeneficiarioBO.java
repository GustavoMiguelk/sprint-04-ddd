package br.com.fiap.bo;

import br.com.fiap.dao.BeneficiarioDao;
import br.com.fiap.entities.Beneficiario;
import java.sql.SQLException;
import java.util.List;

public class BeneficiarioBO {

    public List<Beneficiario> selecionarBo() throws ClassNotFoundException, SQLException {
        BeneficiarioDao dao = new BeneficiarioDao();
        return dao.selecionar();
    }

    public Beneficiario buscarPorCpfBo(String cpf) throws ClassNotFoundException, SQLException {
        BeneficiarioDao dao = new BeneficiarioDao();
        return dao.buscarPorCpf(cpf);
    }

    public String inserirBo(Beneficiario beneficiario) throws ClassNotFoundException, SQLException {
        BeneficiarioDao dao = new BeneficiarioDao();
        return dao.inserir(beneficiario);
    }

    public String atualizarBo(Beneficiario beneficiario) throws ClassNotFoundException, SQLException {
        BeneficiarioDao dao = new BeneficiarioDao();
        return dao.atualizar(beneficiario);
    }

    public String deletarBo(String cpf) throws ClassNotFoundException, SQLException {
        BeneficiarioDao dao = new BeneficiarioDao();
        return dao.deletar(cpf);
    }
}