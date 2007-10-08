package com.thoughtworks.selenium.grid.hub.remotecontrol;

import static junit.framework.Assert.assertEquals;
import org.apache.commons.httpclient.methods.GetMethod;
import org.jbehave.classmock.UsingClassMock;
import org.jbehave.core.mock.Mock;
import org.junit.Test;

import java.io.IOException;
import java.net.ConnectException;

public class HttpClientTest extends UsingClassMock {

    @Test
    public void getReturnsAResponseWithHttpStatusCode() throws IOException {
        final Response response;
        final GetMethod method;
        final Mock httpClient;

        httpClient = mock(org.apache.commons.httpclient.HttpClient.class);
        method = new GetMethod("http://a/url");
        httpClient.expects("executeMethod").with(method).will(returnValue(123));
        response = new HttpClient((org.apache.commons.httpclient.HttpClient) httpClient).get(method);
        assertEquals(123, response.statusCode());
        verifyMocks();
    }


    @Test
    public void getReturnsAResponseWithHttpBody() throws IOException {
        final Response response;
        final Mock method;
        final Mock httpClient;

        httpClient = mock(org.apache.commons.httpclient.HttpClient.class);
        method = mock(GetMethod.class);
        method.expects("getResponseBodyAsString").will(returnValue("Body content"));
        HttpClient client = new HttpClient((org.apache.commons.httpclient.HttpClient) httpClient);
        response = client.get((GetMethod) method);
        assertEquals("Body content", response.body());
        verifyMocks();
    }

    @Test
    public void getReleasesTheConnectionOnTheGetMethod() throws IOException {
        final Mock method;
        final Mock httpClient;

        httpClient = mock(org.apache.commons.httpclient.HttpClient.class);
        method = mock(GetMethod.class);
        method.expects("releaseConnection");
        HttpClient client = new HttpClient((org.apache.commons.httpclient.HttpClient) httpClient);
        client.get((GetMethod) method);
        verifyMocks();
    }

    @SuppressWarnings({"ThrowableInstanceNeverThrown"})
    @Test(expected = ConnectException.class)
    public void getReleasesTheConnectionOnTheGetMethodWhenAProblemOccurs() throws IOException {
        final Mock method;
        final Mock httpClient;

        httpClient = mock(org.apache.commons.httpclient.HttpClient.class);
        method = mock(GetMethod.class);
        httpClient.expects("executeMethod").will(throwException(new ConnectException("an error")));
        method.expects("releaseConnection");
        HttpClient client = new HttpClient((org.apache.commons.httpclient.HttpClient) httpClient);
        client.get((GetMethod) method);
        verifyMocks();
    }

    @Test
    public void getAlsoAcceptAURL() throws IOException {
        final Mock httpClient = mock(org.apache.commons.httpclient.HttpClient.class);
        new HttpClient((org.apache.commons.httpclient.HttpClient) httpClient).get("http://a/url");
        verifyMocks();
    }

}
