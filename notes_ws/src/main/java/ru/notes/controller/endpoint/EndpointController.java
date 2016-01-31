package ru.notes.controller.endpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.Map;
import java.util.Set;

/**
 * @author azelentsov
 */
@RestController
public class EndpointController implements IEndpointController {
    private static final Logger LOGGER = LoggerFactory.getLogger(EndpointController.class);

    private RequestMappingHandlerMapping handlerMapping;

    @Autowired
    public EndpointController(RequestMappingHandlerMapping handlerMapping) {
        this.handlerMapping = handlerMapping;
    }

    @Override
    @RequestMapping(value="/endpoints", method= RequestMethod.GET)
    public Set<RequestMappingInfo> getEndpoints() {
        Map<RequestMappingInfo, HandlerMethod> handlerMethodsMap = handlerMapping.getHandlerMethods();
        Set<RequestMappingInfo> endpointsInfo = handlerMethodsMap.keySet();
        LOGGER.info("Endpoints info has been formed");
        return endpointsInfo;
    }
}
