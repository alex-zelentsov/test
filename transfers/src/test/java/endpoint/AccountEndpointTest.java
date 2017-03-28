package endpoint;

import com.fasterxml.jackson.core.JsonProcessingException;
import main.Application;
import mapper.JacksonMapper;
import mapper.JacksonMapperImpl;
import org.junit.After;
import org.junit.BeforeClass;
import org.junit.Test;
import resource.AccountResource;
import resource.TransferResource;
import resource.TransferResult;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import static org.junit.Assert.assertEquals;

/**
 * Created by alexander.zelentsov on 27.03.2017.
 */
public class AccountEndpointTest {

    private static final String PATH_FOR_ACCOUNT_1 = "/account/1";
    private static final String PATH_FOR_ACCOUNT_2 = "/account/2";
    private static final String PATH_ACCOUNT_TRANSFER = "/account/transfer";

    private JacksonMapper jacksonMapper = new JacksonMapperImpl();

    private WebTarget base = ClientBuilder.newClient().target("http://localhost:8080/");

    @BeforeClass
    public static void beforeClass() throws InterruptedException {
        Thread thread = new Thread(() -> {
            String[] args = {"--server"};
            Application.main(args);
        });
        thread.setDaemon(true);
        thread.start();

        Thread.sleep(3000);
    }


    @After
    public void deleteAccountsAfterTest() {
        base.path(PATH_FOR_ACCOUNT_1).request().delete();
        base.path(PATH_FOR_ACCOUNT_2).request().delete();
    }

    @Test
    public void createAccount() throws JsonProcessingException {
        AccountResource accountResource = new AccountResource(1L, 100.0);
        String accountResourceAsString = jacksonMapper.writeToString(accountResource);
        Entity<String> entity = Entity.entity(accountResourceAsString, MediaType.APPLICATION_JSON_TYPE);

        Response response = base.path(PATH_FOR_ACCOUNT_1).request().post(entity);

        assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());
        assertEquals(accountResourceAsString, response.readEntity(String.class));
    }

    @Test
    public void createAndGetAccount() throws JsonProcessingException {
        String accountResourceAsString = createAccountRequest(PATH_FOR_ACCOUNT_1, 1L, 100.0);

        Response getResponse = base.path(PATH_FOR_ACCOUNT_1).request().get();

        assertEquals(Response.Status.OK.getStatusCode(), getResponse.getStatus());
        assertEquals(accountResourceAsString, getResponse.readEntity(String.class));
    }

    @Test
    public void transfer() throws JsonProcessingException {
        long accountId1 = 1L;
        createAccountRequest(PATH_FOR_ACCOUNT_1, accountId1, 100.0);
        long accountId2 = 2L;
        createAccountRequest(PATH_FOR_ACCOUNT_2, accountId2, 100.0);
        TransferResult expectedTransferResult =
                new TransferResult(new AccountResource(accountId1, 0.0), new AccountResource(accountId2, 200.0));

        String valueAsString = jacksonMapper.writeToString(new TransferResource(accountId1, accountId2, 100.0));
        Entity<String> entity = Entity.entity(valueAsString, MediaType.APPLICATION_JSON_TYPE);
        Response response = base.path("/account/transfer").request().put(entity);

        checkResponse(response.getStatus(), expectedTransferResult, response.readEntity(String.class));

        checkAccount1(expectedTransferResult);
        checkAccount2(expectedTransferResult);
    }

    @Test
    public void errorIfAmountOfAccountFromIsIncorrect() throws JsonProcessingException {
        long accountId1 = 1L;
        createAccountRequest(PATH_FOR_ACCOUNT_1, accountId1, 10.0);
        long accountId2 = 2L;
        createAccountRequest(PATH_FOR_ACCOUNT_2, accountId2, 100.0);

        String valueAsString = jacksonMapper.writeToString(new TransferResource(accountId1, accountId2, 100.0));
        Entity<String> entity = Entity.entity(valueAsString, MediaType.APPLICATION_JSON_TYPE);
        Response response = base.path(PATH_ACCOUNT_TRANSFER).request().put(entity);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    @Test
    public void errorIfAccountIsEqual() throws JsonProcessingException {
        long accountId1 = 1L;
        createAccountRequest(PATH_FOR_ACCOUNT_1, accountId1, 10.0);

        String valueAsString = jacksonMapper.writeToString(new TransferResource(accountId1, accountId1, 100.0));
        Entity<String> entity = Entity.entity(valueAsString, MediaType.APPLICATION_JSON_TYPE);
        Response response = base.path(PATH_ACCOUNT_TRANSFER).request().put(entity);

        assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());
    }

    private void checkResponse(int status, TransferResult expectedTransferResult, String actual) throws JsonProcessingException {
        assertEquals(Response.Status.OK.getStatusCode(), status);
        assertEquals(jacksonMapper.writeToString(expectedTransferResult), actual);
    }

    private void checkAccount1(TransferResult expectedTransferResult) throws JsonProcessingException {
        Response getAccount1 = base.path(PATH_FOR_ACCOUNT_1).request().get();

        assertEquals(Response.Status.OK.getStatusCode(), getAccount1.getStatus());
        String expectedAccount1 = jacksonMapper.writeToString(expectedTransferResult.getAccountFrom());
        assertEquals(expectedAccount1, getAccount1.readEntity(String.class));
    }

    private void checkAccount2(TransferResult expectedTransferResult) throws JsonProcessingException {
        Response getAccount2 = base.path(PATH_FOR_ACCOUNT_2).request().get();

        assertEquals(Response.Status.OK.getStatusCode(), getAccount2.getStatus());
        String expectedAccount2 = jacksonMapper.writeToString(expectedTransferResult.getAccountTo());
        assertEquals(expectedAccount2, getAccount2.readEntity(String.class));
    }

    private String createAccountRequest(String pathForAccount, Long accountId, Double amount) throws JsonProcessingException {
        AccountResource accountResource = new AccountResource(accountId, amount);
        String accountResourceAsString = jacksonMapper.writeToString(accountResource);

        Entity<String> entity = Entity.entity(accountResourceAsString, MediaType.APPLICATION_JSON_TYPE);

        base.path(pathForAccount).request().post(entity);

        return accountResourceAsString;
    }
}