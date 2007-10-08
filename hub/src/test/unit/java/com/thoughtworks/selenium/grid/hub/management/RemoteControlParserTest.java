package com.thoughtworks.selenium.grid.hub.management;

import com.thoughtworks.selenium.grid.hub.remotecontrol.RemoteControlProxy;
import static junit.framework.Assert.assertEquals;
import org.jbehave.classmock.UsingClassMock;
import org.jbehave.core.mock.Mock;
import org.junit.Test;

import javax.servlet.http.HttpServletRequest;

public class RemoteControlParserTest extends UsingClassMock {

    @Test
    public void parseReturnsARemoteControlWhoseHostMatchHttpParameter() {
        final Mock httpRequest;
        final RemoteControlProxy remoteControl;

        httpRequest = mock(HttpServletRequest.class);
        httpRequest.expects("getParameter").with("host").will(returnValue("some.host.com"));
        httpRequest.expects("getParameter").with(anything()).will(returnValue("0")).atLeastOnce();

        remoteControl = RemoteControlParser.parse((HttpServletRequest) httpRequest);
        assertEquals("some.host.com", remoteControl.host());
        verifyMocks();
    }

    @Test
    public void parseReturnsARemoteControlWhosePortMatchHttpParameter() {
        final Mock httpRequest;
        final RemoteControlProxy remoteControl;

        httpRequest = mock(HttpServletRequest.class);
        httpRequest.expects("getParameter").with("port").will(returnValue("1234"));
        httpRequest.expects("getParameter").with(anything()).will(returnValue("0")).atLeastOnce();

        remoteControl = RemoteControlParser.parse((HttpServletRequest) httpRequest);
        assertEquals(1234, remoteControl.port());
        verifyMocks();
    }

    @Test
    public void parseReturnsARemoteControlWhoseEnvironmentMatchHttpParameter() {
        final Mock httpRequest;
        final RemoteControlProxy remoteControl;

        httpRequest = mock(HttpServletRequest.class);
        httpRequest.expects("getParameter").with("environment").will(returnValue("an environment"));
        httpRequest.expects("getParameter").with(anything()).will(returnValue("0")).atLeastOnce();

        remoteControl = RemoteControlParser.parse((HttpServletRequest) httpRequest);
        assertEquals("an environment", remoteControl.environment());
        verifyMocks();
    }

    @Test(expected = IllegalStateException.class)
    public void parseThrowsIllegalArgumentExceptionIfHostIsNull() {
        final Mock httpRequest;

        httpRequest = mock(HttpServletRequest.class);
        httpRequest.expects("getParameter").with("host").will(returnValue(null));
        httpRequest.expects("getParameter").with(anything()).will(returnValue("0")).atLeastOnce();

        RemoteControlParser.parse((HttpServletRequest) httpRequest);
        verifyMocks();
    }

    @Test(expected = IllegalStateException.class)
    public void parseThrowsIllegalArgumentExceptionIfHostIsAnEmptyString() {
        final Mock httpRequest;

        httpRequest = mock(HttpServletRequest.class);
        httpRequest.expects("getParameter").with("host").will(returnValue(""));
        httpRequest.expects("getParameter").with(anything()).will(returnValue("0")).atLeastOnce();

        RemoteControlParser.parse((HttpServletRequest) httpRequest);
        verifyMocks();
    }

    @Test(expected = IllegalStateException.class)
    public void parseThrowsIllegalArgumentExceptionIfPortIsNull() {
        final Mock httpRequest;

        httpRequest = mock(HttpServletRequest.class);
        httpRequest.expects("getParameter").with("port").will(returnValue(null));
        httpRequest.expects("getParameter").with(anything()).will(returnValue("0")).atLeastOnce();

        RemoteControlParser.parse((HttpServletRequest) httpRequest);
        verifyMocks();
    }

    @Test(expected = IllegalStateException.class)
    public void parseThrowsIllegalArgumentExceptionIfPortIsAnEmptyString() {
        final Mock httpRequest;

        httpRequest = mock(HttpServletRequest.class);
        httpRequest.expects("getParameter").with("port").will(returnValue(""));
        httpRequest.expects("getParameter").with(anything()).will(returnValue("0")).atLeastOnce();

        RemoteControlParser.parse((HttpServletRequest) httpRequest);
        verifyMocks();
    }

    @Test(expected = IllegalStateException.class)
    public void parseThrowsIllegalArgumentExceptionIfEnvironmentIsNull() {
        final Mock httpRequest;

        httpRequest = mock(HttpServletRequest.class);
        httpRequest.expects("getParameter").with("environment").will(returnValue(null));
        httpRequest.expects("getParameter").with(anything()).will(returnValue("0")).atLeastOnce();

        RemoteControlParser.parse((HttpServletRequest) httpRequest);
        verifyMocks();
    }

    @Test(expected = IllegalStateException.class)
    public void parseThrowsIllegalArgumentExceptionIfEnvironmentIsAnEmptyString() {
        final Mock httpRequest;

        httpRequest = mock(HttpServletRequest.class);
        httpRequest.expects("getParameter").with("environment").will(returnValue(""));
        httpRequest.expects("getParameter").with(anything()).will(returnValue("0")).atLeastOnce();

        RemoteControlParser.parse((HttpServletRequest) httpRequest);
        verifyMocks();
    }

}
