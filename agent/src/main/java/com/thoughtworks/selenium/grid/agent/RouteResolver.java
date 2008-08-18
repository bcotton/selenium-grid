package com.thoughtworks.selenium.grid.agent;

import com.thoughtworks.selenium.grid.webserver.Resource;
import com.thoughtworks.selenium.grid.webserver.InvalidRouteException;

import javax.servlet.http.HttpServletRequest;

/**
 * Resolve an HTTP request to a specific controller.
 */
public class RouteResolver implements com.thoughtworks.selenium.grid.webserver.RouteResolver {

    public Resource resolve(HttpServletRequest request) {
        final String path;
        path = request.getPathInfo();

        if ("/".equals(path)) {
            return new AgentResource();
        } else if ("/remote-controls".equals(path) && "POST".equals(request.getMethod())) {
            return new RemoteControlCommand();
        }
        throw new InvalidRouteException(request.getPathInfo());
    }
    
}
