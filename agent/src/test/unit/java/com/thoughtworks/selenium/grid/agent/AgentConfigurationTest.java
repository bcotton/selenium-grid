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

    @Test
    public void defaultRemoteControlWrapperJarPathMatchesDistributionLayout() {
        assertEquals("lib/selenium-grid-remote-control-standalone-1.0.1.jar",
                     new AgentConfiguration().remoteControlWrapperJarPath());
    }

    @Test
    public void defaultRemoteControlWrapperJarPathCanBeSetToASpecificValue() {
        final AgentConfiguration configuration = new AgentConfiguration();
        configuration.setRemoteControlWrapperJarPath("./a/new/file.jar");
        assertEquals("./a/new/file.jar", configuration.remoteControlWrapperJarPath());
    }

    @Test
    public void defaultRemoteControlJarPathMatchesDistributionLayout() {
        assertEquals("lib/selenium-server-1.0-SNAPSHOT.jar",
                     new AgentConfiguration().remoteControlJarPath());
    }

    @Test
    public void defaultRemoteControlJarPathCanBeSetToASpecificValue() {
        final AgentConfiguration configuration = new AgentConfiguration();
        configuration.setRemoteControlJar("./a/new/file.jar");
        assertEquals("./a/new/file.jar", configuration.remoteControlJarPath());
    }

}