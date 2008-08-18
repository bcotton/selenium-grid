package com.thoughtworks.selenium.grid.agent;

import com.thoughtworks.selenium.grid.webserver.Resource;
import com.thoughtworks.selenium.grid.HttpParameters;

/**
 */
public class AgentResource extends Resource {

    public String process(HttpParameters params) {
        return "Selenium Grid Agent";
    }

}
