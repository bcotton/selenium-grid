package com.thoughtworks.selenium.grid.hub.remotecontrol;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import org.junit.Test;

import java.io.IOException;

/**
 * TODO
 *
 * @author: Philippe Hanrigou
 */
public class SeleniumRCProxyIntegrationTest {

    private static final int VALID_PORT = 5555;

    @Test
    public void forwardGetNewBrowserSessionToRemoteControl() throws IOException {
      HttpClient client = new HttpClient();
      RemoteControlProxy proxy = new RemoteControlProxy("localhost", VALID_PORT, null, client);
      Response response = proxy.forward("cmd=getNewBrowserSession&1=*firefox&2=http://www.google.com");
      assertEquals(200, response.statusCode());
      assertTrue(response.body().startsWith("OK,"));
    }

}