package com.thoughtworks.selenium.grid;

import junit.framework.Assert;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.jbehave.classmock.UsingClassMock;
import org.jbehave.core.mock.Mock;
import org.junit.Test;

import java.io.IOException;
import java.net.ConnectException;


public class HttpClientTest extends UsingClassMock {

    @Test
    public void requestReturnsAResponseWithHttpBody() throws IOException {
        final Response response;
        final Mock method;
        final Mock httpClient;

        httpClient = mock(org.apache.commons.httpclient.HttpClient.class);
        method = mock(GetMethod.class);
        method.expects("getResponseBody").will(returnValue("Body content".getBytes()));
        HttpClient client = new HttpClient((org.apache.commons.httpclient.HttpClient) httpClient);
        response = client.request((GetMethod) method);
        assertEquals("Body content", response.body());
        verifyMocks();
    }

    @Test
    public void responseHandleInternationalCharactersWhenUTF8EncodedAsRemoteControlDo() throws IOException {
        final Response response;
        final Mock method;
        final Mock httpClient;

        httpClient = mock(org.apache.commons.httpclient.HttpClient.class);
        method = mock(PostMethod.class);
        method.expects("getResponseBody").will(returnValue("32 décembre".getBytes("utf-8")));
        HttpClient client = new HttpClient((org.apache.commons.httpclient.HttpClient) httpClient);
        response = client.request((PostMethod) method);
        assertEquals("32 décembre", response.body());
        verifyMocks();
    }

    @Test
    public void requestReleasesTheConnectionOnTheGetMethod() throws IOException {
        final Mock method;
        final Mock httpClient;

        httpClient = mock(org.apache.commons.httpclient.HttpClient.class);
        method = mock(GetMethod.class);
        method.expects("getResponseBody").will(returnValue("".getBytes()));
        method.expects("releaseConnection");
        HttpClient client = new HttpClient((org.apache.commons.httpclient.HttpClient) httpClient);
        client.request((GetMethod) method);
        verifyMocks();
    }

    @SuppressWarnings({"ThrowableInstanceNeverThrown"})
    @Test(expected = ConnectException.class)
    public void requestReleasesTheConnectionOnTheGetMethodWhenAProblemOccurs() throws IOException {
        final Mock method;
        final Mock httpClient;

        httpClient = mock(org.apache.commons.httpclient.HttpClient.class);
        method = mock(GetMethod.class);
        httpClient.expects("executeMethod").will(throwException(new ConnectException("an error")));
        method.expects("releaseConnection");
        HttpClient client = new HttpClient((org.apache.commons.httpclient.HttpClient) httpClient);
        client.request((GetMethod) method);
        verifyMocks();
    }

    @Test
    public void buildPostMethodUsesURLProvidedAsAParameter() throws IOException {
        final PostMethod postmethod;
        
        postmethod = new HttpClient().buildPostMethod("http://a.url/somwhere", new HttpParameters());
        assertEquals("http://a.url/somwhere", postmethod.getURI().toString());
    }

    @Test
    public void buildPostMethodUsesParamsProvidedAsParameter() throws IOException {
        final HttpParameters parameters;
        final PostMethod postmethod;

        parameters = new HttpParameters();
        parameters.put("selenium", "grid");
        parameters.put("open", "qa");
        postmethod = new HttpClient().buildPostMethod("", parameters);
        assertEquals("grid", postmethod.getParameter("selenium").getValue());
        assertEquals("qa", postmethod.getParameter("open").getValue());
    }

    @Test
    public void getReturnTheResultOfAGetRequestWithTheURLPassedAsAParameter() throws IOException {
        final HttpClient httpClient;
        final Response expectedResponse;

        expectedResponse = new Response(null);
        httpClient = new HttpClient(null) {

            protected Response request(HttpMethod method) throws IOException {
                assertTrue(method instanceof GetMethod);
                assertEquals("http://a.url/", method.getURI().toString());
                return expectedResponse;
            }
            
        };
        assertEquals(expectedResponse, httpClient.get("http://a.url/"));

    }

    @Test
    public void postReturnTheResultOfAPostRequestWithTheURLPassedAsAParameter() throws IOException {
        final HttpClient httpClient;
        final Response expectedResponse;

        expectedResponse = new Response(null);
        httpClient = new HttpClient(null) {

            protected Response request(HttpMethod method) throws IOException {
                assertTrue(method instanceof PostMethod);
                assertEquals("http://a.url/", method.getURI().toString());
                return expectedResponse;
            }

        };
        assertEquals(expectedResponse, httpClient.post("http://a.url/", new HttpParameters()));
    }

    @Test
    public void postReturnTheResultOfAPostRequestWithTheParametersPassedAsArgument() throws IOException {
        final Response expectedResponse;
        final HttpParameters parameters;
        final HttpClient httpClient;

        expectedResponse = new Response(null);
        httpClient = new HttpClient(null) {

            protected Response request(HttpMethod method) throws IOException {
                final PostMethod postMethod;

                postMethod = ((PostMethod) method);
                assertEquals("aValue", postMethod.getParameter("aParameter").getValue());
                assertEquals("anotherValue", postMethod.getParameter("anotherParameter").getValue());
                return expectedResponse;
            }

        };
        parameters = new HttpParameters();
        parameters.put("aParameter", "aValue");
        parameters.put("anotherParameter", "anotherValue");
        assertEquals(expectedResponse, httpClient.post("http://a.url/", parameters));
    }

    @Test
    public void postReturnTheResultOfAPostRequestAsUTF8FormEncodingEncoding() throws IOException {
        final Response expectedResponse;
        final HttpParameters parameters;
        final HttpClient httpClient;

        expectedResponse = new Response(null);
        httpClient = new HttpClient(null) {

            protected Response request(HttpMethod method) throws IOException {
                final HttpMethodParams params;

                assertEquals("application/x-www-form-urlencoded; ; charset=UTF-8",
                             method.getRequestHeader("Content-Type").getValue());
                return expectedResponse;
            }

        };
        assertEquals(expectedResponse, httpClient.post("http://a.url/", new HttpParameters()));
    }

}
