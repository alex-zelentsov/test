package resource;

import persistent.Account;

/**
 * Created by alexander.zelentsov on 23.03.2017.
 */
public class AccountResource {
    private long id;
    private Double amount;

    public AccountResource() {
    }

    public AccountResource(long id, Double amount) {
        this.id = id;
        this.amount = amount;
    }

    public long getId() {
        return id;
    }

    public Double getAmount() {
        return amount;
    }

    public static AccountResource from(Account account) {
        return new AccountResource(account.getId(), account.getAmount().doubleValue());
    }
}
