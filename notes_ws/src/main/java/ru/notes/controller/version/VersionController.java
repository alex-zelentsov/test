package ru.notes.controller.version;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.notes.model.version.Version;

/**
 * @author azelentsov
 */
@RestController
public class VersionController implements IVersionController {
    @Value("${info.build.version}")
    private String version;
    @Value("${info.build.name}")
    private String name;

    @Override
    @RequestMapping(value = "/version", method = RequestMethod.GET)
    public Version getVersion() {
        return new Version(name, version);
    }
}
