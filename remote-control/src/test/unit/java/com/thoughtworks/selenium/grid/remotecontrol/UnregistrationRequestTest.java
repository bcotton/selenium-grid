package com.thoughtworks.selenium.grid.remotecontrol;

import junit.framework.Assert;
import static junit.framework.Assert.assertEquals;
import org.jbehave.classmock.UsingClassMock;
import org.junit.Test;

import java.io.IOException;

/**
 * {@link com.thoughtworks.selenium.grid.remotecontrol.UnregistrationRequest} unit test class.
 */
public class UnregistrationRequestTest extends UsingClassMock {

    @Test
    public void postToHubUsingURLProvidedInConstructor() throws IOException {
      final HubRequest request = new UnregistrationRequest("http://thehub.url:4444", "", "", "");
        assertEquals("http://thehub.url:4444/registration-manager/unregister", request.postMethod().getURI().toString());
    }

}