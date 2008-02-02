package com.thoughtworks.selenium.grid.remotecontrol;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;

/*
 * Registration Request to Selenium Grid Hub.
 *
 * @author Philippe Hanrigou
 */
public class UnregistrationRequest extends HubRequest {

    private static final Log logger = LogFactory.getLog(RegistrationRequest.class);

    public UnregistrationRequest(String seleniumHubURL, String host, String port, String environment) {
        super(seleniumHubURL + "/registration-manager/unregister", host, port, environment);
    }


    public int execute() throws IOException {
        logger.info("Unregistering from " + targetURL());
        return super.execute();
    }

}