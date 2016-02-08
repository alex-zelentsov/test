package com.solanteq.test;

import org.junit.Test;
import org.mockito.Mockito;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.when;

public class SyncPointTest {

    public static final int SLEEP_TIME = 5_000;
    
    private SyncPoint syncPoint = new SyncPoint();

    private SecureRandom random = new SecureRandom();

    @Test
    public void getFastCalculatedValueFor1Request() throws Exception {
        int requestCount = 1;

        setFastCalculatorMock();
        doTestWithRequestCount(requestCount);
    }

    @Test
    public void getFastCalculatedValueFor100Request() throws Exception {
        int requestCount = 100;

        setFastCalculatorMock();
        doTestWithRequestCount(requestCount);
    }

    @Test
    public void getFastCalculatedValueFor1_000Request() throws Exception {
        int requestCount = 1_000;

        setFastCalculatorMock();
        doTestWithRequestCount(requestCount);
    }

    @Test
    public void getRandomPingCalculatedValueFor1Request() throws Exception {
        int requestCount = 1;

        setRandomTimeAsyncCalculatorMock();
        doTestWithRequestCount(requestCount);
    }


    @Test
    public void getRandomPingCalculatedValueFor100Request() throws Exception {
        int requestCount = 100;

        setRandomTimeAsyncCalculatorMock();
        doTestWithRequestCount(requestCount);
    }

    @Test
    public void getRandomPingCalculatedValueFor1_000Request() throws Exception {
        int requestCount = 1_000;

        setRandomTimeAsyncCalculatorMock();
        doTestWithRequestCount(requestCount);
    }

    @Test
    public void getSlowCalculatedValueFor1Request() throws Exception {
        int requestCount = 1;

        setSlowAsyncCalculatorMock();
        doTestWithRequestCount(requestCount);
    }


    @Test
    public void getSlowCalculatedValueFor100Request() throws Exception {
        int requestCount = 100;

        setSlowAsyncCalculatorMock();
        doTestWithRequestCount(requestCount);
    }

    @Test
    public void getSlowPingCalculatedValueFor1_000Request() throws Exception {
        int requestCount = 1_000;

        setSlowAsyncCalculatorMock();
        doTestWithRequestCount(requestCount);
    }

    private void setRandomTimeAsyncCalculatorMock() {
        Calculator mockSimpleCalculator = createMock(PingType.RANDOM);

        syncPoint.setCalculator(mockSimpleCalculator);
    }

    private void setFastCalculatorMock() {
        Calculator mockSimpleCalculator = createMock(PingType.FAST);

        syncPoint.setCalculator(mockSimpleCalculator);
    }

    private void setSlowAsyncCalculatorMock() {
        Calculator mockSimpleCalculator  = createMock(PingType.SLOW);

        syncPoint.setCalculator(mockSimpleCalculator);
    }

    private Calculator createMock(PingType pingType) {
        ExecutorService executorService = Executors.newFixedThreadPool(32);
        Calculator mockSimpleCalculator  = Mockito.mock(Calculator.class);
        when(mockSimpleCalculator.calculate(any(String.class), any(DataConsumer.class))).then(x -> {
                    UUID uuid = UUID.randomUUID();
                    executorService.submit(() -> {
                        sleepForPing(pingType);
                        DataConsumer dataConsumer = (DataConsumer) x.getArguments()[1];
                        dataConsumer.accept(uuid, calculateSecretFormula((String) x.getArguments()[0]));
                    });

                    return uuid;
                }
        );
        return mockSimpleCalculator;
    }

    private void sleepForPing(PingType pingType) {
        try {
            switch (pingType) {
                case FAST:
                    break;
                case RANDOM:
                    Thread.sleep(random.nextInt(SLEEP_TIME));
                    break;
                case SLOW:
                    Thread.sleep(SLEEP_TIME);
            }
        } catch (InterruptedException e) {
            throw new AssertionError();
        }
    }

    private void doTestWithRequestCount(int requestCount) throws Exception {
        ExecutorService executorService = null;
        try {
            List<Callable<Integer>> requests = new ArrayList<>();
            List<String> requestValues = new ArrayList<>();

            fillRequestsLists(requests, requestValues, requestCount);

            executorService = Executors.newFixedThreadPool(32);
            List<Future<Integer>> responses = executorService.invokeAll(requests);

            checkResponse(requestValues, responses);

        } finally {
            if (executorService != null) {
                executorService.shutdown();
            }
        }
    }

    private void checkResponse(List<String> requestValues, List<Future<Integer>> responses) throws Exception {
        int i = 0;
        for(Future<Integer> response: responses) {
            Integer responseValue = response.get();
            assertNotNull(responseValue);
            assertEquals(Integer.valueOf(calculateSecretFormula(requestValues.get(i))), responseValue);
            i++;
        }
    }

    private void fillRequestsLists(List<Callable<Integer>> requests, List<String> requestValues, int requestCount) {
        for (int i = 0; i < requestCount; i++) {
            String requestValue = getRandomString();
            requestValues.add(requestValue);
            requests.add(() -> syncPoint.getCalculatedValue(requestValue));
        }
    }

    private String getRandomString() {
        String randomString = UUID.randomUUID().toString();
        int randomStringLength = random.nextInt(randomString.length());
        return randomString.substring(randomStringLength);
    }

    private int calculateSecretFormula(String requestValue) {
        return (requestValue == null ? "" : requestValue).length() * 42;
    }
}