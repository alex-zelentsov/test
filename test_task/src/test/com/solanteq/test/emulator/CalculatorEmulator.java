package com.solanteq.test.emulator;

import com.solanteq.test.Calculator;
import com.solanteq.test.DataConsumer;
import com.solanteq.test.utils.TestUtils;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.UUID;

/**
 * @author azelentsov
 */
public abstract class CalculatorEmulator extends Calculator {
    protected ThreadPoolTaskExecutor taskExecutor;

    public CalculatorEmulator() {
        taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(32);
        taskExecutor.initialize();
    }

    @Override
    public UUID calculate(String value, DataConsumer consumer) {
        UUID id = UUID.randomUUID();
        taskExecutor.execute(() -> {
            beforeHook();
            consumer.accept(id, TestUtils.getResult(value));
        });
        return id;
    }

    protected abstract void beforeHook();

}
