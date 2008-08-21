package com.thoughtworks.selenium.grid.hub.management;

import com.thoughtworks.selenium.grid.hub.HubRegistry;
import org.jbehave.classmock.UsingClassMock;
import org.jbehave.core.mock.Mock;
import org.junit.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LifecyleManagerServletTest extends UsingClassMock {

    @Test
    public void shutdownHubWhenPostingAShutdownAction() throws IOException, ServletException {
        final Mock lifecyleManager;
        final LifecycleManagerServlet servlet;
        final Mock registry;
        final Mock request;
        final Mock expectedResponse;

        request = mock(HttpServletRequest.class);
        expectedResponse = mock(HttpServletResponse.class);
        registry = mock(HubRegistry.class);
        lifecyleManager = mock(LifecycleManager.class);
        servlet = new LifecycleManagerServlet() {

            protected HubRegistry registry() {
                return (HubRegistry) registry;
            }

        };

        request.stubs("getParameter").with("action").will(returnValue("shutdown"));
        registry.stubs("lifecycleManager").will(returnValue(lifecyleManager));
        lifecyleManager.expects("shutdown");

        servlet.doPost((HttpServletRequest) request, (HttpServletResponse) expectedResponse);
        verifyMocks();        
    }

    @Test
    public void doNotShutdownHubWhenPostedActionIsNotShutdown() throws IOException, ServletException {
        final Mock lifecyleManager;
        final LifecycleManagerServlet servlet;
        final Mock registry;
        final Mock request;
        final Mock expectedResponse;

        request = mock(HttpServletRequest.class);
        expectedResponse = mock(HttpServletResponse.class);
        registry = mock(HubRegistry.class);
        lifecyleManager = mock(LifecycleManager.class);
        servlet = new LifecycleManagerServlet() {

            protected HubRegistry registry() {
                return (HubRegistry) registry;
            }

        };

        request.stubs("getParameter").with("action").will(returnValue("random action"));
        registry.stubs("lifecycleManager").will(returnValue(lifecyleManager));
        lifecyleManager.expects("shutdown").never();

        servlet.doPost((HttpServletRequest) request, (HttpServletResponse) expectedResponse);
        verifyMocks();
    }

}
