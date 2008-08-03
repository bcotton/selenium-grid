package com.thoughtworks.selenium.grid.webserver;

import javax.servlet.http.HttpServletRequest;

/**
 * Resolve an HTTP request to a specific resource.
 */
public interface RouteResolver {

    Resource resolve(HttpServletRequest request);
    
}