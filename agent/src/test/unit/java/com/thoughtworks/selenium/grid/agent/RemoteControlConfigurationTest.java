package com.thoughtworks.selenium.grid.agent;

import org.junit.Test;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;

public class RemoteControlConfigurationTest {

    @Test
    public void portIs4444ByDefault() {
        assertEquals(4444, new RemoteControlConfiguration().port());
    }

    @Test
    public void portCanBeSetToANonDefaultValue() {
        final RemoteControlConfiguration configuration;

        configuration = new RemoteControlConfiguration();
        configuration.setPort(4445);
        assertEquals(4445, configuration.port());

    }

    @Test
    public void hubURLIsLocalByDefault() {
        assertEquals("http://localhost:4444", new RemoteControlConfiguration().hubURL());
    }


    @Test
    public void hostIsAllLocalAdressesByDefault() {
        assertEquals("0.0.0.0", new RemoteControlConfiguration().host());
    }

    @Test
    public void environmentIsChromeByDefault() {
        assertEquals("*chrome", new RemoteControlConfiguration().environment());
    }

    @Test
    public void additionalSeleniumArgsIsNullByDefault() {
        assertEquals(null, new RemoteControlConfiguration().additionalSeleniumArgs());
    }

}
