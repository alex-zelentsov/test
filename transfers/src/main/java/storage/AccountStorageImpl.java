package storage;

import com.google.inject.Inject;
import org.apache.cayenne.ObjectContext;
import org.apache.cayenne.configuration.server.ServerRuntime;
import org.apache.cayenne.query.ObjectSelect;
import persistent.Account;
import resource.AccountResource;

import java.math.BigDecimal;

/**
 * Created by alexander.zelentsov on 27.03.2017.
 */
public class AccountStorageImpl implements AccountStorage {
    @Inject
    private ServerRuntime serverRuntime;

    @Override
    public ObjectContext getContext() {
        return serverRuntime.newContext();
    }

    @Override
    public Account getAccountById(long id, ObjectContext context) {
        return ObjectSelect.query(Account.class)
                .where(Account.ID.eq(id))
                .selectOne(context);
    }

    @Override
    public Account createAccount(AccountResource accountResource) {
        ObjectContext context = getContext();
        Account account = context.newObject(Account.class);
        account.setId(accountResource.getId());
        account.setAmount(BigDecimal.valueOf(accountResource.getAmount()));
        context.commitChanges();
        return account;
    }

    @Override
    public void deleteAccount(long id) {
        ObjectContext context = getContext();
        Account account = getAccountById(id, context);
        if (account != null) {
            context.deleteObject(account);
        }
        context.commitChanges();
    }
}
