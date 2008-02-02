package com.thoughtworks.selenium.grid.remotecontrol;

import static junit.framework.Assert.assertEquals;
import org.junit.Test;
import org.apache.commons.httpclient.URIException;


public class RegistrationRequestTest {

    @Test
    public void postToHubUsingURLProvidedInConstructorForRegistrationRequest() throws URIException {
        RegistrationRequest request = new RegistrationRequest("http://thehub.url:4444", "", "", "");
        assertEquals("http://thehub.url:4444/registration-manager/register", request.postmethod().getURI().toString());
    }


}