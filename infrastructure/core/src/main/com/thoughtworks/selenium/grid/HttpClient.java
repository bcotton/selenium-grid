package com.thoughtworks.selenium.grid;

import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
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
        return request(new GetMethod(url));
    }

    public Response post(String url, HttpParameters parameters) throws IOException {
        return request(buildPostMethod(url, parameters));
    }

    protected PostMethod buildPostMethod(String url, HttpParameters parameters) {
        final PostMethod postMethod;

        postMethod = new PostMethod(url);
        postMethod.setRequestHeader("Content-Type", "application/x-www-form-urlencoded; ; charset=UTF-8");
        for (String name : parameters.names()) {
            postMethod.setParameter(name, parameters.get(name));
        }
        return postMethod;
    }

    protected Response request(HttpMethod method) throws IOException {
        final int statusCode;
        final String body;

        try {
            statusCode = client.executeMethod(method);
            body = new String(method.getResponseBody(), "utf-8");
            logger.info("Remote Control replied with '" + statusCode + " / '" + body + "'");
            return new Response(statusCode, body);
        } finally {
            method.releaseConnection();
        }
    }

}
