package com.thoughtworks.selenium.grid.remotecontrol;

import static junit.framework.Assert.assertEquals;
import org.jbehave.classmock.UsingClassMock;
import org.junit.Test;

import java.io.IOException;

/**
 * {@link RegistrationRequest} unit test class.
 */
public class RegistrationRequestTest extends UsingClassMock {

    @Test
    public void postToHubUsingURLProvidedInConstructor() throws IOException {
      final HubRequest request = new RegistrationRequest("http://thehub.url:4444", "", "", "");
        assertEquals("http://thehub.url:4444/registration-manager/register", request.postMethod().getURI().toString());
    }

}