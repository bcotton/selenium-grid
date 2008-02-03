package com.thoughtworks.selenium.grid.remotecontrol.instrumentation;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.net.ServerSocket;
import java.net.Socket;
import java.io.*;

import com.thoughtworks.selenium.grid.IOHelper;

/**
 * Single-threaded basic HTTP Server used only for testing purposes. No pretention to be robust,
 * compliant or scalable: Only strives to be lightweight and good enough for Selenium Grid testing purposes.
 *
 * @author Philippe Hanrigou
 */
public class SimplisticHttpServer {

    private static final Log logger = LogFactory.getLog(SimplisticHttpServer.class);
    private final int port;

    public SimplisticHttpServer(int port) {
        this.port = port;
    }

    public void start() throws Exception {
        ServerSocket serverSocket = new ServerSocket(port);
        logger.info("Now listening for incoming connections on " + serverSocket.getLocalSocketAddress() + ":" + serverSocket.getLocalPort());
        while (true) {
            processHttpRequest(serverSocket.accept());
        }
    }

    public void processHttpRequest(Socket socket) throws Exception {
        BufferedReader reader = null;
        BufferedWriter writer = null;

        logger.info("Accepted connection from" + socket.getInetAddress() + ":" + socket.getPort());
        try {
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
            process(Request.parse(reader)).write(writer);
        } finally {
            IOHelper.close(writer);
            IOHelper.close(reader);
            IOHelper.close(socket);
        }
    }

    public Response process(Request request) {
        return new Response("Echo");
    }

}





