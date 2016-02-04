package ru.notes.controller.endpoint;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;

import java.util.Set;

/**
 * @author azelentsov
 */
public interface IEndpointController {
    Set<RequestMappingInfo> getEndpoints();
}
