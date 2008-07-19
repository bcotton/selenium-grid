package com.thoughtworks.selenium.grid.configuration;

/**
 * Describes configuration of a Hub environment (JavaBean).
 */
public class EnvironmentConfiguration {

    private String name;
    private String browser;

    public EnvironmentConfiguration(String name, String browser) {
        this.name = name;
        this.browser = browser;
    }

    public EnvironmentConfiguration() {
        this(null, null);
    }

    public String getName() {
        return name;
    }

    public String getBrowser() {
        return browser;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setBrowser(String browser) {
        this.browser = browser;
    }

    public String toString() {
        return "[EnvironmentConfiguration name='" + name + "', browser='" + browser + "']";
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }

        final EnvironmentConfiguration otherRemoteControl = (EnvironmentConfiguration) other;
        return compoundKey().equals(otherRemoteControl.compoundKey());
    }

    public int hashCode() {
        return compoundKey().hashCode();
    }


    protected String compoundKey() {
        return name + browser;
    }
}
