package br.com.fiap.bo;

import br.com.fiap.dao.LocalDao;
import br.com.fiap.entities.Local;
import java.sql.SQLException;
import java.util.List;

public class LocalBO {

    public List<Local> selecionarBo() throws ClassNotFoundException, SQLException {
        LocalDao dao = new LocalDao();
        return dao.selecionar();
    }

    public Local buscarPorIdBo(int idLocal) throws ClassNotFoundException, SQLException {
        LocalDao dao = new LocalDao();
        return dao.buscarPorId(idLocal);
    }

    public String inserirBo(Local local) throws ClassNotFoundException, SQLException {
        LocalDao dao = new LocalDao();
        return dao.inserir(local);
    }

    public String atualizarBo(Local local) throws ClassNotFoundException, SQLException {
        LocalDao dao = new LocalDao();
        return dao.atualizar(local);
    }

    public String deletarBo(int idLocal) throws ClassNotFoundException, SQLException {
        LocalDao dao = new LocalDao();
        return dao.deletar(idLocal);
    }
}