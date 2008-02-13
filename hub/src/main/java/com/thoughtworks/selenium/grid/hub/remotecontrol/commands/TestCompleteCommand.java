package com.thoughtworks.selenium.grid.hub.remotecontrol.commands;

import com.thoughtworks.selenium.grid.Response;
import com.thoughtworks.selenium.grid.hub.remotecontrol.RemoteControlPool;
import com.thoughtworks.selenium.grid.HttpParameters;

import java.io.IOException;

/**
 * Selenese command marking the end of a Selenese session.
 */
public class TestCompleteCommand extends SeleneseCommand {

    public TestCompleteCommand(String sessionId, HttpParameters parameters) {
        super(sessionId, parameters);
    }


    public Response execute(RemoteControlPool pool) throws IOException {
        try {
            return super.execute(pool);
        } finally {
          pool.releaseForSession(sessionId());
        }
    }

}
