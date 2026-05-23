package br.com.fiap.resource;

import br.com.fiap.bo.InstituicaoBO;
import br.com.fiap.entities.Instituicao;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.sql.SQLException;
import java.util.List;

@Path("/instituicao")
public class InstituicaoResource {

    private final InstituicaoBO instituicaoBO = new InstituicaoBO();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Instituicao> selecionar() throws ClassNotFoundException, SQLException {
        return instituicaoBO.selecionarBo();
    }

    @GET
    @Path("/{cnpj}")
    @Produces(MediaType.APPLICATION_JSON)
    public Instituicao buscarPorCnpj(@PathParam("cnpj") String cnpj) throws ClassNotFoundException, SQLException {
        return instituicaoBO.buscarPorCnpjBo(cnpj);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response inserir(Instituicao instituicao, @Context UriInfo uriInfo) throws ClassNotFoundException, SQLException {
        instituicaoBO.inserirBo(instituicao);
        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(instituicao.getCnpj());
        return Response.created(builder.build()).build();
    }

    @PUT
    @Path("/{cnpj}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response atualizar(@PathParam("cnpj") String cnpj, Instituicao instituicao) throws ClassNotFoundException, SQLException {
        instituicaoBO.atualizarBo(instituicao);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{cnpj}")
    public Response deletar(@PathParam("cnpj") String cnpj) throws ClassNotFoundException, SQLException {
        instituicaoBO.deletarBo(cnpj);
        return Response.ok().build();
    }
}