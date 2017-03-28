package resource;

/**
 * Created by alexander.zelentsov on 27.03.2017.
 */
public class TransferResult {
    private AccountResource accountFrom;
    private AccountResource accountTo;

    public TransferResult(AccountResource accountFrom, AccountResource accountTo) {
        this.accountFrom = accountFrom;
        this.accountTo = accountTo;
    }

    public AccountResource getAccountFrom() {
        return accountFrom;
    }

    public AccountResource getAccountTo() {
        return accountTo;
    }
}
