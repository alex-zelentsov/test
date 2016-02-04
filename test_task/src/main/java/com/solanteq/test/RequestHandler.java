package com.solanteq.test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


/**
 * This class represents client that is requesting secret function and need synchronous result.
 * It must not be modified while Test task implementation.
 *
 * @author dkochelaev
 */
@Controller
public class RequestHandler {

    @Autowired
    private SyncPoint syncPoint;

    @RequestMapping(value = "/calculate", method = RequestMethod.GET, produces = "text/plain")
    public @ResponseBody String calculate(@RequestParam("value") String value) {
        return String.valueOf(syncPoint.getCalculatedValue(value));
    }
}