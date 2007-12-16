package com.thoughtworks.selenium.grid.demo;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;
import static org.testng.AssertJUnit.assertTrue;
import static org.testng.AssertJUnit.assertEquals;

/**
 * Base class for Web Acceptance tests
 */
public abstract class AmazonCommentTestBase {

    /** Thread local Selenium driver instance so that we can run in multi-threaded mode. */
    ThreadLocal<Selenium> threadLocalSelenium = new ThreadLocal<Selenium>();

    @org.testng.annotations.AfterTest(groups = "demo")
    @org.testng.annotations.AfterClass
    public void closeSeleniumSession() throws Exception {
        if (null != seleniumDriver()) {
            seleniumDriver().stop();
            resetSeleniumDriver();
        }
    }

    protected void runAmazonScenario(String seleniumHost, int seleniumPort, String browser) throws Exception {

        createSeleniumDriver(seleniumHost, seleniumPort, "http://amazon.com", browser);
        final Selenium seleniumDriver = threadLocalSelenium.get();
        seleniumDriver.open("/");
        seleniumDriver.type("twotabsearchtextbox", "refactoring");
        seleniumDriver.click("navGoButtonPanel");
        seleniumDriver.waitForPageToLoad("60000");
        assertTrue(seleniumDriver.getLocation().startsWith("http://www.amazon.com/s/ref="));
        seleniumDriver.click("//img[@alt='Refactoring: Improving the Design of Existing Code (The Addison-Wesley Object Technology Series)']");
        seleniumDriver.waitForPageToLoad("60000");
        assertEquals("1", seleniumDriver.getValue("name=quantity"));
        assertTrue(seleniumDriver.isTextPresent("excellent"));
        assertTrue(seleniumDriver.isTextPresent("Hidden Treasure"));

        closeSeleniumSession();
    }

    protected void createSeleniumDriver(String seleniumHost, int port, String url, String browser) {
        threadLocalSelenium.set(new DefaultSelenium(seleniumHost, port, browser, url));
        seleniumDriver().start();
    }

    protected Selenium seleniumDriver() {
      return threadLocalSelenium.get();        
    }

    protected void resetSeleniumDriver() {
      threadLocalSelenium.set(null);
    }

}
