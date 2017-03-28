package service.account;

import org.apache.cayenne.ObjectContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import persistent.Account;
import resource.TransferResult;
import service.lock.LockService;
import storage.AccountStorage;

import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Created by alexander.zelentsov on 27.03.2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class AccountServiceImplTest {

    @Mock
    private AccountStorage accountStorage;
    @Mock
    private LockService lockService;

    @InjectMocks
    private AccountServiceImpl accountService;

    @Before
    public void before() {
        when(accountStorage.getContext())
                .thenReturn(mock(ObjectContext.class));
    }

    @Test
    public void transfer() throws Exception {
        long accountIdFrom = 1L;
        long accountIdTo = 2L;

        mockAccounts(accountIdFrom, accountIdTo, 100, 100);

        TransferResult transferResult = accountService.transfer(accountIdFrom, accountIdTo, 100.0);

        assertEquals(BigDecimal.valueOf(transferResult.getAccountFrom().getAmount()), BigDecimal.valueOf(0.0));
        assertEquals(BigDecimal.valueOf(transferResult.getAccountTo().getAmount()), BigDecimal.valueOf(200.0));
    }

    @Test(expected = IllegalStateException.class)
    public void errorIfAmountOfAccountFromIncorrect() throws Exception {
        long accountIdFrom = 1L;
        long accountIdTo = 2L;

        mockAccounts(accountIdFrom, accountIdTo, 5, 100);

        accountService.transfer(accountIdFrom, accountIdTo, 10.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void errorIfAccountIdsIsEqual() throws Exception {
        long accountIdFrom = 1L;
        long accountIdTo = 1L;

        accountService.transfer(accountIdFrom, accountIdTo, 10.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void errorIfAccountIdIsWrong() throws Exception {
        long accountIdFrom = 1L;
        long accountIdTo = 2L;

        mockAccounts(accountIdFrom, 3L, 5, 100);

        accountService.transfer(accountIdFrom, accountIdTo, 10.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void errorIfAccountIdFromIsNull() throws Exception {
        accountService.transfer(null, 1L, 10.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void errorIfAccountIdToIsNull() throws Exception {
        accountService.transfer(1L, null, 10.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void errorIfValueIsNull() throws Exception {
        accountService.transfer(1L, 2L, null);
    }

    private void mockAccounts(long accountIdFrom, long accountIdTo, double accountFromAmount, double accountToAmount) {
        when(accountStorage.getAccountById(eq(accountIdFrom), any()))
                .thenReturn(mockAccount(accountIdFrom, accountFromAmount));
        when(accountStorage.getAccountById(eq(accountIdTo), any()))
                .thenReturn(mockAccount(accountIdTo, accountToAmount));
    }

    private Account mockAccount(long id, double amount) {
        Account account = new Account();
        account.setId(id);
        account.setAmount(BigDecimal.valueOf(amount));
        return account;
    }

}