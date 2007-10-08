package com.thoughtworks.selenium.grid.hub.remotecontrol.commands;

import com.thoughtworks.selenium.grid.hub.remotecontrol.RemoteControlPool;
import com.thoughtworks.selenium.grid.hub.remotecontrol.RemoteControlProxy;
import com.thoughtworks.selenium.grid.hub.remotecontrol.Response;

import java.io.IOException;

/**
 * Generic Selenese command
 */
public class SeleneseCommand {

    private final String queryString;
    private final String sessionId;

    public SeleneseCommand(String sessionId, String queryString) {
        this.queryString = queryString;
        this.sessionId = sessionId;
    }

    public String queryString() {
        return queryString;
    }

    public String sessionId() {
        return sessionId;
    }

    public Response execute(RemoteControlPool pool) throws IOException {
        final RemoteControlProxy remoteControl;

        if (null == sessionId) {
            return new Response("Selenium Driver error: No sessionId provided for command '" + queryString + "'");
        }
        remoteControl = pool.retrieve(sessionId());
        return remoteControl.forward(queryString());
    }

}
