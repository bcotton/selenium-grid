package com.thoughtworks.selenium.grid.hub.management;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;

/**
 * Servlet in charge of shutting down the Hub.
 */
public class LifecycleManagerServlet extends HubServlet {

    private static final Log LOGGER = LogFactory.getLog(LifecycleManagerServlet.class);

    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        final String action;

        action = request.getParameter("action");
        LOGGER.info("Requesting life cycle manager action : '" + action + "'");
        if ("shutdown".equals(action)) {
            registry().lifecycleManager().shutdown();
        }
    }

}
