package com.thoughtworks.selenium.grid.remotecontrol;

import static junit.framework.Assert.assertEquals;
import org.junit.Test;
import org.apache.commons.httpclient.URIException;


public class HubRequestTest {

    @Test
    public void postHostAsProvidedInConstructor() {
        HubRequest request = new HubRequest("", "The Host", "", "");
        assertEquals("The Host", request.postmethod().getParameter("host").getValue());
    }

    @Test
    public void postPortAsProvidedInConstructor() {
        HubRequest request = new HubRequest("", "", "The Port", "");
        assertEquals("The Port", request.postmethod().getParameter("port").getValue());
    }

    @Test
    public void postEnvironmentAsProvidedInConstructor() {
        HubRequest request = new HubRequest("", "", "", "The Env");
        assertEquals("The Env", request.postmethod().getParameter("environment").getValue());
    }


    @Test
    public void postToHubUsingURLProvidedInConstructor() throws URIException {
        HubRequest request = new HubRequest("http://hub.url:4444/action", "", "", "");
        assertEquals("http://hub.url:4444/action", request.postmethod().getURI().toString());
    }

    @Test
    public void environmentReturnsEnvironmentProvidedInConstructor() {
        HubRequest request = new HubRequest("", "", "", "The Env");
        assertEquals("The Env", request.environment());
    }

    @Test
    public void targetURLReturnsEnvironmentProvidedInConstructor() {
        HubRequest request = new HubRequest("http://target-url", "", "", "");
        assertEquals("http://target-url", request.targetURL());
    }

}
