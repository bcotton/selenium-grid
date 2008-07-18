package com.thoughtworks.selenium.grid.webserver;

import com.thoughtworks.selenium.grid.agent.RouteResolver;
import org.jbehave.classmock.UsingClassMock;
import org.jbehave.core.mock.Mock;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import org.junit.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class MainServletTest extends UsingClassMock {

    @Test
    public void aRouteResolverIsCreatedImplicitly() {
        assertNotNull(new MainServlet().routeResolver());
    }
    
    @Test
    public void serviceRendersThePageProcessedByTheController() throws IOException, ServletException {
        final Mock routeResolver;
        final Mock theResponse;
        final Mock theRequest;
        final MainServlet servlet;
        final Mock aController;

        theRequest = mock(HttpServletRequest.class);
        theResponse = mock(HttpServletResponse.class);
        aController = mock(Controller.class);
        routeResolver = mock(com.thoughtworks.selenium.grid.agent.RouteResolver.class);

        routeResolver.expects("resolve").with(theRequest).will(returnValue(aController));
        aController.expects("process").will(returnValue("aPage"));

        servlet = new MainServlet() {

            protected RouteResolver routeResolver() {
                return (RouteResolver) routeResolver;
            }

            public void render(String page, HttpServletResponse response) throws IOException {
                assertEquals("aPage", page);
                assertSame(theResponse, response);
            }
        };

        servlet.service((HttpServletRequest) theRequest, (HttpServletResponse) theResponse);
    }

    @Test
    public void rendersSetContentTypeToTextPlain() throws IOException {
        final Mock response;
        final Mock writer;

        writer = mock(PrintWriter.class);
        response = mock(HttpServletResponse.class);
        response.stubs("getWriter").will(returnValue(writer));
        response.expects("setContentType").with("text/plain");

        new MainServlet().render("", (HttpServletResponse) response);
    }
    
}
