package com.thoughtworks.selenium.grid.agent;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertSame;
import org.junit.Test;

public class AgentRegistryTest {

    @Test
    public void registryReturnsAValidInstance() {
        assertNotNull(AgentRegistry.registry());
    }

    public void registryReturnsASingletonInstance() {
        assertSame(AgentRegistry.registry(), AgentRegistry.registry());
    }

    @Test
    public void agentConfigurationReturnsAValidInstance() {
        assertNotNull(AgentRegistry.registry().agentConfiguration());
    }

    @Test
    public void agentConfigurationReturnsAUniqueInstance() {
        assertSame(AgentRegistry.registry().agentConfiguration(),
                   AgentRegistry.registry().agentConfiguration());
    }

}
