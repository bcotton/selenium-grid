package com.thoughtworks.selenium.grid.remotecontrol;

import static junit.framework.Assert.assertEquals;
import org.jbehave.classmock.UsingClassMock;
import org.junit.Test;

import java.io.IOException;

/**
 * {@link HubRequest} unit test class.
 */
public class HubRequestTest extends UsingClassMock {

    @Test
    public void postToHubUsingURLProvidedInConstructor() throws IOException {
      final HubRequest request = new HubRequest("http://hub.url:4444/action", "", "", "");
        assertEquals("http://hub.url:4444/action", request.postMethod().getURI().toString());
    }

    @Test
    public void targetURLReturnsThetargetURLProvidedInConstructor() throws IOException {
        assertEquals("http://target-url", new HubRequest("http://target-url", "", "", "").targetURL());
    }

    @Test
    public void postHostAsProvidedInConstructor() throws IOException {
        assertEquals("The Host", new HubRequest("", "The Host", "", "").postMethod().getParameter("host").getValue());
    }

    @Test
    public void postPortAsProvidedInConstructor() throws IOException {
        assertEquals("The Port", new HubRequest("", "", "The Port", "").postMethod().getParameter("port").getValue());
    }

    @Test
    public void postEnvironmnetAsProvidedInConstructor() throws IOException {
        assertEquals("The Env", new HubRequest("", "", "", "The Env").postMethod().getParameter("environment").getValue());
    }

}
