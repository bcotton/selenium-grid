package com.thoughtworks.selenium.grid.hub.management;

import com.thoughtworks.selenium.grid.hub.ApplicationRegistry;
import com.thoughtworks.selenium.grid.hub.remotecontrol.DynamicRemoteControlPool;
import com.thoughtworks.selenium.grid.hub.remotecontrol.RemoteControlProxy;
import static junit.framework.Assert.assertSame;
import org.jbehave.classmock.UsingClassMock;
import org.jbehave.core.mock.Mock;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class RegistrationServletTest extends UsingClassMock {

    @Test
    public void registerSubmittedRemoteControlPool() throws IOException {
        final RemoteControlProxy expectedRemoteControl;
        final RegistrationServlet servlet;
        final Mock remoteControlPool;
        final Mock registry;
        final Mock request;
        final Mock expectedResponse;

        request = mock(HttpServletRequest.class);
        expectedResponse = mock(HttpServletResponse.class);
        registry = mock(ApplicationRegistry.class);
        remoteControlPool = mock(DynamicRemoteControlPool.class);
        expectedRemoteControl = new RemoteControlProxy("a host", 24, "an environment", 1, null);
        servlet = new RegistrationServlet() {

            protected ApplicationRegistry registry() {
                return (ApplicationRegistry) registry;
            }

            protected void witeSuccessfulResponse(HttpServletResponse response) throws IOException {
                assertSame(expectedResponse, response);
            }
        };

        request.stubs("getParameter").with("host").will(returnValue(expectedRemoteControl.host()));
        request.stubs("getParameter").with("port").will(returnValue("" + expectedRemoteControl.port()));
        request.stubs("getParameter").with("environment").will(returnValue(expectedRemoteControl.environment()));

        registry.expects("remoteControlPool").will(returnValue(remoteControlPool));
        remoteControlPool.expects("register").with(eq(expectedRemoteControl));

        servlet.process((HttpServletRequest) request, (HttpServletResponse) expectedResponse);
        verifyMocks();
    }
}
