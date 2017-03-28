package service.account;

import resource.AccountResource;
import resource.TransferResult;

/**
 * Created by alexander.zelentsov on 24.03.2017.
 */
public interface AccountService {
    AccountResource getById(long id);

    AccountResource create(long id, AccountResource accountResource);

    TransferResult transfer(Long accountIdFrom, Long accountIdTo, Double value);

    void delete(long id);
}
