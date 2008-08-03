package com.thoughtworks.selenium.grid.agent;

import org.junit.Test;
import org.junit.Assert;

public class AgentControllerTest {

    @Test
    public void processReturnAHArdCodedString() {
        Assert.assertEquals("Selenium Grid Agent", new AgentResource().process());
    }
    
}
