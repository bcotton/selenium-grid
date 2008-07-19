package com.thoughtworks.selenium.grid.configuration;

import org.ho.yaml.Yaml;

import java.io.Reader;
import java.io.StringReader;

/**
 * Selenium Grid Configuration.
 */
public class GridConfiguration {

    private HubConfiguration hub;

    public GridConfiguration() {
        this.hub = new HubConfiguration();
    }

    public HubConfiguration getHub() {
        return hub;
    }

    public void setHub(HubConfiguration hub) {
        this.hub = hub;
    }

    public static GridConfiguration parse(String yamlDefinition) {
        return parse(new StringReader(yamlDefinition));
    }

    public String toYAML() {
        return Yaml.dump(this, true);
    }

    protected static GridConfiguration parse(Reader yamlDefinition) {
        return Yaml.loadType(yamlDefinition, GridConfiguration.class);
    }

}
