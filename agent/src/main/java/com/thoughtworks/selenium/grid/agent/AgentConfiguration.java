package com.thoughtworks.selenium.grid.agent;

/**
 * Selenium Grid Agent Configuration. 
 */
public class AgentConfiguration {
    public static int DEFAULT_PORT_NUMBER = 4443;
    private int port;

    public AgentConfiguration() {
        this.port = DEFAULT_PORT_NUMBER;
    }

    public void setPort(int newPortNumber) {
        this.port = newPortNumber;
    }

    public Object getPort() {
        return port;
    }
}
