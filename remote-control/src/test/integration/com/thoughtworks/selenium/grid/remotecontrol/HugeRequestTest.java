package com.thoughtworks.selenium.grid.remotecontrol;

import org.junit.Test;
import com.thoughtworks.selenium.Selenium;
import com.thoughtworks.selenium.DefaultSelenium;

/**
 * Test sending a huge request via the Selenium Driver. Make sure it passes through the Hub.
 */
public class HugeRequestTest {


    @Test
    public void typeHugetextBlock() throws Exception {
        final String hugeText = hugeText();
        final Selenium seleniumDriver = new DefaultSelenium("localhost", 4444, "*chrome", "http://amazon.com");
        seleniumDriver.start();

        seleniumDriver.open("/");
        seleniumDriver.type("twotabsearchtextbox", hugeText);
        seleniumDriver.stop();
    }

    protected String hugeText() {
        final StringBuilder builder = new StringBuilder(10 * 1024);
        for (int i = 0; i < 1024; i++) {
            builder.append("1234567890");
        }
        return builder.toString();
    }

}
