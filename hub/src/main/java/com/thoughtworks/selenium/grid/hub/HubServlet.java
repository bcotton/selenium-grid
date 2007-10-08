package com.thoughtworks.selenium.grid.hub;

import com.thoughtworks.selenium.grid.hub.remotecontrol.DynamicRemoteControlPool;
import com.thoughtworks.selenium.grid.hub.remotecontrol.Response;
import com.thoughtworks.selenium.grid.hub.remotecontrol.commands.SeleneseCommand;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Main entry point for the Hub and the Selenium Farm.
 * Load balance selense requests accross a farm of remote control.
 */
public class HubServlet extends HttpServlet {

    private final static Log logger = LogFactory.getLog(HubServer.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        process(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        process(request, response);
    }

    protected void process(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final ServletParametersAdapter parameters;
        final Response remoteControlResponse;

        ApplicationRegistry registry = ApplicationRegistry.registry();
        parameters = requestParameters(request);
        remoteControlResponse = forward(parameters, registry.remoteControlPool(), registry.environmentManager());
        reply(response, remoteControlResponse);
    }

    protected Response forward(ServletParametersAdapter parameters, DynamicRemoteControlPool pool, EnvironmentManager environmentManager) throws IOException {
        final SeleneseCommand command;
        final Response response;

        logger.info("Processing '" + parameters.queryString() + "'");
        try {
            command = new HttpCommandParser(parameters).parse(environmentManager);
            response = command.execute(pool);
        } catch (CommandParsingException e) {
            logger.error("Failed to parse '" + parameters.queryString() + "' : " + e.getMessage());
            return new Response(e.getMessage());
        }
        logger.info("Responding with " + response.statusCode() + "/ '" + response.body() + "'");

        return response;
    }

    protected void reply(HttpServletResponse response, Response remoteControlResponse) throws IOException {
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(remoteControlResponse.statusCode());
        response.getWriter().print(remoteControlResponse.body());
    }

    @SuppressWarnings({"unchecked"})
    protected ServletParametersAdapter requestParameters(HttpServletRequest request) {
        final ServletParametersAdapter parameters;
        parameters = new ServletParametersAdapter(request.getParameterMap());
        return parameters;
    }

}
