package br.com.fiap.bo;

import br.com.fiap.dao.ProfissionalDao;
import br.com.fiap.entities.Profissional;
import java.sql.SQLException;
import java.util.List;

public class ProfissionalBO {

    public List<Profissional> selecionarBo() throws ClassNotFoundException, SQLException {
        ProfissionalDao dao = new ProfissionalDao();
        return dao.selecionar();
    }

    public Profissional buscarPorCpfBo(String cpf) throws ClassNotFoundException, SQLException {
        ProfissionalDao dao = new ProfissionalDao();
        return dao.buscarPorCpf(cpf);
    }

    public String inserirBo(Profissional profissional) throws ClassNotFoundException, SQLException {
        ProfissionalDao dao = new ProfissionalDao();
        return dao.inserir(profissional);
    }

    public String atualizarBo(Profissional profissional) throws ClassNotFoundException, SQLException {
        ProfissionalDao dao = new ProfissionalDao();
        return dao.atualizar(profissional);
    }

    public String deletarBo(String cpf) throws ClassNotFoundException, SQLException {
        ProfissionalDao dao = new ProfissionalDao();
        return dao.deletar(cpf);
    }

    public Profissional loginBo(String cro, String senha) throws ClassNotFoundException, SQLException {
        ProfissionalDao dao = new ProfissionalDao();
        return dao.login(cro, senha);
    }
}