package com.thoughtworks.selenium.grid.agent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/*
* Agent application registry.
* <p/>
* Provides access to configuration.
*/
public class AgentRegistry {

    private static final Log LOGGER = LogFactory.getLog(AgentRegistry.class);
    private static AgentRegistry singleton;
    private AgentConfiguration agentConfiguration;

    public static synchronized AgentRegistry registry() {
        if (null == singleton) {
            singleton = new AgentRegistry();
        }
        return singleton;
    }

    public synchronized AgentConfiguration agentConfiguration() {
        if (null == agentConfiguration) {
            agentConfiguration = new AgentConfiguration();
            LOGGER.info("Loaded agent configuration:\n" + agentConfiguration);
        }
        return agentConfiguration;
    }

}
