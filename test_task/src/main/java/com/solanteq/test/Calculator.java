package com.solanteq.test;

import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.UUID;
import java.util.function.IntConsumer;


/**
 * This class represents external service that can calculate some secret value and provide asynchronous result.
 * It must not be modified while Test task implementation.
 *
 * @author dkochelaev
 */
@Service
public class Calculator {

    private ThreadPoolTaskExecutor taskExecutor;

    @PostConstruct
    public void init() {
        taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(32);
        taskExecutor.initialize();
    }

    /**
     * Calculates secret value and passes the result via callback asynchronously.
     *
     * @param value    base value for calculation
     * @param consumer сallback function to accept the result
     *
     * @return Unique request id that will be passed along in callback
     */
    public UUID calculate(String value, DataConsumer consumer) {
        UUID id = UUID.randomUUID();
        taskExecutor.execute(() -> consumer.accept(id, (value == null ? "" : value).length() * 42));
        return id;
    }
}
