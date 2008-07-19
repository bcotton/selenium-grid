package com.thoughtworks.selenium.grid.webserver;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.ContextHandlerCollection;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.servlet.ServletHolder;

/**
 * Self contained Selenium Grid Hub. Uses Jetty to as a standalone web application.
 */
public class WebServer {

    private final int port;
    private final Class routeResolverClass;
    private Server httpServer;


    public WebServer(int port, Class routeResolverClass) {
        this.port = port;
        this.routeResolverClass = routeResolverClass;
    }

    public int port() {
        return port;
    }

    public void launch() throws Exception {
        createHttpServer();
        startListeningForIncomingRequests();
        waitForShutdown();
    }

    protected void startListeningForIncomingRequests() throws Exception {
        httpServer().start();
    }

    protected void waitForShutdown() throws InterruptedException {
        httpServer().join();
    }

    protected void createHttpServer() {
        final ContextHandlerCollection contexts;
        final ServletHolder servletHolder;
        final Context root;

        httpServer = new Server(port);
        contexts = new ContextHandlerCollection();
        httpServer.setHandler(contexts);

        root = new Context(contexts, "/", Context.SESSIONS);
        servletHolder = new ServletHolder(new MainServlet());
        servletHolder.setInitParameter("route_resolver", routeResolverClass().getName());
        root.addServlet(servletHolder, "/*");
    }


    protected Class routeResolverClass() {
        return routeResolverClass;
    }

    protected Server httpServer() {
        return httpServer;
    }

}