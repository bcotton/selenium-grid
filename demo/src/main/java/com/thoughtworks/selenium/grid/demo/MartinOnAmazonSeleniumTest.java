package com.thoughtworks.selenium.grid.demo;

import static junit.framework.Assert.assertEquals;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;
import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * Traditional Selenium Test checking the quality of Amazon comments ;o).
 * <br/>
 * Each test request a different browser/environment.
 * <br/>
 * <code>testAmazonOnFirefox</code> can run against a plain vanilla
 * Selenium remote control.
 * <br/>
 * The other tests need to run again a Selenium Hub: They demonstrate
 * the capacity of requesting a specific environment per
 * test/test suite/build. Of course these environments must be defined
 * on the Hub and at least one remote control must register as providing
 * this particular environment.
 */
public class MartinOnAmazonSeleniumTest extends TestCase {

    private Selenium selenium;

    public MartinOnAmazonSeleniumTest(String s) {
        super(s);
    }

    public void tearDown() throws Exception {
        selenium.stop();
    }

    public void testAmazonOnFirefox() throws Throwable {
        runAmazonScript("*chrome");
    }

    public void testAmazonOnFirefoxOnWindows() throws Throwable {
        runAmazonScript("Firefox on Windows");
    }

    public void testAmazonOnFirefoxOnLinux() throws Throwable {
        runAmazonScript("Firefox on Linux");
    }

    public void testAmazonOnFirefoxOnMac() throws Throwable {
        runAmazonScript("Firefox on Mac");
    }

    public void testAmazonOnIEOnWindows() throws Throwable {
        // windows implicitly
        runAmazonScript("IE on Windows");
    }

    protected void runAmazonScript(String browser) {
        createDriver(4444, "http://amazon.com", browser);
        selenium.open("/");
        selenium.type("twotabsearchtextbox", "refactoring");
        selenium.click("navGoButtonPanel");
        selenium.waitForPageToLoad("60000");
        assertTrue(selenium.getLocation().startsWith("http://www.amazon.com/s/ref="));
        selenium.click("//img[@alt='Refactoring: Improving the Design of Existing Code (The Addison-Wesley Object Technology Series)']");
        selenium.waitForPageToLoad("60000");
        assertEquals("1", selenium.getValue("name=quantity"));
        assertTrue(selenium.isTextPresent("excellent"));
        assertTrue(selenium.isTextPresent("Hidden Treasure"));
    }

    protected void createDriver(int port, String url, String browser) {
        selenium = new DefaultSelenium("localhost", port, browser, url);
        selenium.start();
    }

}
