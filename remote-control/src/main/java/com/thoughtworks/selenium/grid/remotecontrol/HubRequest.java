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

    private final PostMethod postMethod;
    private final String environment;
    private final String targetURL;

    protected HubRequest(String targetURL, String host, String port, String environment) {
        this.environment =  environment;
        this.targetURL = targetURL;

        postMethod = new PostMethod(targetURL);
        postMethod.addParameter("host", host);
        postMethod.addParameter("port", port);
        postMethod.addParameter("environment", environment);
    }

    public int execute() throws IOException {
        return new HttpClient().executeMethod(postMethod);
    }

    protected PostMethod postmethod() {
        return postMethod;
    }


    protected String targetURL() {
        return targetURL;
    }

    protected String environment() {
        return environment;
    }

}
