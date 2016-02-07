package com.solanteq.test.emulator;

import com.solanteq.test.DataConsumer;
import com.solanteq.test.SyncPoint;
import com.solanteq.test.utils.TestUtils;

import java.util.Random;
import java.util.UUID;
import java.util.logging.Logger;

/**
 * @author azelentsov
 */
public class RandomPingCalculatorEmulator extends CalculatorEmulator {
    private static final Logger LOGGER = Logger.getLogger(SyncPoint.class.getName());


    @Override
    protected void beforeHook() {
        try {
            Thread.sleep(new Random().nextInt(10_000));
        } catch (InterruptedException e) {
            LOGGER.info("calculate was interrupted");
            throw new AssertionError();
        }
    }
}
