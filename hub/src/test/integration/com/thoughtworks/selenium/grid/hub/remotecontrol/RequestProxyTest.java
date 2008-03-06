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
public class RequestProxyTest {

    private static final int VALID_PORT = 5555;

    @Test
    public void forwardGetNewBrowserSessionToRemoteControl() throws IOException {
      HttpClient proxy = new HttpClient();
      Response response = proxy.get("http://localhost:" + VALID_PORT + "/selenium-server/driver/?cmd=getNewBrowserSession&1=*firefox&2=http://www.google.com");
      assertEquals(200, response.statusCode());
      assertTrue(response.body().startsWith("OK,"));
    }

    @Test
    public void closeToRemoteControl() throws IOException {
      HttpClient proxy = new HttpClient();
      Response response = proxy.get("http://localhost:" + VALID_PORT + "/selenium-server/driver/?cmd=testComplete&sessionId=193797");
      assertEquals(200, response.statusCode());
      assertEquals("OK", response.body());
    }

    
}
