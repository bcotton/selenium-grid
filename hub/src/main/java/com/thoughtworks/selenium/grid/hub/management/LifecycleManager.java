package com.thoughtworks.selenium.grid.hub.management;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Manages lifecyle of Hub components
 */
public class LifecycleManager {

    private static final Log LOGGER = LogFactory.getLog(LifecycleManager.class);

    public void shutdown() {
        LOGGER.info("Shutting down Hub...");
        System.exit(0);
    }
    
}
