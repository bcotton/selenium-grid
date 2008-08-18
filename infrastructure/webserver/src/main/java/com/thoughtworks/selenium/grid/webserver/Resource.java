package com.thoughtworks.selenium.grid.webserver;

import com.thoughtworks.selenium.grid.HttpParameters;

/**
 */
public abstract class Resource {

    public abstract String process(HttpParameters params);
    
}
