package com.thoughtworks.selenium.grid.hub.management.console.mvc;

/**
 * Signals a problem in template rendering.
 */
public class RenderingException extends RuntimeException {

    public RenderingException(Throwable t) {
        super(t);
    }

    public RenderingException(String message) {
        super(message);
    }

}
