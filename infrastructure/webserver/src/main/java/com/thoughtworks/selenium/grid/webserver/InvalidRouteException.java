package com.thoughtworks.selenium.grid.webserver;

/**
 * Indicates that an HTTP route in invalid and/or cannot be resolved.
 */
public class InvalidRouteException extends RuntimeException {

    private final String path;

    public InvalidRouteException(String path) {
        super();
        this.path = path;
    }

    public String path() {
        return path;
    }

    public String toString() {
        return "[InvalidRouteException path='" + path + "']";
    }
}
