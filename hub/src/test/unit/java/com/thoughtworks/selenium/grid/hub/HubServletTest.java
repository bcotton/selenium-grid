package com.thoughtworks.selenium.grid.hub;

import com.thoughtworks.selenium.grid.hub.remotecontrol.DynamicRemoteControlPool;
import com.thoughtworks.selenium.grid.hub.remotecontrol.RemoteControlProxy;
import com.thoughtworks.selenium.grid.Response;
import com.thoughtworks.selenium.grid.HttpParameters;
import static junit.framework.Assert.assertEquals;
import org.jbehave.classmock.UsingClassMock;
import org.jbehave.core.mock.Mock;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;


public class HubServletTest extends UsingClassMock {
    
    @Test
    public void replySetContentTypeAsPlainText() throws IOException {
        final Response remoteControlResponse;
        final Mock servletResponse;

        remoteControlResponse = new Response(123, "");
        servletResponse = mock(HttpServletResponse.class);
        servletResponse.expects("setContentType").with("text/plain");
        servletResponse.expects("getWriter").will(returnValue(new PrintWriter(new StringWriter(100))));

        new HubServlet().reply((HttpServletResponse) servletResponse, remoteControlResponse);
        verifyMocks();
    }

    @Test
    public void replySetCharacterEncodingToUTF8() throws IOException {
        final Response remoteControlResponse;
        final Mock servletResponse;

        remoteControlResponse = new Response(123, "");
        servletResponse = mock(HttpServletResponse.class);
        servletResponse.expects("setCharacterEncoding").with("UTF-8");
        servletResponse.expects("getWriter").will(returnValue(new PrintWriter(new StringWriter(100))));

        new HubServlet().reply((HttpServletResponse) servletResponse, remoteControlResponse);
        verifyMocks();
    }

    @Test
    public void replySetStatusFromRemoteControlOnTheServletResponse() throws IOException {
        final Response remoteControlResponse;
        final Mock servletResponse;

        remoteControlResponse = new Response(123, "");
        servletResponse = mock(HttpServletResponse.class);
        servletResponse.expects("setStatus").with(123);
        servletResponse.expects("getWriter").will(returnValue(new PrintWriter(new StringWriter(100))));

        new HubServlet().reply((HttpServletResponse) servletResponse, remoteControlResponse);
        verifyMocks();
    }

    @Test
    public void replyWriteRemoteControlResponseOnServletResponseAsPlainText() throws IOException {
        final StringWriter writer = new StringWriter(100);
        final Response remoteControlResponse;
        final Mock servletResponse;

        remoteControlResponse = new Response(0, "some response message");
        servletResponse = mock(HttpServletResponse.class);
        servletResponse.expects("getWriter").will(returnValue(new PrintWriter(writer)));

        new HubServlet().reply((HttpServletResponse) servletResponse, remoteControlResponse);
        assertEquals("some response message", writer.getBuffer().toString());

        verifyMocks();
    }

    @Test
    public void forwardExecuteTheSeleneseCommandOnTheAppropriateRemoteControl() throws IOException, NoSuchMethodException {
        final HttpParameters requestParameters;
        final Mock environmentManager;
        final Mock remoteControl;
        final Response response;
        final HubServlet servlet;
        final Mock pool;

        servlet = new HubServlet();
        requestParameters = new HttpParameters();
        requestParameters.put("cmd", "aSeleneseCommand");
        requestParameters.put("sessionId", "a session id");
        pool = mock(DynamicRemoteControlPool.class);
        remoteControl = mock(RemoteControlProxy.class);
        environmentManager = mock(EnvironmentManager.class);
        response = new Response(0, "");

        pool.expects("retrieve").with("a session id").will(returnValue(remoteControl));
        remoteControl.expects("forward").with(eq(requestParameters)).will(returnValue(response));

        assertEquals(response, servlet.forward(requestParameters, (DynamicRemoteControlPool) pool, (EnvironmentManager) environmentManager));
        verifyMocks();
    }

    @SuppressWarnings({"ThrowableInstanceNeverThrown"})
    @Test
    public void forwardReturnAnErrorMessageWhenACommandParsingExceptionIsThrown() throws IOException, NoSuchMethodException {
        final HttpParameters requestParameters;
        final Mock environmentManager;
        final Response response;
        final HubServlet servlet;
        final Mock pool;

        servlet = new HubServlet();
        requestParameters = new HttpParameters();
        requestParameters.put("cmd", "aSeleneseCommand");
        requestParameters.put("sessionId", "a session id");
        pool = mock(DynamicRemoteControlPool.class);
        environmentManager = mock(EnvironmentManager.class);

        pool.expects("retrieve").with("a session id").will(throwException(new CommandParsingException("an error message")));

        response = servlet.forward(requestParameters, (DynamicRemoteControlPool) pool, (EnvironmentManager) environmentManager);
        assertEquals(200, response.statusCode());
        assertEquals("ERROR: an error message", response.body());
        verifyMocks();
    }

    @Test
    public void requestParametersReturnsAdaptedRequestParameters() {
        final HttpParameters parameters;
        final Map<String, String[]> parameterMap;
        final HubServlet servlet;
        final Mock request;

        parameterMap = new HashMap<String, String[]>();
        parameterMap.put("a name", new String[] { "a value"});
        request = mock(HttpServletRequest.class);
        request.expects("getParameterMap").will(returnValue(parameterMap));
        servlet = new HubServlet();
        parameters = servlet.requestParameters((HttpServletRequest) request);
        assertEquals("a value", parameters.get("a name"));
    }


}
