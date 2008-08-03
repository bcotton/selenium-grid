package com.thoughtworks.selenium.grid.agent;

import com.thoughtworks.selenium.grid.webserver.Resource;
import com.thoughtworks.selenium.grid.webserver.InvalidRouteException;

import javax.servlet.http.HttpServletRequest;

/**
 * Resolve an HTTP request to a specific controller.
 */
public class RouteResolver implements com.thoughtworks.selenium.grid.webserver.RouteResolver {

    public Resource resolve(HttpServletRequest request) {
        if ("/".equals(request.getPathInfo())) {
            return new AgentResource();
        }
        throw new InvalidRouteException(request.getPathInfo());
    }
    
}
