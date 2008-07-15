package com.thoughtworks.selenium.grid.hub.remotecontrol.commands;

import com.thoughtworks.selenium.grid.hub.Environment;
import com.thoughtworks.selenium.grid.HttpParameters;
import com.thoughtworks.selenium.grid.Response;
import com.thoughtworks.selenium.grid.hub.remotecontrol.DynamicRemoteControlPool;
import com.thoughtworks.selenium.grid.hub.remotecontrol.RemoteControlProxy;
import com.thoughtworks.selenium.grid.hub.remotecontrol.RemoteControlPool;
import static junit.framework.Assert.assertEquals;
import org.jbehave.core.mock.Mock;
import org.jbehave.classmock.UsingClassMock;
import org.junit.Test;

import java.io.IOException;


public class NewBrowserSessionCommandTest extends UsingClassMock {

    @Test
    public void environmentReturnsTheEnvironmentProvidedInTheConstructor() {
        final Environment anEnvironment;

        anEnvironment = new Environment("", null);
        assertEquals(anEnvironment, new NewBrowserSessionCommand(anEnvironment, null).environment());
    }

    @Test
    public void sessionIdIsAlwaysNull() {
        assertEquals(null, new NewBrowserSessionCommand(null, null).sessionId());
    }

    @Test
    public void parseSessionIdReturnsTheSessionIdWhenResponseIsSuccessful() {
        assertEquals("22207", new NewBrowserSessionCommand(null, null).parseSessionId("OK,22207"));
    }

    @Test
    public void parseSessionIdReturnsTheSessionIdWhenSessionIdIsAGUID() {
        assertEquals("36d3cc124c8b4b4f8c53c5b600052da3", new NewBrowserSessionCommand(null, null).parseSessionId("OK,36d3cc124c8b4b4f8c53c5b600052da3"));
    }

    @Test
    public void parseSessionIdReturnsNullWhenResponseIsNotSuccessful() {
        assertEquals(null, new NewBrowserSessionCommand(null, null).parseSessionId(""));
    }

    @Test
    public void executeReserveAndThenAssociateARemoteControlWithTheSession() throws IOException {
        final NewBrowserSessionCommand command;
        final Mock remoteControl;
        final Mock pool;
        final Response expectedResponse;
        final Environment environment;
        final HttpParameters parameters;

        expectedResponse = new Response(0, "OK,1234");
        parameters = new HttpParameters();
        pool = mock(DynamicRemoteControlPool.class);
        remoteControl = mock(RemoteControlProxy.class);
        environment = new Environment("an environment", "*browser");
        command = new NewBrowserSessionCommand(environment, parameters);
        remoteControl.expects("forward").with(parameters).will(returnValue(expectedResponse));
        pool.expects("reserve").with(environment).will(returnValue(remoteControl));
        pool.expects("associateWithSession").with(anything(), eq("1234"));  // TODO with(remoteControl, ...)

        assertEquals(expectedResponse, command.execute((RemoteControlPool) pool));
        verifyMocks();
    }

    @Test
    public void executeReturnsAnErrorResponseWhenSessionCannotBeCreated() throws IOException {
        final NewBrowserSessionCommand command;
        final Mock remoteControl;
        final Environment environment;
        final Mock pool;
        final Response response;

        pool = mock(DynamicRemoteControlPool.class);
        remoteControl = mock(RemoteControlProxy.class);
        environment = new Environment("an environment", "*browser");
        command = new NewBrowserSessionCommand(environment, new HttpParameters());
        pool.expects("reserve").with(environment).will(returnValue(remoteControl));
        remoteControl.expects("forward").with(command.parameters()).will(returnValue(new Response(500, "")));

        response = command.execute((RemoteControlPool) pool);
        assertEquals(200, response.statusCode());
        assertEquals("ERROR: Could not retrieve a new session", response.body());
        verifyMocks();
    }

    @Test
    public void executeReleaseRemoteControlWhenSessionCannotBeCreated() throws IOException {
        final NewBrowserSessionCommand command;
        final Environment environment;
        final Mock remoteControl;
        final Response response;
        final Mock pool;

        pool = mock(DynamicRemoteControlPool.class);
        remoteControl = mock(RemoteControlProxy.class);
        environment = new Environment("an environment", "*browser");
        command = new NewBrowserSessionCommand(environment, new HttpParameters());
        pool.stubs("reserve").will(returnValue(remoteControl));
        pool.expects("release").with(remoteControl);
        remoteControl.stubs("forward").will(returnValue(new Response(500, "")));

        response = command.execute((RemoteControlPool) pool);
        assertEquals(200, response.statusCode());
        assertEquals("ERROR: Could not retrieve a new session", response.body());
        verifyMocks();
    }

    @SuppressWarnings({"ThrowableInstanceNeverThrown"})
    @Test
    public void executeReturnsAnErrorResponseWhenThereIsANetworkError() throws IOException {
        final NewBrowserSessionCommand command;
        final Environment environment;
        final Mock remoteControl;
        final Response response;
        final Mock pool;

        pool = mock(DynamicRemoteControlPool.class);
        remoteControl = mock(RemoteControlProxy.class);
        environment = new Environment("an environment", "*browser");
        command = new NewBrowserSessionCommand(environment, new HttpParameters());
        remoteControl.expects("forward").with(command.parameters()).will(throwException(new IOException("an error message")));
        pool.expects("reserve").with(environment).will(returnValue(remoteControl));
        pool.stubs("release").with(remoteControl);

        response = command.execute((RemoteControlPool) pool);
        assertEquals(200, response.statusCode());
        assertEquals("ERROR: an error message", response.body());
        verifyMocks();
    }

    @SuppressWarnings({"ThrowableInstanceNeverThrown"})
    @Test
    public void executeReleasesReservedRemoteControlAndReturnsAnErrorResponseWhenThereIsANetworkError() throws IOException {
        final NewBrowserSessionCommand command;
        final Mock remoteControl;
        final Environment environment;
        final Mock pool;

        pool = mock(DynamicRemoteControlPool.class);
        remoteControl = mock(RemoteControlProxy.class);
        environment = new Environment("an environment", "*browser");
        command = new NewBrowserSessionCommand(environment, new HttpParameters());
        remoteControl.stubs("forward").will(throwException(new IOException("an error message")));
        pool.stubs("reserve").will(returnValue(remoteControl));
        pool.expects("release").with(remoteControl);

        command.execute((RemoteControlPool) pool);
        verifyMocks();
    }

    @Test
    public void executeReturnsAnErrorResponseWhenNoRemoteControlCanBeReserved() throws IOException {
        final NewBrowserSessionCommand command;
        final Environment environment;
        final Response response;
        final Mock pool;

        pool = mock(DynamicRemoteControlPool.class);
        environment = new Environment("an environment", "*browser");
        command = new NewBrowserSessionCommand(environment, null);
        pool.expects("reserve").with(environment).will(returnValue(null));

        response = command.execute((RemoteControlPool) pool);
        assertEquals(200, response.statusCode());
        assertEquals("ERROR: No available remote control for environment 'an environment'", response.body());
        verifyMocks();
    }

}
