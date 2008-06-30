package com.thoughtworks.selenium.grid.remotecontrol.instrumentation;

import com.thoughtworks.selenium.grid.IOHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringWriter;

/**
 * HTTP Requests
 */
public class Request {

    private final static String CRLF = "\r\n";
    private final String body;

    public Request(String body) {
        this.body = body;
    }

    public static Request parse(BufferedReader reader) throws IOException {
        readHeader(reader);
        return new Request(readBody(reader));
    }

    public static void readHeader(BufferedReader reader) throws IOException {
        while (reader.ready()) {
            final String headerLine;
            
            headerLine = reader.readLine();
            if (CRLF.equals(headerLine) || "".equals(headerLine)) {
                break;
            }
        }
    }

    public static String readBody(BufferedReader reader) throws IOException {
        final StringWriter body;

        body = new StringWriter();
        IOHelper.copyStream(reader, body, 1024);
        return body.toString();
    }


    public String body() {
        return body;
    }

}
