package com.thoughtworks.selenium.grid.remotecontrol.instrumentation;

import com.thoughtworks.selenium.grid.IOHelper;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Single-threaded basic HTTP Server used only for testing purposes. No pretention to be robust,
 * compliant or scalable: Only strives to be lightweight and good enough for Selenium Grid testing purposes.
 *
 * @author Philippe Hanrigou
 */
public class SimplisticHttpServer {

    private static final Log LOGGER = LogFactory.getLog(SimplisticHttpServer.class);
    private final HttpRequestProcessor requestProcessor;
    private final int port;

    public SimplisticHttpServer(int port, HttpRequestProcessor requestProcessor) {
        this.requestProcessor = requestProcessor;
        this.port = port;
    }

    public void start() throws Exception {
        final ServerSocket serverSocket;
        
        serverSocket = new ServerSocket(port);
        LOGGER.info("Now listening for incoming connections on " + serverSocket.getLocalSocketAddress() + ":" + serverSocket.getLocalPort());
        while (true) {
            processHttpRequest(serverSocket.accept());
        }
    }

    protected void processHttpRequest(Socket socket) throws Exception {
        BufferedReader reader = null;
        BufferedWriter writer = null;

        LOGGER.info("Accepted connection from" + socket.getInetAddress() + ":" + socket.getPort());
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            requestProcessor.process(Request.parse(reader)).write(writer);
        } finally {
            IOHelper.close(writer);
            IOHelper.close(reader);
            IOHelper.close(socket);
        }
    }


}





