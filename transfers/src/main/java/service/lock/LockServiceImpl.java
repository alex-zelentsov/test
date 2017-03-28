package service.lock;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Created by alexander.zelentsov on 27.03.2017.
 */
public class LockServiceImpl implements LockService {

    private final ConcurrentMap<Long, ReentrantLock> lockMap = new ConcurrentHashMap<>();

    @Override
    public void lock(Long accountIdFrom, Long accountIdTo) {
        if (accountIdFrom.compareTo(accountIdTo) > 0) {
            lockForAccountId(accountIdFrom);
            lockForAccountId(accountIdTo);
        } else {
            lockForAccountId(accountIdTo);
            lockForAccountId(accountIdFrom);
        }

    }

    private void lockForAccountId(Long accountId) {
        ReentrantLock lock = new ReentrantLock();
        ReentrantLock prevReentrantLock = lockMap.putIfAbsent(accountId, lock);
        if (prevReentrantLock != null) {
            lock = prevReentrantLock;
        }
        try {
           lock.lockInterruptibly();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Override
    public void unlock(Long accountIdFrom, Long accountIdTo) {
        if (accountIdFrom.compareTo(accountIdTo) > 0) {
            unlockForAccountId(accountIdTo);
            unlockForAccountId(accountIdFrom);
        } else {
            unlockForAccountId(accountIdFrom);
            unlockForAccountId(accountIdTo);
        }
    }

    private void unlockForAccountId(Long accountId) {
        ReentrantLock lock = lockMap.remove(accountId);
        if (lock != null) {
            lock.unlock();
        }
    }
}
