package com.thoughtworks.selenium.grid.hub;

/**
 * Environment offered by the Selenium Farm. For instance 'Firefox on Windows' or 'IE 6.0, Chinese locale'.
 * <br/>
 * The exact semantics of a specific environment are defined by the people operating the grid;
 * From the Hub point of view an environment is just a mapping between an arbitrary string and
 * a browser definition understood by plain vanilla remote controls.
 * <br/>
 * Each remote control that is part of the Selenium farm registers itself to the Hub as providing a
 * specific environment.
 *
 * @see com.thoughtworks.selenium.grid.hub.management.RegistrationServlet
 * @see com.thoughtworks.selenium.grid.hub.remotecontrol.DynamicRemoteControlPool#reserve(Environment)
 */
public class Environment {

    private final String name;
    private final String browser;

    public Environment(String name, String browser) {
        if (null == name) {
            throw new IllegalArgumentException("name cannot be null");
        }
        this.name = name;
        this.browser = browser;
    }

    public String name() {
        return name;
    }

    public String browser() {
        return browser;
    }

    public String toString() {
        return "[Environment name='" + name + "', browser='" + browser + "']";
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        final Environment otherRemoteControl = (Environment) other;
        return name.equals(otherRemoteControl.name);
    }

    public int hashCode() {
        return name.hashCode();
    }

}
