package com.thoughtworks.selenium.grid.hub;

import static junit.framework.Assert.*;
import org.junit.Test;


public class ApplicationRegistryTest {

    @Test
    public void registryReturnsAValidInstance() {
        assertFalse(null == ApplicationRegistry.registry());
    }

    public void registryReturnsASingletonInstance() {
        assertSame(ApplicationRegistry.registry(), ApplicationRegistry.registry());
    }

    @Test
    public void remoteControlPoolReturnsAValidPool() {
        assertFalse(null == ApplicationRegistry.registry().remoteControlPool());
    }

    @Test
    public void environmentManagerReturnsAUniqueInstance() {
        assertSame(ApplicationRegistry.registry().environmentManager(),
                   ApplicationRegistry.registry().environmentManager());
    }

    @Test
    public void gridConfigurationReturnsAUniqueInstance() {
        assertSame(ApplicationRegistry.registry().gridConfiguration(),
                   ApplicationRegistry.registry().gridConfiguration());
    }

}
