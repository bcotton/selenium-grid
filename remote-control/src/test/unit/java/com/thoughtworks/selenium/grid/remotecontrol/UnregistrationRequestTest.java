package com.thoughtworks.selenium.grid.remotecontrol;

import static junit.framework.Assert.assertEquals;
import org.junit.Test;
import org.apache.commons.httpclient.URIException;


public class UnregistrationRequestTest {

    @Test
    public void postToHubUsingURLProvidedInConstructorForRegistrationRequest() throws URIException {
        UnregistrationRequest request = new UnregistrationRequest("http://thehub.url:4444", "", "", "");
        assertEquals("http://thehub.url:4444/registration-manager/unregister", request.postmethod().getURI().toString());
    }


}