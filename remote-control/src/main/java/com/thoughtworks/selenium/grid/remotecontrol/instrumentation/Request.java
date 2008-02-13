package com.thoughtworks.selenium.grid.remotecontrol.instrumentation;

import com.thoughtworks.selenium.grid.IOHelper;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringWriter;

/**
 * HTTP Requests
 */
public class Request {

    final static String CRLF = "\r\n";

    private String body;

    public Request(String body) {
        this.body = body;
    }

    public static Request parse(BufferedReader reader) throws IOException {
        readHeader(reader);
        return new Request(readBody(reader));
    }

    public static void readHeader(BufferedReader reader) throws IOException {
        while (reader.ready()) {
            String headerLine = reader.readLine();
            if (headerLine.equals(CRLF) || headerLine.equals("")) {
                break;
            }
        }
    }

    public static String readBody(BufferedReader reader) throws IOException {
        StringWriter body = new StringWriter();
        IOHelper.copyStream(reader, body, 1024);

        return body.toString();
    }


    public String body() {
        return body;
    }

}
