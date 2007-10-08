package com.thoughtworks.selenium.grid.hub.remotecontrol;

import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;

/**
 * Invoke HTTP GET requests and gather status code and text body for the response.
 * <br/>
 * Implementation is simplistic but should cover Selenium RC limited vocabulary.
 */
public class HttpClient {

    private static final Log logger = LogFactory.getLog(HttpClient.class);
    private final org.apache.commons.httpclient.HttpClient client;

    public HttpClient(org.apache.commons.httpclient.HttpClient client) {
        this.client = client;
    }

    public HttpClient() {
        this(new org.apache.commons.httpclient.HttpClient());
    }

    public Response get(String url) throws IOException {
        return get(new GetMethod(url));
    }

    public Response get(GetMethod method) throws IOException {
        final int statusCode;
        final String body;

        try {
            statusCode = client.executeMethod(method);
            body = method.getResponseBodyAsString();
            logger.info("Remote Control replied with '" + statusCode + " / '" + body + "'");
            return new Response(statusCode, body);
        } finally {
            method.releaseConnection();
        }
    }

}
