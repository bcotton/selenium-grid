package com.thoughtworks.selenium.grid.agent;

import org.jbehave.classmock.UsingClassMock;
import org.jbehave.core.mock.Mock;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;

import com.thoughtworks.selenium.grid.webserver.Resource;
import com.thoughtworks.selenium.grid.webserver.InvalidRouteException;

public class RouteResolverTest extends UsingClassMock {

    @Test
    public void resolveRaisesAnExceptionWhenRouteIsUnknown() {
        Mock request;

        request = mock(HttpServletRequest.class);
        request.stubs("getPathInfo").will(returnValue("/invalid/path"));
        try {
          new RouteResolver().resolve((HttpServletRequest) request);
          fail("Did not catch InvalidRouteException a expected");
        } catch (InvalidRouteException e) {
          assertEquals("/invalid/path", e.path());
        }
    }

    @Test
    public void rootPathResolvesToAnAgentResourceInstance() {
        Mock request;
        Resource resource;

        request = mock(HttpServletRequest.class);
        request.stubs("getPathInfo").will(returnValue("/"));
        resource = new RouteResolver().resolve((HttpServletRequest) request);
        assertTrue(resource instanceof AgentResource);
    }
    @Test

    public void slashRemoteControlResolvesToARemoteControlResourceInstance() {
        Mock request;
        Resource resource;

        request = mock(HttpServletRequest.class);
        request.stubs("getPathInfo").will(returnValue("/remote-controls"));
        request.stubs("getMethod").will(returnValue("POST"));
        resource = new RouteResolver().resolve((HttpServletRequest) request);
        assertTrue(resource instanceof RemoteControlCommand);
    }

}
