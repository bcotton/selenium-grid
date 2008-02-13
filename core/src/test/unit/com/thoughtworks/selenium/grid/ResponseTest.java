package com.thoughtworks.selenium.grid;

import junit.framework.Assert;
import org.junit.Test;
import com.thoughtworks.selenium.grid.Response;


public class ResponseTest {

    @Test
    public void defaultStatusCodeIs200() {
       Assert.assertEquals(200, new Response("").statusCode());
    }

    @Test
    public void bodyIsAnErrorMessageWhenNoStatusIsProvided() {
       Assert.assertEquals("ERROR: a message", new Response("a message").body());
    }

    @Test
    public void statusCodeReturnsTheOneProvidedInTheConstructor() {
       Assert.assertEquals(123, new Response(123, "").statusCode());
    }

    @Test
    public void bodyReturnsTheOneProvidedInTheConstructor() {
       Assert.assertEquals("some content", new Response(0, "some content").body());
    }

}
