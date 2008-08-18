package com.thoughtworks.selenium.grid.agent;

import com.thoughtworks.selenium.grid.webserver.Resource;
import com.thoughtworks.selenium.grid.HttpParameters;

/**
 * TODO
 */
public class RemoteControlCommand extends Resource {

    public String process(HttpParameters params) {
        return null;
    }

    public RemoteControlConfiguration parseRemoteControlConfiguration(HttpParameters httpParameters) {
        final RemoteControlConfiguration configuration;

        configuration = new RemoteControlConfiguration();
        if (null != httpParameters.get("port")) {
            final Integer port;
            port = Integer.parseInt(httpParameters.get("port"));

            configuration.setPort(port);
        }
        
        return configuration;
    }
    
}
