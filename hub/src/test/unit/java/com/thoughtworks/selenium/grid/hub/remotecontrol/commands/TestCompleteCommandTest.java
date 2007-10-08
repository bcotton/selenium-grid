package com.thoughtworks.selenium.grid.hub.remotecontrol.commands;

import com.thoughtworks.selenium.grid.hub.remotecontrol.RemoteControlPool;
import com.thoughtworks.selenium.grid.hub.remotecontrol.RemoteControlProxy;
import com.thoughtworks.selenium.grid.hub.remotecontrol.Response;
import static junit.framework.Assert.assertEquals;
import org.jbehave.classmock.UsingClassMock;
import org.jbehave.core.mock.Mock;
import org.junit.Test;

import java.io.IOException;


public class TestCompleteCommandTest extends UsingClassMock {

    @Test
    public void executeReleasesTheRemoteControlWhenRemoteCallIsSuccessful() throws IOException {
        final TestCompleteCommand command;
        final Response expectedResponse;
        final Mock remoteControl;
        final Mock pool;

        expectedResponse = new Response(0, "");
        pool = mock(RemoteControlPool.class);
        remoteControl = mock(RemoteControlProxy.class);
        pool.expects("retrieve").with("a session id").will(returnValue(remoteControl));
        remoteControl.expects("forward").with("a query").will(returnValue(expectedResponse));
        pool.expects("releaseForSession").with("a session id");

        command = new TestCompleteCommand("a session id", "a query");
        assertEquals(expectedResponse, command.execute((RemoteControlPool) pool));
        verifyMocks();
    }

    @SuppressWarnings({"ThrowableInstanceNeverThrown"})
    @Test(expected = IOException.class)
    public void executeReleasesTheRemoteControlWhenRemoteCallFailsAndThereIsASession() throws IOException {
        final Mock remoteControl;
        final Mock pool;

        pool = mock(RemoteControlPool.class);
        remoteControl = mock(RemoteControlProxy.class);
        pool.expects("retrieve").with("a session id").will(returnValue(remoteControl));
        remoteControl.expects("forward").with("a query").will(throwException(new IOException()));
        pool.expects("releaseForSession").with("a session id");
        new TestCompleteCommand("a session id", "a query").execute((RemoteControlPool) pool);
        verifyMocks();
    }

}
