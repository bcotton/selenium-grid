package com.thoughtworks.selenium.grid.hub;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertSame;
import org.junit.Test;


public class ApplicationRegistryTest {

    @Test
    public void registryReturnsASingletonInstance() {
        assertSame(ApplicationRegistry.registry(), ApplicationRegistry.registry());
    }

//    @Test
//    public void registryReturnsAValidInstance() {
//        assertFalse(null == ApplicationRegistry.registry());
//    }
//
//    @Test
//    public void remoteControlPoolReturnsAValidPool() {
//        assertFalse(null == ApplicationRegistry.registry().remoteControlPool());
//    }
//
//    @Test
//    public void environmentManagerReturnsAUniqueInstance() {
//        assertSame(ApplicationRegistry.registry().environmentManager(),
//                   ApplicationRegistry.registry().environmentManager());
//    }

//    @Test
//    public void gridConfigurationReturnsAUniqueInstance() {
//        assertSame(ApplicationRegistry.registry().gridConfiguration(),
//                   ApplicationRegistry.registry().gridConfiguration());
//    }

}
