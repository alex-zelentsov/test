package com.solanteq.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static junit.framework.Assert.assertEquals;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {AppConfig.class})
@WebAppConfiguration
public class SyncPointTest {

    private static final int REQUEST_COUNT = 1000;

    @Autowired
    private SyncPoint syncPoint;

    private SecureRandom random = new SecureRandom();

    @Test
    public void testGetCalculatedValue() throws Exception {
        ExecutorService executorService = null;
        try {
            List<Callable<Integer>>  requests = new ArrayList<>();
            List<String> requestValues = new ArrayList<>();

            fillRequestsLists(requests, requestValues);

            executorService = Executors.newFixedThreadPool(REQUEST_COUNT);
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

    private void fillRequestsLists(List<Callable<Integer>> requests, List<String> requestValues) {
        for (int i = 0; i < REQUEST_COUNT; i++) {
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