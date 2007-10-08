package com.thoughtworks.selenium.grid.configuration;

import static junit.framework.Assert.assertEquals;
import org.junit.Test;

public class HubConfigurationTest {

    @Test
    public void defaultPortIs4444() {
        assertEquals(4444, new HubConfiguration().getPort());
    }

    @Test
    public void portCanBeSetToANonDefaultValue() {
        final HubConfiguration configuration = new HubConfiguration();
        configuration.setPort(5555);
        assertEquals(5555, configuration.getPort());
    }

    @Test
    public void defaultEnvironmentsArrayIsEmpty() {
        assertEquals(0, new HubConfiguration().getEnvironments().length);
    }

    @Test
    public void environmentsCanBeSetToANewValue() {
        final EnvironmentConfiguration[] expected;
        final HubConfiguration hubConfiguration;

        expected = new EnvironmentConfiguration[] {new EnvironmentConfiguration("a name", "a browser") };
        hubConfiguration = new HubConfiguration();
        hubConfiguration.setEnvironments(expected);
        assertEquals(expected, hubConfiguration.getEnvironments());
    }


}