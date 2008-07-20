package com.thoughtworks.selenium.grid.hub.management.console;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import com.thoughtworks.selenium.grid.hub.HubRegistry;

/**
 * Gateway to Selenium Farm.
 *
 * @author Philippe Hanrigou
 */
public class ConsoleServlet extends HttpServlet {

    private static final Log logger = LogFactory.getLog(ConsoleServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        logger.debug("Rendering console...");
        controller().process(response);
    }

    protected ConsoleController controller() {
        return new ConsoleController(HubRegistry.registry());
    }
}