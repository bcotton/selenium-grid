package com.thoughtworks.selenium.grid;


public class Response {

    private final int statusCode;
    private final String body;


    public Response(String errorMessage) {
        this(200, "ERROR: " + errorMessage);
    }

    public Response(int statusCode, String body) {
        this.statusCode = statusCode;
        this.body = body;
    }

    public int statusCode() {
        return statusCode;
    }

    public String body() {
        return body;
    }

}
