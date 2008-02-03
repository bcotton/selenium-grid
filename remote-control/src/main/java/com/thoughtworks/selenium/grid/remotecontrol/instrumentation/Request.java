package com.thoughtworks.selenium.grid.remotecontrol.instrumentation;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * HTTP Requests
 */
public class Request {

    final static String CRLF = "\r\n";

    private String content;

    public Request(String content) {
        this.content = content;
    }

    public static Request parse(BufferedReader reader) throws IOException {
        System.out.println(">>>>>>>>>> parsing header");
        readHeader(reader);
//        System.out.println(">>>>>>>>>> parsing body");
//        readBody(reader);
//        System.out.println(">>>>>>>>>> don with body");
        return new Request("");
    }

    public static void readHeader(BufferedReader reader) throws IOException {
        while(true) {
            String headerLine = reader.readLine();
            System.out.println(headerLine);
            if (headerLine.equals(CRLF) || headerLine.equals("")) break;
        }
    }

    public static void readBody(BufferedReader reader) throws IOException {
//        while (true) {
            String line = reader.readLine();
            System.out.println(">>>>>>>>>>>>>>> " + line);
//            if (null == line || line.equals(CRLF) || line.equals("")) break;
//        }
    }

}
