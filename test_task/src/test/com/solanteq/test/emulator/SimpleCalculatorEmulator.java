package com.solanteq.test.emulator;

import com.solanteq.test.DataConsumer;
import com.solanteq.test.utils.TestUtils;

import java.util.UUID;

/**
 * @author azelentsov
 */
public class SimpleCalculatorEmulator extends CalculatorEmulator {

    @Override
    protected void beforeHook() {
        // empty for simple emulator
    }
}
