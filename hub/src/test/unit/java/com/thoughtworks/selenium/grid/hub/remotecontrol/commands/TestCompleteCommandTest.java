package com.thoughtworks.selenium.grid.hub.remotecontrol.commands;

import com.thoughtworks.selenium.grid.hub.remotecontrol.RemoteControlPool;
import com.thoughtworks.selenium.grid.hub.remotecontrol.RemoteControlProxy;
import com.thoughtworks.selenium.grid.Response;
import com.thoughtworks.selenium.grid.HttpParameters;
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

        command = new TestCompleteCommand("a session id", new HttpParameters());
        expectedResponse = new Response(0, "");
        pool = mock(RemoteControlPool.class);
        remoteControl = mock(RemoteControlProxy.class);
        pool.expects("retrieve").with("a session id").will(returnValue(remoteControl));
        remoteControl.expects("forward").with(command.parameters()).will(returnValue(expectedResponse));
        pool.expects("releaseForSession").with("a session id");

        assertEquals(expectedResponse, command.execute((RemoteControlPool) pool));
        verifyMocks();
    }

    @SuppressWarnings({"ThrowableInstanceNeverThrown"})
    @Test(expected = IOException.class)
    public void executeReleasesTheRemoteControlWhenRemoteCallFailsAndThereIsASession() throws IOException {
        final TestCompleteCommand command;
        final Mock remoteControl;
        final Mock pool;
        
        command = new TestCompleteCommand("a session id", new HttpParameters());
        pool = mock(RemoteControlPool.class);
        remoteControl = mock(RemoteControlProxy.class);
        pool.expects("retrieve").with("a session id").will(returnValue(remoteControl));
        remoteControl.expects("forward").with(command.parameters()).will(throwException(new IOException()));
        pool.expects("releaseForSession").with("a session id");
        command.execute((RemoteControlPool) pool);
        verifyMocks();
    }

}
