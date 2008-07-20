package com.thoughtworks.selenium.grid.agent;

import static junit.framework.Assert.assertEquals;
import org.junit.Test;

public class AgentConfigurationTest {

    @Test
    public void defaultPortIs4443() {
        assertEquals(4443, new AgentConfiguration().getPort());
    }

    @Test
    public void portCanBeSetToANonDefaultValue() {
        final AgentConfiguration configuration = new AgentConfiguration();
        configuration.setPort(5555);
        assertEquals(5555, configuration.getPort());
    }

}