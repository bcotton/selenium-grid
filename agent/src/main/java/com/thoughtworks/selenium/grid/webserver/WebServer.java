package com.thoughtworks.selenium.grid.webserver;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.ContextHandlerCollection;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.servlet.ServletHolder;
import org.mortbay.component.AbstractLifeCycle;

/**
 * Self contained Selenium Grid Hub. Uses Jetty to as a standalone web application.
 */
public class WebServer {

    private final int port;
    private final RouteResolver routeResolver;
    private Server httpServer;


    public WebServer(int port, RouteResolver routeResolver) {
        this.port = port;
        this.routeResolver = routeResolver;
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
        final Context root;

        httpServer = new Server(port);
        contexts = new ContextHandlerCollection();
        httpServer.setHandler(contexts);

        root = new Context(contexts, "/", Context.SESSIONS);
        root.addServlet(new ServletHolder(new MainServlet()), "/*");
    }


    protected RouteResolver routeResolver() {
        return routeResolver;
    }

    protected Server httpServer() {
        return httpServer;
    }

}