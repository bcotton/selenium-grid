package com.thoughtworks.selenium.grid.hub.remotecontrol;

import com.thoughtworks.selenium.grid.HttpParameters;
import com.thoughtworks.selenium.grid.Response;
import com.thoughtworks.selenium.grid.HttpClient;

import java.io.IOException;

/**
 * Local interface to a real remote control running somewhere in the grid.
 */
public class RemoteControlProxy {

    private final HttpClient httpClient;
    private final String environment;
    private final String host;
    private final int port;

    public RemoteControlProxy(String host, int port, String environment, HttpClient httpClient) {
        if (null == host) {
            throw new IllegalArgumentException("host cannot be null");
        }
        if (null == environment) {
            throw new IllegalArgumentException("environment cannot be null");
        }
        this.host = host;
        this.port = port;
        this.environment = environment;
        this.httpClient = httpClient;
    }

    public String host() {
        return host;
    }

    public int port() {
        return port;
    }

    public String environment() {
        return environment;
    }

    public String remoteControlURL() {
        return "http://" + host + ":" + port + "/selenium-server/driver/";
    }

    public Response forward(HttpParameters parameters) throws IOException {
        return httpClient.post(remoteControlURL(), parameters);
    }

    public String toString() {
        return "[RemoteControlProxy " + host + ":" + port + "]";
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        final RemoteControlProxy otherRemoteControl = (RemoteControlProxy) other;
        return host.equals(otherRemoteControl.host)
                && port == otherRemoteControl.port;
    }

    public int hashCode() {
        return (host + port).hashCode();
    }

}
