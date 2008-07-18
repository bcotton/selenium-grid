package com.thoughtworks.selenium.grid.agent;

import com.thoughtworks.selenium.grid.webserver.Controller;
import com.thoughtworks.selenium.grid.webserver.InvalidRouteException;

import javax.servlet.http.HttpServletRequest;

/**
 * Resolve an HTTP request to a specific controller.
 */
public class RouteResolver implements com.thoughtworks.selenium.grid.webserver.RouteResolver {

    public Controller resolve(HttpServletRequest request) {
        if ("/".equals(request.getPathInfo())) {
            return new AgentController();
        }
        throw new InvalidRouteException(request.getPathInfo());
    }
    
}
