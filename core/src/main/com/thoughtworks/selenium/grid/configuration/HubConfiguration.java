package com.thoughtworks.selenium.grid.configuration;

/**
 * Selenium Hub Configuration.
 */
public class HubConfiguration {

    private int port;
    private EnvironmentConfiguration[] environments;

    public HubConfiguration() {
        setPort(4444);
        environments = new EnvironmentConfiguration[] {};
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public EnvironmentConfiguration[] getEnvironments() {
        return environments;
    }

    public void setEnvironments(EnvironmentConfiguration[] environments) {
        this.environments = environments;
    }

}