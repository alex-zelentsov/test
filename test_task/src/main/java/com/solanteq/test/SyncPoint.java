package com.solanteq.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.*;
import java.util.logging.Logger;


/**
 * This class performs synchronisation.
 *
 * @author dkochelaev
 */
@Service
public class SyncPoint {
    private static final Logger LOGGER = Logger.getLogger(SyncPoint.class.getName());

    public static final int CALLBACK_TIMEOUT = 5;
    public static final int CLIENT_TIMEOUT = 60 * 10;

    @Autowired
    private Calculator calculator;

    private ConcurrentMap<UUID, Exchanger<Integer>> exchangerMap = new ConcurrentHashMap<>();

     /**
     * Method performs synchronisation via callback method
     * Get value from callback
     *
     * @param value from request handler
     * @return value from callback
     * @throws CalculatingException if the current thread was
     *         interrupted while waiting result from callback
     */
    public int getCalculatedValue(String value) {
        UUID id = calculator.calculate(value, this::callback);
        Exchanger<Integer> exchanger = getExchanger(id);
        Integer exchange;
        try {
            exchange = exchanger.exchange(null, CLIENT_TIMEOUT, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            LOGGER.info("Getting calculated value process was interrupted: " + e);
            throw new CalculatingException(e);
        } catch (TimeoutException e) {
            LOGGER.info("Getting calculated value process was finished with time out: " + e);
            throw  new CalculatingException(e);
        } finally {
            exchangerMap.remove(id);
        }
        return exchange;
    }

    private void callback(UUID id, int result) {
        try {
            Exchanger<Integer> exchanger = getExchanger(id);
            exchanger.exchange(result, CALLBACK_TIMEOUT, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            LOGGER.info("Callback was interrupted: " + e);
            Thread.currentThread().interrupt();
        } catch (TimeoutException e) {
            LOGGER.info("Callback was finished with time out: " + e);
        } finally {
            exchangerMap.remove(id);
        }
    }

    private Exchanger<Integer> getExchanger(UUID id) {
        Exchanger<Integer> newExchanger = new Exchanger<>();
        Exchanger<Integer> existExchanger = exchangerMap.putIfAbsent(id, newExchanger);
        return  (existExchanger == null) ? newExchanger : existExchanger;
    }

    public void setCalculator(Calculator calculator) {
        this.calculator = calculator;
    }
}
