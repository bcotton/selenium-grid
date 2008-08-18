package com.thoughtworks.selenium.grid.webserver;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 */
public class MainServlet extends HttpServlet {

    private RouteResolver routeResolver;


    public void service(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        final Resource resource;
        final String page;

        resource = routeResolver().resolve(request);
        page = resource.process(request.getParameterMap());
        render(page, response);
    }

    public void render(String page, HttpServletResponse response) throws IOException {
        response.setContentType("text/plain");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(page);
    }


    protected RouteResolver routeResolver() {
        if (null == routeResolver) {
          routeResolver = createRouteResolver();
        }
        return routeResolver;
    }

    protected RouteResolver createRouteResolver() {
        final String routeResolverClassName;
        final Class routeResolverClass;

        routeResolverClassName = getServletConfig().getInitParameter("route_resolver");
        try {
            routeResolverClass = Class.forName(routeResolverClassName);
            return (RouteResolver) routeResolverClass.newInstance();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Could not find route resolver '" + routeResolverClassName + "'", e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException("Could not access class or its no-arg constructor '" + routeResolverClassName + "'", e);
        } catch (InstantiationException e) {
            throw new RuntimeException("Could not find no arg constructor for '" + routeResolverClassName + "'", e);
        }
    }

}