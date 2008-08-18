package com.thoughtworks.selenium.grid.webserver;

import org.jbehave.classmock.UsingClassMock;
import org.jbehave.core.mock.Mock;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.Map;
import java.util.HashMap;

import com.thoughtworks.selenium.grid.HttpParameters;

public class MainServletTest extends UsingClassMock {

    /*
     * Named resolver we can use in the unit tests.
     */
    public static class TestRouteResolver implements RouteResolver {
        public Resource resolve(HttpServletRequest request) {
            throw new IllegalStateException("Should not have been called");
        }
    }

    /*
     * Named resolver with private constructor we can use in the unit tests.
     */
    public static class TestPrivateRouteResolver implements RouteResolver {
        private TestPrivateRouteResolver() {
        }

        public Resource resolve(HttpServletRequest request) {
            throw new IllegalStateException("Should not have been called");
        }
    }

    @Test
    public void aRouteResolverIsbasedOnTheValueOfTheInitParameter() {
        final MainServlet servlet;
        final Mock config;

        config = mock(ServletConfig.class);
        config.stubs("getInitParameter").with("route_resolver").will(returnValue(TestRouteResolver.class.getName()));
        servlet = new MainServlet() {

            public ServletConfig getServletConfig() {
                return (ServletConfig) config;
            }

        };
        assertTrue(servlet.routeResolver() instanceof TestRouteResolver);
    }

    @Test(expected = RuntimeException.class)
    public void aRuntimeExceptionIsThrownWhenTheRouteResolverParameterPointToAClassThatCannotBeFound() {
        final Mock config;

        config = mock(ServletConfig.class);
        config.stubs("getInitParameter").with("route_resolver").will(returnValue("does.not.Exist"));
        new MainServlet() {

            public ServletConfig getServletConfig() {
                return (ServletConfig) config;
            }

        }.routeResolver();
    }

    @Test(expected = RuntimeException.class)
    public void aRuntimeExceptionIsThrownWhenTheRouteResolverParameterPointToAClasswhoseConstructorCannotBeAccessed() {
        final Mock config;

        config = mock(ServletConfig.class);
        config.stubs("getInitParameter").with("route_resolver")
              .will(returnValue(TestPrivateRouteResolver.class.getName()));
        new MainServlet() {

            public ServletConfig getServletConfig() {
                return (ServletConfig) config;
            }

        }.routeResolver();
    }

    @Test(expected = RuntimeException.class)
    public void aRuntimeExceptionIsThrownWhenTheRouteResolverParameterPointToAClassThatHasNoNoArgConstructor() {
        final Mock config;

        config = mock(ServletConfig.class);
        config.stubs("getInitParameter").with("route_resolver").will(returnValue(Field.class.getName()));
        new MainServlet() {

            public ServletConfig getServletConfig() {
                return (ServletConfig) config;
            }

        }.routeResolver();
    }

    @Test
    public void serviceRendersThePageProcessedByTheResource() throws IOException, ServletException {
        final Mock routeResolver;
        final Mock theResponse;
        final Mock theRequest;
        final MainServlet servlet;
        final Mock aResource;

        theRequest = mock(HttpServletRequest.class);
        theResponse = mock(HttpServletResponse.class);
        aResource = mock(Resource.class);
        routeResolver = mock(com.thoughtworks.selenium.grid.webserver.RouteResolver.class);

        routeResolver.expects("resolve").with(theRequest).will(returnValue(aResource));
        aResource.expects("process").will(returnValue("aPage"));

        servlet = new MainServlet() {

            public void render(String page, HttpServletResponse response) throws IOException {
                assertEquals("aPage", page);
                assertSame(theResponse, response);
            }

            protected RouteResolver routeResolver() {
                return (RouteResolver) routeResolver;
            }
        };

        servlet.service((HttpServletRequest) theRequest, (HttpServletResponse) theResponse);
    }

    @Test
    public void serviceDelegatesProcessingToResourcesPassingInHttpParameters() throws IOException, ServletException {
        final Mock routeResolver;
        final Mock theResponse;
        final Mock theRequest;
        final MainServlet servlet;
        final Resource aResource;
        final Map<String,String[]> parameterMap;


        aResource = new Resource() {
            public String process(HttpParameters params) {
                assertEquals(2, params.names().size());
                params.get("firstParameter");
                assertEquals("firstValue", params.get("firstParameter"));
                assertEquals("secondValue", params.get("secondParameter"));
                return null;
            }
        };
        theRequest = mock(HttpServletRequest.class);
        theResponse = mock(HttpServletResponse.class);
        parameterMap = new HashMap<String, String[]>();
        parameterMap.put("firstParameter", new String[] { "firstValue" });
        parameterMap.put("secondParameter", new String[] { "secondValue" });

        theRequest.stubs("getParameterMap").will(returnValue(parameterMap));
        routeResolver = mock(com.thoughtworks.selenium.grid.webserver.RouteResolver.class);
        routeResolver.expects("resolve").with(theRequest).will(returnValue(aResource));

        servlet = new MainServlet() {

            public void render(String page, HttpServletResponse response) throws IOException {}            

            protected RouteResolver routeResolver() {
                return (RouteResolver) routeResolver;
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
