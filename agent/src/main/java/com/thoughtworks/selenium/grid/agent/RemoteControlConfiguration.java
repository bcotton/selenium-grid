package com.thoughtworks.selenium.grid.agent;

/**
 * Configuration used by Agent to launch a new Remote Control.
 */
public class RemoteControlConfiguration {

    private int port;

    public RemoteControlConfiguration() {
        this.port = 4444;
    }

    public int port() {
        return port;
    }

    public void setPort(int newPortNumber) {
        this.port = newPortNumber;
    }

    public String host() {
        return "0.0.0.0";
    }

    public String environment() {
        return "*chrome";
    }

    public String additionalSeleniumArgs() {
        return null;
    }

    public String hubURL() {
        return "http://localhost:4444";
    }
    
}
