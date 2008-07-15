package com.thoughtworks.selenium.grid.hub;

import com.thoughtworks.selenium.grid.hub.remotecontrol.commands.NewBrowserSessionCommand;
import com.thoughtworks.selenium.grid.hub.remotecontrol.commands.SeleneseCommand;
import com.thoughtworks.selenium.grid.hub.remotecontrol.commands.TestCompleteCommand;
import com.thoughtworks.selenium.grid.HttpParameters;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import org.jbehave.classmock.UsingClassMock;
import org.jbehave.core.mock.Mock;
import org.junit.Test;

import java.io.IOException;


public class HttpCommandParserTest extends UsingClassMock {

    @Test
    public void parametersReturnsTheRequestParametersProvidedToTheConstructor() {
      HttpParameters parameters = new HttpParameters();
      assertEquals(parameters, new HttpCommandParser(parameters).parameters());
    }

    @Test
    public void returnsARemoteControlCommandWithHttpRequestQueryStringForAGenericRequest() {
      HttpParameters parameters = new HttpParameters();
      parameters.put("cmd", "generic");
      parameters.put("sessionId", "1234");

      SeleneseCommand command = new HttpCommandParser(parameters).parse(null);
      assertEquals("generic", command.parameters().get("cmd"));
      assertEquals("1234", command.parameters().get("sessionId"));
      assertEquals("1234", command.sessionId());
    }

    @Test
    public void returnsARemoteControlCommandWithMatchingHttpParametersForAGenericRequest() {
      HttpParameters parameters = new HttpParameters();
      parameters.put("cmd", "generic");
      parameters.put("sessionId", "1234");

      SeleneseCommand command = new HttpCommandParser(parameters).parse(null);
      assertEquals("generic", command.parameters().get("cmd"));
      assertEquals("1234", command.parameters().get("sessionId"));
    }

    @Test
    public void returnsARemoteControlCommandWithMatchingSessionIdForAGenericRequest() {
      HttpParameters parameters = new HttpParameters();
      parameters.put("cmd", "generic");
      parameters.put("sessionId", "1234");

      SeleneseCommand command = new HttpCommandParser(parameters).parse(null);
      assertEquals("1234", command.sessionId());
    }

    @Test
    public void returnsTestCompleteCommandForTestCompleteRequests() {
      HttpParameters parameters = new HttpParameters();
      parameters.put("cmd", "testComplete");
      parameters.put("sessionId", "1234");

      SeleneseCommand command = new HttpCommandParser(parameters).parse(null);
      assertTrue(command instanceof TestCompleteCommand);
      assertEquals("testComplete", command.parameters().get("cmd"));
      assertEquals("1234", command.parameters().get("sessionId"));
      assertEquals("1234", command.sessionId());
    }


    @SuppressWarnings({"ConstantConditions"})
    @Test
    public void returnsNewBrowserSessionCommandForNewSessionRequests() {
        final NewBrowserSessionCommand browserSessionCommand;
        final Mock environmentManager;
        final Environment expectedEnvironment;
        final HttpParameters parameters;
        final SeleneseCommand command;

        parameters = new HttpParameters();
        parameters.put("cmd", "getNewBrowserSession");
        parameters.put("1", "an environment name");
        parameters.put("2", "http://openqa.org");
        expectedEnvironment = new Environment("", "aBrowser");
        environmentManager = mock(EnvironmentManager.class);
        environmentManager.expects("environment").with("an environment name").will(returnValue(expectedEnvironment));

        command = new HttpCommandParser(parameters).parse((EnvironmentManager) environmentManager);
        assertEquals(true, command instanceof NewBrowserSessionCommand);
        browserSessionCommand = (NewBrowserSessionCommand) command;

        assertEquals("getNewBrowserSession", browserSessionCommand.parameters().get("cmd"));
        assertEquals("aBrowser", browserSessionCommand.parameters().get("1"));
        assertEquals("http://openqa.org", browserSessionCommand.parameters().get("2"));

        assertEquals(expectedEnvironment, browserSessionCommand.environment());

        verifyMocks();
    }

    
    @Test(expected = CommandParsingException.class)
    public void executeThrowsCommandParsingExceptionForNewBrowserSessionWhenEnvironmentIsNotKnown() throws IOException {
        final Mock environmentManager;
        final HttpParameters parameters;

        parameters = new HttpParameters();
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
        final HttpParameters parameters;

        parameters = new HttpParameters();
        parameters.put("cmd", "genericCommand");
        environmentManager = mock(EnvironmentManager.class);

        new HttpCommandParser(parameters).parse((EnvironmentManager) environmentManager);
        verifyMocks();
    }

    @Test(expected = CommandParsingException.class)
    public void executeThrowsCommandParsingExceptionForTestCompleteCommandWhenSessionIdIsNull() throws IOException {
        final Mock environmentManager;
        final HttpParameters parameters;

        parameters = new HttpParameters();
        parameters.put("cmd", "testComplete");
        environmentManager = mock(EnvironmentManager.class);

        new HttpCommandParser(parameters).parse((EnvironmentManager) environmentManager);
        verifyMocks();
    }

    
}
