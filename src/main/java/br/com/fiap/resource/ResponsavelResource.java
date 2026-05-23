package br.com.fiap.resource;

import br.com.fiap.bo.ResponsavelBO;
import br.com.fiap.entities.Responsavel;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.sql.SQLException;
import java.util.List;

@Path("/responsavel")
public class ResponsavelResource {

    private final ResponsavelBO responsavelBO = new ResponsavelBO();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Responsavel> selecionar() throws ClassNotFoundException, SQLException {
        return responsavelBO.selecionarBo();
    }

    @GET
    @Path("/{cpf}")
    @Produces(MediaType.APPLICATION_JSON)
    public Responsavel buscarPorCpf(@PathParam("cpf") String cpf) throws ClassNotFoundException, SQLException {
        return responsavelBO.buscarPorCpfBo(cpf);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response inserir(Responsavel responsavel, @Context UriInfo uriInfo) throws ClassNotFoundException, SQLException {
        responsavelBO.inserirBo(responsavel);
        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(responsavel.getCpf());
        return Response.created(builder.build()).build();
    }

    @PUT
    @Path("/{cpf}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response atualizar(@PathParam("cpf") String cpf, Responsavel responsavel) throws ClassNotFoundException, SQLException {
        responsavelBO.atualizarBo(responsavel);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{cpf}")
    public Response deletar(@PathParam("cpf") String cpf) throws ClassNotFoundException, SQLException {
        responsavelBO.deletarBo(cpf);
        return Response.ok().build();
    }
}