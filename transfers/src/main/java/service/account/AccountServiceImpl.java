package service.account;

import com.google.inject.Inject;
import org.apache.cayenne.ObjectContext;
import persistent.Account;
import resource.AccountResource;
import resource.TransferResult;
import service.lock.LockService;
import storage.AccountStorage;

import java.math.BigDecimal;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkState;

/**
 * Created by alexander.zelentsov on 24.03.2017.
 */
public class AccountServiceImpl implements AccountService {
    @Inject
    private LockService lockService;
    @Inject
    private AccountStorage accountStorage;

    @Override
    public AccountResource getById(long id) {
        ObjectContext context = accountStorage.getContext();
        Account account = accountStorage.getAccountById(id, context);
        return AccountResource.from(account);
    }

    @Override
    public AccountResource create(long id, AccountResource accountResource) {
        Account account = accountStorage.createAccount(accountResource);
        return AccountResource.from(account);
    }

    @Override
    public TransferResult transfer(Long accountIdFrom, Long accountIdTo, Double value) {

        checkQueryParameters(accountIdFrom, accountIdTo, value);

        Account accountFrom;
        Account accountTo;
        try {
            lockService.lock(accountIdFrom, accountIdTo);

            ObjectContext context = accountStorage.getContext();
            accountFrom = accountStorage.getAccountById(accountIdFrom, context);
            accountTo = accountStorage.getAccountById(accountIdTo, context);

            checkAccountExist(accountIdFrom, accountFrom);
            checkAccountExist(accountIdTo, accountTo);

            BigDecimal amountFrom = accountFrom.getAmount();
            BigDecimal transferValue = BigDecimal.valueOf(value);

            checkTransferAvailability(amountFrom, transferValue);

            BigDecimal newAmountFrom = amountFrom.subtract(transferValue);
            BigDecimal amountTo = accountTo.getAmount();
            BigDecimal newAmountTo = amountTo.add(transferValue);

            accountFrom.setAmount(newAmountFrom);
            accountTo.setAmount(newAmountTo);

            context.commitChanges();
        } finally {
            lockService.unlock(accountIdFrom, accountIdTo);
        }

        return new TransferResult(AccountResource.from(accountFrom), AccountResource.from(accountTo));
    }

    @Override
    public void delete(long id) {
        accountStorage.deleteAccount(id);
    }

    private void checkQueryParameters(Long accountIdFrom, Long accountIdTo, Double value) {
        checkArgument(accountIdFrom != null, "accountIdFrom should be not null: " + accountIdFrom);
        checkArgument(accountIdTo != null, "accountIdTo should be not null: " + accountIdTo);
        checkArgument(!accountIdFrom.equals(accountIdTo), "accountIdFrom and accountIdTo should not be equal");

        checkArgument(value != null, "value should be not null: " + value);
    }

    private void checkTransferAvailability(BigDecimal amountFrom, BigDecimal transferValue) {
        checkState(amountFrom.compareTo(transferValue) >= 0,
                "amount: " + amountFrom.doubleValue() + " is less then transferring value: " + transferValue.doubleValue());
    }

    private void checkAccountExist(long id, Account account) {
        checkArgument(account != null, "account with id: " + id + " doesn't exist");
    }
}
