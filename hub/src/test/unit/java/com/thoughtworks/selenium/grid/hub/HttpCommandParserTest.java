package com.thoughtworks.selenium.grid.hub;

import com.thoughtworks.selenium.grid.hub.remotecontrol.commands.NewBrowserSessionCommand;
import com.thoughtworks.selenium.grid.hub.remotecontrol.commands.SeleneseCommand;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import org.jbehave.classmock.UsingClassMock;
import org.jbehave.core.mock.Mock;
import org.junit.Test;

import java.io.IOException;


public class HttpCommandParserTest extends UsingClassMock {

    @SuppressWarnings({"ConstantConditions"})
    @Test
    public void returnsNewBrowserSessionCommandForNewSessionRequests() {
        // Moved to JRuby but not working yet -- Clint
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
