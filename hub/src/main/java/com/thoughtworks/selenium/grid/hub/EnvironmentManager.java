package com.thoughtworks.selenium.grid.hub;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Keep track of the environments offered by the Selenium farm.
 */
public class EnvironmentManager {

    private final Map<String, Environment> environmentMap;

    public EnvironmentManager() {
        this.environmentMap = new HashMap<String, Environment>();
    }

    public List<Environment> environments() {
        return new ArrayList<Environment>(environmentMap.values());
    }

    public void addEnvironment(Environment newEnvironment) {
        synchronized (environmentMap) {
            environmentMap.put(newEnvironment.name(), newEnvironment);
        }
    }

    public Environment environment(String environmentName) {
        synchronized (environmentMap) {
            return environmentMap.get(environmentName);
        }
    }

}
