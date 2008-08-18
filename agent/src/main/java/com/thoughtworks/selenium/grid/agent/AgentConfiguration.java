package com.thoughtworks.selenium.grid.agent;

/**
 * Selenium Grid Agent Configuration.
 */
public class AgentConfiguration {
    public static final int DEFAULT_PORT_NUMBER = 4443;
    public static final String DEFAULT_RC_WRAPPER_JAR_PATH =
            "lib/selenium-grid-remote-control-standalone-1.0.1.jar";
    public static final String DEFAULT_RC_JAR_PATH =
            "lib/selenium-server-1.0-SNAPSHOT.jar";

    private int port;
    private String remoteControlWrapperJarPath;
    private String remoteControlJarPath;

    public AgentConfiguration() {
        this.port = DEFAULT_PORT_NUMBER;
        this.remoteControlWrapperJarPath = DEFAULT_RC_WRAPPER_JAR_PATH;
        this.remoteControlJarPath = DEFAULT_RC_JAR_PATH;
    }

    public void setPort(int newPortNumber) {
        this.port = newPortNumber;
    }

    public Object getPort() {
        return port;
    }

    public String remoteControlWrapperJarPath() {
        return this.remoteControlWrapperJarPath;

    }

    public void setRemoteControlWrapperJarPath(String newRemoteControlWrapperJar) {
        this.remoteControlWrapperJarPath = newRemoteControlWrapperJar;
    }

    public Object remoteControlJarPath() {
        return this.remoteControlJarPath;
    }

    public void setRemoteControlJar(String newRemoteControlJarPath) {
        this.remoteControlJarPath = newRemoteControlJarPath;
    }
    
}
