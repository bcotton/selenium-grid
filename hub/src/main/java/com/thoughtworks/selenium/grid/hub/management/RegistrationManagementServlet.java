package com.thoughtworks.selenium.grid.hub.management;

import com.thoughtworks.selenium.grid.hub.HubRegistry;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;


public abstract class RegistrationManagementServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        process(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        process(request, response);
    }

    protected abstract void process(HttpServletRequest request, HttpServletResponse response) throws IOException;

    protected void witeSuccessfulResponse(HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().print("OK");
    }

    protected HubRegistry registry() {
        return HubRegistry.registry();
    }

}
