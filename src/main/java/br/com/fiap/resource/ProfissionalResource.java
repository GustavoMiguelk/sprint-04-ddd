package br.com.fiap.resource;

import br.com.fiap.bo.ProfissionalBO;
import br.com.fiap.entities.Profissional;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.*;
import java.sql.SQLException;
import java.util.List;

@Path("/profissional")
public class ProfissionalResource {

    private final ProfissionalBO profissionalBO = new ProfissionalBO();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<Profissional> selecionar() throws ClassNotFoundException, SQLException {
        return profissionalBO.selecionarBo();
    }

    @GET
    @Path("/{cpf}")
    @Produces(MediaType.APPLICATION_JSON)
    public Profissional buscarPorCpf(@PathParam("cpf") String cpf) throws ClassNotFoundException, SQLException {
        return profissionalBO.buscarPorCpfBo(cpf);
    }

    @POST
    @Path("/cadastrar")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response inserir(Profissional profissional, @Context UriInfo uriInfo) throws ClassNotFoundException, SQLException {
        profissionalBO.inserirBo(profissional);
        UriBuilder builder = uriInfo.getAbsolutePathBuilder();
        builder.path(profissional.getCpf());
        return Response.created(builder.build()).build();
    }

    @PUT
    @Path("/{cpf}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response atualizar(@PathParam("cpf") String cpf, Profissional profissional) throws ClassNotFoundException, SQLException {
        profissionalBO.atualizarBo(profissional);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{cpf}")
    public Response deletar(@PathParam("cpf") String cpf) throws ClassNotFoundException, SQLException {
        profissionalBO.deletarBo(cpf);
        return Response.ok().build();
    }

    // Classe interna para receber o body do login
    public static class LoginRequest {
        public String cro;
        public String senha;
    }

    // Endpoint de login
    @POST
    @Path("/login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(LoginRequest request) throws ClassNotFoundException, SQLException {
        Profissional p = profissionalBO.loginBo(request.cro, request.senha);
        if (p == null) {
            return Response.status(Response.Status.UNAUTHORIZED)
                    .entity("{\"mensagem\": \"CRO ou senha inválidos\"}")
                    .build();
        }
        return Response.ok(p).build();
    }
}