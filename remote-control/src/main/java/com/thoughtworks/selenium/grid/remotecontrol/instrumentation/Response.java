package com.thoughtworks.selenium.grid.remotecontrol.instrumentation;

import java.io.BufferedWriter;
import java.io.IOException;

/**
 * HTTP Response
 *
 * @author Philippe Hanrigou
 */
public class Response {
    private static final String CRLF = "\r\n";
    private final String body;

    public Response(String body) {
        this.body = body;
    }


    public void write(BufferedWriter writer) throws IOException {
        writer.write("HTTP/1.0 200 OK" + CRLF);
        writer.write("Server: Selenium Grid Echo Remote Control" + CRLF);
        writer.write("Content-type: text/html" + CRLF);

// Send a blank line to indicate the end of the header lines.
        writer.write(CRLF);

        writer.write(body);
    }

}
