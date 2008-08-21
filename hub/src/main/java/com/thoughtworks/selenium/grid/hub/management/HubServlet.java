package com.thoughtworks.selenium.grid.hub.management;

import com.thoughtworks.selenium.grid.hub.HubRegistry;

import javax.servlet.http.HttpServlet;

/**
 * Base class for all servlets exposed by Selenium Grid Hub.
 *
 *
 * Provides access to the global application registry via a method
 * that can easily be overriden in unit tests.
 */
public class HubServlet extends HttpServlet {

    /**
     * Application Registry (singleton)
     *
     * @return A valid application registry. Never null.
     */
    protected HubRegistry registry() {
        return HubRegistry.registry();
    }
    
}
