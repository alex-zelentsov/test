package com.solanteq.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.Exchanger;
import java.util.logging.Logger;


/**
 * This class performs synchronisation.
 *
 * @author dkochelaev
 */
@Service
public class SyncPoint {
    private static final Logger LOGGER = Logger.getLogger(SyncPoint.class.getName());

    @Autowired
    private Calculator calculator;

    /**
     * Method performs synchronisation via callback method
     * Get value from callback
     *
     * @param value from request handler
     * @return value from callback
     * @throws CalculatingInterruptedException if the current thread was
     *         interrupted while waiting result from callback
     */
    public int getCalculatedValue(String value) {
        Exchanger<Integer> exchanger = new Exchanger<>();
        calculator.calculate(value, (id, result) -> callback(id, result, exchanger));
        Integer exchange;
        try {
            exchange = exchanger.exchange(null);
        } catch (InterruptedException e) {
            LOGGER.info("Calculation value process was interrupted: " + e);
            throw new CalculatingInterruptedException(e);
        }
        return exchange;
    }

    private void callback(UUID id, int result, Exchanger<Integer> exchanger) {
        try {
            exchanger.exchange(result);
        } catch (InterruptedException e) {
            LOGGER.info("Callback was interrupted: " + e);
            Thread.currentThread().interrupt();
        }
    }
}
