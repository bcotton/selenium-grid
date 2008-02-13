package com.thoughtworks.selenium.grid.hub.remotecontrol.commands;

import com.thoughtworks.selenium.grid.hub.remotecontrol.RemoteControlPool;
import com.thoughtworks.selenium.grid.hub.remotecontrol.RemoteControlProxy;
import com.thoughtworks.selenium.grid.Response;
import com.thoughtworks.selenium.grid.HttpParameters;

import java.io.IOException;

/**
 * Generic Selenese command
 */
public class SeleneseCommand {

    private final String sessionId;
    private final HttpParameters parameters;

    public SeleneseCommand(String sessionId, HttpParameters parameters) {
        this.sessionId = sessionId;
        this.parameters = parameters;
    }

    public String sessionId() {
        return sessionId;
    }

    public HttpParameters parameters() {
        return parameters;
    }

    public Response execute(RemoteControlPool pool) throws IOException {
        final RemoteControlProxy remoteControl;

        if (null == sessionId) {
            return new Response("Selenium Driver error: No sessionId provided for command '" + parameters.toString() + "'");
        }
        remoteControl = pool.retrieve(sessionId());
        return remoteControl.forward(parameters());
    }

}
