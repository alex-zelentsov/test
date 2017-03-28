package resource;

/**
 * Created by alexander.zelentsov on 27.03.2017.
 */
public class TransferResource {
    private Long accountIdFrom;
    private Long accountIdTo;
    private Double value;

    public TransferResource(Long accountIdFrom, Long accountIdTo, Double value) {
        this.accountIdFrom = accountIdFrom;
        this.accountIdTo = accountIdTo;
        this.value = value;
    }

    public TransferResource() {
    }

    public Long getAccountIdFrom() {
        return accountIdFrom;
    }

    public Long getAccountIdTo() {
        return accountIdTo;
    }

    public Double getValue() {
        return value;
    }
}
