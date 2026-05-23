package br.com.fiap.resource;

import br.com.fiap.bo.ConsultaBO;
import br.com.fiap.entities.Consulta;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.sql.SQLException;
import java.util.List;

@Path("/consulta")
public class ConsultaResource {

    private final ConsultaBO consultaBO = new ConsultaBO();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Consulta> selecionar() throws ClassNotFoundException, SQLException {
        return consultaBO.selecionarBo();
    }

    @GET
    @Path("/{codigo}")
    @Produces(MediaType.APPLICATION_JSON)
    public Consulta buscarPorCodigo(@PathParam("codigo") String codigo) throws ClassNotFoundException, SQLException {
        return consultaBO.buscarPorCodigoBo(codigo);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response inserir(Consulta consulta, @Context UriInfo uriInfo) throws ClassNotFoundException, SQLException {
        consultaBO.inserirBo(consulta);
        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(consulta.getCodigoConsulta());
        return Response.created(builder.build()).build();
    }

    @PUT
    @Path("/{codigo}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response atualizar(@PathParam("codigo") String codigo, Consulta consulta) throws ClassNotFoundException, SQLException {
        consultaBO.atualizarBo(consulta);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{codigo}")
    public Response deletar(@PathParam("codigo") String codigo) throws ClassNotFoundException, SQLException {
        consultaBO.deletarBo(codigo);
        return Response.ok().build();
    }
}