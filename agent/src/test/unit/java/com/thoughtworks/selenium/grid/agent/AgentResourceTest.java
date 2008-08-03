package com.thoughtworks.selenium.grid.agent;

import org.junit.Test;
import org.junit.Assert;

public class AgentResourceTest {

    @Test
    public void processReturnAHardCodedString() {
        Assert.assertEquals("Selenium Grid Agent", new AgentResource().process());
    }
    
}
