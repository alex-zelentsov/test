package com.solanteq.test;

import com.solanteq.test.emulator.RandomPingCalculatorEmulator;
import com.solanteq.test.emulator.SimpleCalculatorEmulator;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static junit.framework.Assert.assertEquals;

public class SyncPointTest {

    private SyncPoint syncPoint;

    private SecureRandom random = new SecureRandom();

    @Before
    public void setUp() {
        syncPoint = new SyncPoint();
        syncPoint.setCalculator(new SimpleCalculatorEmulator());
    }

    @Test
    public void getCalculatedValueFor1Request() throws Exception {
        int requestCount = 1;
        syncPoint.setCalculator(new SimpleCalculatorEmulator());

        doTestWithRequestCount(requestCount);
    }

    @Test
    public void getCalculatedValueFor100Request() throws Exception {
        int requestCount = 100;
        syncPoint.setCalculator(new SimpleCalculatorEmulator());

        doTestWithRequestCount(requestCount);
    }

    @Test
    public void getCalculatedValueFor10_000Request() throws Exception {
        int requestCount = 10_000;
        syncPoint.setCalculator(new SimpleCalculatorEmulator());

        doTestWithRequestCount(requestCount);
    }

    @Test
    public void getRandomPingCalculatedValueFor1Request() throws Exception {
        int requestCount = 1;
        syncPoint.setCalculator(new RandomPingCalculatorEmulator());

        doTestWithRequestCount(requestCount);
    }

    @Test
    public void getRandomPingCalculatedValueFor100Request() throws Exception {
        int requestCount = 100;
        syncPoint.setCalculator(new RandomPingCalculatorEmulator());

        doTestWithRequestCount(requestCount);
    }

    @Test
    @Ignore
    public void getRandomPingCalculatedValueFor10_000Request() throws Exception {
        int requestCount = 10_000;
        syncPoint.setCalculator(new RandomPingCalculatorEmulator());

        doTestWithRequestCount(requestCount);
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
            Integer expectedValue = calculateSecretFormula(requestValues, i);
            i++;

            assertEquals(expectedValue, responseValue);
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

    private int calculateSecretFormula(List<String> requestValues, int i) {
        /*
         * Done for simplify test task,
         * In test for production system for Calculator service need to create emulator service
         * and use SyncPoint with @Autowired Calculator calculator -> @Autowired Emulator emulator
         */
        return (requestValues.get(i) == null ? "" : requestValues.get(i)).length() * 42;
    }
}