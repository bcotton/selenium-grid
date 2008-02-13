package com.thoughtworks.selenium.grid.hub.remotecontrol.commands;

import com.thoughtworks.selenium.grid.hub.remotecontrol.RemoteControlPool;
import com.thoughtworks.selenium.grid.hub.remotecontrol.RemoteControlProxy;
import com.thoughtworks.selenium.grid.Response;
import com.thoughtworks.selenium.grid.HttpParameters;
import static junit.framework.Assert.assertEquals;
import org.jbehave.core.mock.Mock;
import org.jbehave.classmock.UsingClassMock;
import org.junit.Test;

import java.io.IOException;


public class SeleneseCommandTest extends UsingClassMock {


    @Test
    public void parametersReturnsTheParametersProvidedToConstructor() {
        HttpParameters theParameters = new HttpParameters();
        assertEquals(theParameters, new SeleneseCommand("", theParameters).parameters());
    }

    @Test
    public void sessionIdReturnsTheSessionIdProvidedToConstructor() {
        assertEquals("a session id", new SeleneseCommand("a session id", null).sessionId());
    }

    @Test
    public void executeForwardsTheRequestToTheRemoteControl() throws Exception {
        final Mock remoteControl;
        final SeleneseCommand command;
        final Response expectedResponse;
        final Mock pool;

        command = new SeleneseCommand("a session id", new HttpParameters());
        expectedResponse = new Response(0, "");
        remoteControl = mock(RemoteControlProxy.class);
        pool = mock(RemoteControlPool.class);
        pool.expects("retrieve").with("a session id").will(returnValue(remoteControl));
        remoteControl.expects("forward").with(command.parameters()).will(returnValue(expectedResponse));

        assertEquals(expectedResponse, command.execute((RemoteControlPool) pool));
        verifyMocks();
    }

    @Test
    public void executeReturnsAnErrorResponseWhenNoSessionIdIsProvided() throws IOException {
        final HttpParameters parameters;
        final Response response;

        parameters = new HttpParameters();
        parameters.put("foo", "bar");
        response = new SeleneseCommand(null, parameters).execute(null);
        assertEquals("ERROR: Selenium Driver error: No sessionId provided for command 'foo => \"bar\"'", response.body());
    }

}
