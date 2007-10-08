package com.thoughtworks.selenium.grid.hub.remotecontrol.commands;

import com.thoughtworks.selenium.grid.hub.remotecontrol.RemoteControlPool;
import com.thoughtworks.selenium.grid.hub.remotecontrol.RemoteControlProxy;
import com.thoughtworks.selenium.grid.hub.remotecontrol.Response;
import static junit.framework.Assert.assertEquals;
import org.jbehave.core.mock.Mock;
import org.jbehave.classmock.UsingClassMock;
import org.junit.Test;

import java.io.IOException;


public class RemoteControlCommandTest extends UsingClassMock {

      @Test
      public void queryStringReturnsTheQueryStringProvidedToConstructor() {
          assertEquals("a query", new SeleneseCommand("", "a query").queryString());
      }

      @Test
      public void sessionIdReturnsTheSessionIdProvidedToConstructor() {
          assertEquals("a session id", new SeleneseCommand("a session id", "").sessionId());
      }

      @Test
      public void executeForwardsTheRequestToTheRemoteControl() throws Exception {
          final Mock remoteControl;
          final SeleneseCommand command;
          final Response expectedResponse;
          final Mock pool;

          command = new SeleneseCommand("a session id", "a query");
          expectedResponse = new Response(0, "");
          remoteControl = mock(RemoteControlProxy.class);
          pool = mock(RemoteControlPool.class);
          pool.expects("retrieve").with("a session id").will(returnValue(remoteControl));
          remoteControl.expects("forward").with("a query").will(returnValue(expectedResponse));

          assertEquals(expectedResponse, command.execute((RemoteControlPool) pool));
          verifyMocks();
      }

    @Test
    public void executeReturnsAnErrorResponseWhenNoSessionIdIsProvided() throws IOException {
        final Response response;

        response = new SeleneseCommand(null, "a query").execute(null);
        assertEquals("ERROR: Selenium Driver error: No sessionId provided for command 'a query'",
                     response.body());
    }
    
}
