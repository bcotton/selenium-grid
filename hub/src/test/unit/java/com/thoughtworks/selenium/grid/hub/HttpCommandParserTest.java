package com.thoughtworks.selenium.grid.hub;

import com.thoughtworks.selenium.grid.hub.remotecontrol.commands.NewBrowserSessionCommand;
import com.thoughtworks.selenium.grid.hub.remotecontrol.commands.SeleneseCommand;
import com.thoughtworks.selenium.grid.hub.remotecontrol.commands.TestCompleteCommand;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import org.jbehave.classmock.UsingClassMock;
import org.jbehave.core.mock.Mock;
import org.junit.Test;

import java.io.IOException;


public class HttpCommandParserTest extends UsingClassMock {

    @Test
    public void parametersReturnsTheRequestParametersProvidedToTheConstructor() throws java.lang.reflect.InvocationTargetException, IllegalAccessException {
        final ServletParametersAdapter parameters = new ServletParametersAdapter();
        ServletParametersAdapter actual = null;
        for (java.lang.reflect.Method m: HttpCommandParser.class.getDeclaredMethods()) {
            System.out.println("==> " + m.getName());
            if ("parameters" == m.getName()) {
                m.setAccessible(true);
                System.out.println("OK");
                actual = (ServletParametersAdapter) m.invoke(new HttpCommandParser(parameters), new Object[0]);
            }
        }

        assertEquals(parameters, actual);
    }

    @Test
    public void returnsARemoteControlCommandWithHttpRequestQueryStringForAGenericRequest() {
        final ServletParametersAdapter parameters;
        final SeleneseCommand command;

        parameters = new ServletParametersAdapter();
        parameters.put("cmd", "generic");
        parameters.put("sessionId", "1234");

        command = new HttpCommandParser(parameters).parse(null);
        assertEquals("cmd=generic&sessionId=1234", command.queryString());
        assertEquals("1234", command.sessionId());

        verifyMocks();
    }

    @SuppressWarnings({"ConstantConditions"})
    @Test
    public void returnsNewBrowserSessionCommandForNewSessionRequests() {
        final NewBrowserSessionCommand browserSessionCommand;
        final Mock environmentManager;
        final Environment expectedEnvironment;
        final ServletParametersAdapter parameters;
        final SeleneseCommand command;

        parameters = new ServletParametersAdapter();
        parameters.put("cmd", "getNewBrowserSession");
        parameters.put("1", "an environment name");
        parameters.put("2", "http://openqa.org");
        expectedEnvironment = new Environment("", "aBrowser", null);
        environmentManager = mock(EnvironmentManager.class);
        environmentManager.expects("environment").with("an environment name").will(returnValue(expectedEnvironment));

        command = new HttpCommandParser(parameters).parse((EnvironmentManager) environmentManager);
        assertEquals(true, command instanceof NewBrowserSessionCommand);
        browserSessionCommand = (NewBrowserSessionCommand) command;
        assertTrue(browserSessionCommand.queryString().contains("cmd=getNewBrowserSession"));
        assertTrue(browserSessionCommand.queryString().contains("1=aBrowser"));
        assertTrue(browserSessionCommand.queryString().contains("2=http%3A%2F%2Fopenqa.org"));
        assertEquals(expectedEnvironment, browserSessionCommand.environment());

        verifyMocks();
    }

    @Test
    public void returnsTestCompleteCommandForTestCompleteRequests() {
        final ServletParametersAdapter parameters;
        final SeleneseCommand command;

        parameters = new ServletParametersAdapter();
        parameters.put("cmd", "testComplete");
        parameters.put("sessionId", "1234");

        command = new HttpCommandParser(parameters).parse(null);
        assertEquals(true, command instanceof TestCompleteCommand);
        assertEquals("cmd=testComplete&sessionId=1234", command.queryString());
        assertEquals("1234", command.sessionId());
    }

    @Test(expected = CommandParsingException.class)
    public void executeThrowsCommandParsingExceptionForNewBrowserSessionWhenEnvironmentIsNotKnown() throws IOException {
        final Mock environmentManager;
        final ServletParametersAdapter parameters;

        parameters = new ServletParametersAdapter();
        parameters.put("cmd", "getNewBrowserSession");
        parameters.put("1", "an unknown environment name");
        parameters.put("2", "http://openqa.org");
        environmentManager = mock(EnvironmentManager.class);
        environmentManager.expects("environment").with("an unknown environment name").will(returnValue(null));

        new HttpCommandParser(parameters).parse((EnvironmentManager) environmentManager);
        verifyMocks();
    }

    @Test(expected = CommandParsingException.class)
    public void executeThrowsCommandParsingExceptionForAGenericSeleneseCommandWhenSessionIdIsNull() throws IOException {
        final Mock environmentManager;
        final ServletParametersAdapter parameters;

        parameters = new ServletParametersAdapter();
        parameters.put("cmd", "genericCommand");
        environmentManager = mock(EnvironmentManager.class);

        new HttpCommandParser(parameters).parse((EnvironmentManager) environmentManager);
        verifyMocks();
    }

    @Test(expected = CommandParsingException.class)
    public void executeThrowsCommandParsingExceptionForTestCompleteCommandWhenSessionIdIsNull() throws IOException {
        final Mock environmentManager;
        final ServletParametersAdapter parameters;

        parameters = new ServletParametersAdapter();
        parameters.put("cmd", "testComplete");
        environmentManager = mock(EnvironmentManager.class);

        new HttpCommandParser(parameters).parse((EnvironmentManager) environmentManager);
        verifyMocks();
    }

    
}
