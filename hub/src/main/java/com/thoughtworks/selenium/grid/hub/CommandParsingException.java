package com.thoughtworks.selenium.grid.hub;

/**
 * Signals that a problem was detected while parsing a Selenese request.
 */
public class CommandParsingException extends RuntimeException {

    public CommandParsingException(String message) {
        super(message);
    }

}
