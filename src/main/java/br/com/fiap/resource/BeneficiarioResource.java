package br.com.fiap.resource;

import br.com.fiap.bo.BeneficiarioBO;
import br.com.fiap.entities.Beneficiario;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.sql.SQLException;
import java.util.List;

@Path("/beneficiario")
public class BeneficiarioResource {

    private final BeneficiarioBO beneficiarioBO = new BeneficiarioBO();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Beneficiario> selecionar() throws ClassNotFoundException, SQLException {
        return beneficiarioBO.selecionarBo();
    }

    @GET
    @Path("/{cpf}")
    @Produces(MediaType.APPLICATION_JSON)
    public Beneficiario buscarPorCpf(@PathParam("cpf") String cpf) throws ClassNotFoundException, SQLException {
        return beneficiarioBO.buscarPorCpfBo(cpf);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response inserir(Beneficiario beneficiario, @Context UriInfo uriInfo) throws ClassNotFoundException, SQLException {
        beneficiarioBO.inserirBo(beneficiario);
        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(beneficiario.getCpf());
        return Response.created(builder.build()).build();
    }

    @PUT
    @Path("/{cpf}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response atualizar(@PathParam("cpf") String cpf, Beneficiario beneficiario) throws ClassNotFoundException, SQLException {
        beneficiarioBO.atualizarBo(beneficiario);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{cpf}")
    public Response deletar(@PathParam("cpf") String cpf) throws ClassNotFoundException, SQLException {
        beneficiarioBO.deletarBo(cpf);
        return Response.ok().build();
    }
}