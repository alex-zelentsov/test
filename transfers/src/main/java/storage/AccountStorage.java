package storage;

import org.apache.cayenne.ObjectContext;
import persistent.Account;
import resource.AccountResource;

/**
 * Created by alexander.zelentsov on 27.03.2017.
 */
public interface AccountStorage {
    ObjectContext getContext();

    Account getAccountById(long id, ObjectContext context);

    Account createAccount(AccountResource accountResource);

    void deleteAccount(long id);
}
