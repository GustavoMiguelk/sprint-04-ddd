package br.com.fiap.bo;

import br.com.fiap.dao.ConsultaDao;
import br.com.fiap.entities.Consulta;
import java.sql.SQLException;
import java.util.List;

public class ConsultaBO {

    public List<Consulta> selecionarBo() throws ClassNotFoundException, SQLException {
        ConsultaDao dao = new ConsultaDao();
        return dao.selecionar();
    }

    public Consulta buscarPorCodigoBo(String codigo) throws ClassNotFoundException, SQLException {
        ConsultaDao dao = new ConsultaDao();
        return dao.buscarPorCodigo(codigo);
    }

    public String inserirBo(Consulta consulta) throws ClassNotFoundException, SQLException {
        ConsultaDao dao = new ConsultaDao();
        return dao.inserir(consulta);
    }

    public String atualizarBo(Consulta consulta) throws ClassNotFoundException, SQLException {
        ConsultaDao dao = new ConsultaDao();
        return dao.atualizar(consulta);
    }

    public String deletarBo(String codigo) throws ClassNotFoundException, SQLException {
        ConsultaDao dao = new ConsultaDao();
        return dao.deletar(codigo);
    }
}