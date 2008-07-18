package com.thoughtworks.selenium.grid.webserver;

import com.thoughtworks.selenium.grid.webserver.Controller;

import javax.servlet.http.HttpServletRequest;

/**
 * Resolve an HTTP request to a specific controller.
 */
public interface RouteResolver {

    Controller resolve(HttpServletRequest request);
    
}