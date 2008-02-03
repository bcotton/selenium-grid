package com.thoughtworks.selenium.grid.remotecontrol.instrumentation;

/**
 * Process Http Requests
 *
 * @author Philippe Hanrigou
 */
public interface HttpRequestProcessor {

    Response process(Request request);
    
}
