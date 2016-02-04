package ru.notes.model.version;

/**
 * @author azelentsov
 */
public class Version {
    private final String projectName;
    private final String version;

    public Version(String projectName, String version) {
        this.projectName = projectName;
        this.version = version;
    }

    public String getProjectName() {
        return projectName;
    }

    public String getVersion() {
        return version;
    }
}
