package com.thoughtworks.selenium.grid.webserver;

import com.thoughtworks.selenium.grid.agent.RouteResolver;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 */
public class MainServlet extends HttpServlet {

    private final RouteResolver routeResolver;

    public MainServlet() {
        this.routeResolver = new RouteResolver();
    }

    public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        final Controller controller;
        final String page;

        controller = routeResolver().resolve(request);
        page = controller.process();
        render(page, response);

    }

    public void render(String page, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(page);
    }


    protected RouteResolver routeResolver() {
        return routeResolver;
    }

}