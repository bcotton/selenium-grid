package com.thoughtworks.selenium.grid.examples.java;

import static com.thoughtworks.selenium.grid.tools.ThreadSafeSeleniumSessionStorage.closeSeleniumSession;
import static com.thoughtworks.selenium.grid.tools.ThreadSafeSeleniumSessionStorage.startSeleniumSession;
import static com.thoughtworks.selenium.grid.tools.ThreadSafeSeleniumSessionStorage.session;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import static org.testng.AssertJUnit.assertTrue;
import static org.testng.AssertJUnit.assertEquals;


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
public class TechnicalBookTest {


    @Test(groups = {"demo", "firefox", "default"}, description = "Test Amazon Comment Quality Using the Firefox Web Browser (1).")
    @Parameters({"seleniumHost", "seleniumPort", "browser", "webSite"})
    public void firstTest(String seleniumHost, int seleniumPort, String browser, String webSite) throws Throwable {
        startSeleniumSession(seleniumHost, seleniumPort, browser, webSite);
        session().open("/");
        session().type("twotabsearchtextbox", "refactoring");
        session().click("navGoButtonPanel");
        session().waitForPageToLoad("60000");
        assertTrue(session().getLocation().startsWith("http://www.amazon.com/s/ref="));
        session().click("//img[@alt='Refactoring: Improving the Design of Existing Code (The Addison-Wesley Object Technology Series)']");
        session().waitForPageToLoad("60000");
        assertEquals("1", session().getValue("name=quantity"));
        assertTrue(session().isTextPresent("excellent"));
        assertTrue(session().isTextPresent("Hidden Treasure"));
        closeSeleniumSession();
    }

    @Test(groups = {"demo", "firefox", "default"}, description = "Test Amazon Comment Quality Using the Firefox Web Browser (2).")
    @Parameters({"seleniumHost", "seleniumPort", "browser", "webSite"})
    public void secondTest(String seleniumHost, int seleniumPort, String browser, String webSite) throws Throwable {
        startSeleniumSession(seleniumHost, seleniumPort, browser, webSite);
        session().open("/");
        session().type("twotabsearchtextbox", "refactoring");
        session().click("navGoButtonPanel");
        session().waitForPageToLoad("60000");
        assertTrue(session().getLocation().startsWith("http://www.amazon.com/s/ref="));
        session().click("//img[@alt='Refactoring: Improving the Design of Existing Code (The Addison-Wesley Object Technology Series)']");
        session().waitForPageToLoad("60000");
        assertEquals("1", session().getValue("name=quantity"));
        assertTrue(session().isTextPresent("excellent"));
        assertTrue(session().isTextPresent("Hidden Treasure"));
        closeSeleniumSession();
    }



}