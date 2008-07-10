package com.thoughtworks.selenium.grid.hub.management;

import com.thoughtworks.selenium.grid.HttpClient;
import com.thoughtworks.selenium.grid.hub.remotecontrol.RemoteControlProxy;

import javax.servlet.http.HttpServletRequest;

/**
 * PARSE
 */
public class RemoteControlParser {

    public static RemoteControlProxy parse(HttpServletRequest request) {
        final RemoteControlProxy remoteControl;
        final String portParameter;
        final String environment;
        final String host;
        final int port;

        host = request.getParameter("host");
        if (null == host || "".equals(host.trim())) {
            throw new IllegalStateException("You must specify a 'host' parameter");
        }
        portParameter = request.getParameter("port");
        if (null == portParameter || "".equals(portParameter.trim())) {
            throw new IllegalStateException("You must specify a 'port' parameter");
        }
        port = Integer.parseInt(portParameter);
        environment = request.getParameter("environment");
        if (null == environment || "".equals(environment.trim())) {
            throw new IllegalStateException("You must specify an 'environment' parameter");
        }

        remoteControl = new RemoteControlProxy(host, port, environment, 1, new HttpClient());

        return remoteControl;
    }

}
