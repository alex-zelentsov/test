package endpoint;

import com.google.inject.Inject;
import mapper.JacksonMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import resource.AccountResource;
import resource.TransferResource;
import resource.TransferResult;
import service.account.AccountService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by alexander.zelentsov on 23.03.2017.
 */
@Path("/account")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class AccountEndpoint {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountEndpoint.class);

    @Inject
    private JacksonMapper jacksonMapper;
    @Inject
    private AccountService accountService;

    @PUT()
    @Path("/transfer")
    public Response doTransfer(String data) {
        try {
            TransferResource transferResource = jacksonMapper.readObject(data, TransferResource.class);

            TransferResult transferResult = accountService.transfer(
                    transferResource.getAccountIdFrom(), transferResource.getAccountIdTo(), transferResource.getValue());

            return Response.ok(jacksonMapper.writeToString(transferResult)).build();
        } catch (Exception e) {
            return returnBadRequest(e);
        }
    }

    @GET()
    @Path("/{id}")
    public Response getAccount(@PathParam("id") long id) {
        try {
            AccountResource accountResource = accountService.getById(id);

            return Response.ok(jacksonMapper.writeToString(accountResource)).build();
        } catch (Exception e) {
            return returnBadRequest(e);
        }
    }

    @POST()
    @Path("/{id}")
    public Response createAccount(@PathParam("id") long id, String data) {
        try {
            AccountResource accountResource = jacksonMapper.readObject(data, AccountResource.class);

            AccountResource savedResource = accountService.create(id, accountResource);

            return Response.ok(jacksonMapper.writeToString(savedResource)).build();
        } catch (Exception e) {
            return returnBadRequest(e);
        }
    }

    @DELETE()
    @Path("/{id}")
    public Response deleteAccount(@PathParam("id") long id) {
        try {
            accountService.delete(id);
            return Response.ok().build();
        } catch (Exception e) {
            return returnBadRequest(e);
        }
    }

    private Response returnBadRequest(Exception e) {
        LOGGER.error(e.getMessage(), e);
        return Response.status(Response.Status.BAD_REQUEST).build();
    }
}