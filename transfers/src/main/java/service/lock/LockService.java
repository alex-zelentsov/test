package service.lock;

/**
 * Created by alexander.zelentsov on 27.03.2017.
 */
public interface LockService {

    void lock(Long accountIdFrom, Long accountIdTo);

    void unlock(Long accountIdFrom, Long accountIdTo);
}
