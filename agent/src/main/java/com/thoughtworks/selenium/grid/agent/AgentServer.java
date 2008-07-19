package com.thoughtworks.selenium.grid.agent;

import com.thoughtworks.selenium.grid.webserver.WebServer;

/**
 * TODO
 */
public class AgentServer extends WebServer {

    public AgentServer(int port) {
        super(port, RouteResolver.class);
    }

    public static void main(String[] args) throws Exception {
        final AgentServer server;

        server = new AgentServer(4443);
        server.launch();
    }

}