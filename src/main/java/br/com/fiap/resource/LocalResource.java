package br.com.fiap.resource;

import br.com.fiap.bo.LocalBO;
import br.com.fiap.entities.Local;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.sql.SQLException;
import java.util.List;

@Path("/local")
public class LocalResource {

    private final LocalBO localBO = new LocalBO();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Local> selecionar() throws ClassNotFoundException, SQLException {
        return localBO.selecionarBo();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Local buscarPorId(@PathParam("id") int id) throws ClassNotFoundException, SQLException {
        return localBO.buscarPorIdBo(id);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response inserir(Local local, @Context UriInfo uriInfo) throws ClassNotFoundException, SQLException {
        localBO.inserirBo(local);
        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(Integer.toString(local.getIdLocal()));
        return Response.created(builder.build()).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response atualizar(@PathParam("id") int id, Local local) throws ClassNotFoundException, SQLException {
        localBO.atualizarBo(local);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{id}")
    public Response deletar(@PathParam("id") int id) throws ClassNotFoundException, SQLException {
        localBO.deletarBo(id);
        return Response.ok().build();
    }
}