package com.thoughtworks.selenium.grid.agent;

import org.jbehave.classmock.UsingClassMock;
import org.jbehave.core.mock.Mock;
import static org.junit.Assert.assertEquals;
import org.junit.Test;
import org.junit.Assert;

import javax.servlet.http.HttpServletRequest;

import com.thoughtworks.selenium.grid.webserver.Resource;
import com.thoughtworks.selenium.grid.webserver.InvalidRouteException;

public class RouteResolverTest extends UsingClassMock {

    @Test
    public void resolveRaisesAnExceptionWhenRouteIsUnknown() {
        Mock request;

        request = mock(HttpServletRequest.class);
        request.stubs("getPathInfo").will(returnValue("/a/path"));
        try {
          new RouteResolver().resolve((HttpServletRequest) request);
          fail("Did not catch InvalidRouteException a expected");
        } catch (InvalidRouteException e) {
          assertEquals("/a/path", e.path());
        }
    }

    @Test
    public void rootPathResolvesToAnAgentControllerInstance() {
        Mock request;
        Resource resource;

        request = mock(HttpServletRequest.class);
        request.stubs("getPathInfo").will(returnValue("/"));
        resource = new RouteResolver().resolve((HttpServletRequest) request);
        Assert.assertTrue(resource instanceof AgentResource);
    }

}
