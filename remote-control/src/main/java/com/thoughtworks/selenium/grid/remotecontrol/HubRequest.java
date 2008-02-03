package com.thoughtworks.selenium.grid.remotecontrol;

import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.HttpClient;

import java.io.IOException;

/**
 * Selenium Remote Control Request to Grid Hub.
 *
 * @author Philippe Hanrigou
 */
public class HubRequest {

    private final String environment;
    private final String targetURL;
    private final String host;
    private final String port;

    public HubRequest(String targetURL, String host, String port, String environment) {
        this.targetURL = targetURL;
        this.environment =  environment;
        this.host = host;
        this.port = port;
    }

    public int execute() throws IOException {
        return new HttpClient().executeMethod(postMethod());
    }

    public PostMethod postMethod() {
        final PostMethod postMethod = new PostMethod(targetURL);
        postMethod.addParameter("host", host);
        postMethod.addParameter("port", port);
        postMethod.addParameter("environment", environment);

        return postMethod;
    }


    public String targetURL() {
        return targetURL;
    }

    public String environment() {
        return environment;
    }

}
