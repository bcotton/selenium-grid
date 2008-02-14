package com.thoughtworks.selenium.grid.demo;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import com.thoughtworks.selenium.Selenium;
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
public class WebTestForASingleBrowser extends AmazonCommentTestBase {


    @Test(groups = {"demo", "firefox", "default"}, description = "Test Amazon Comment Quality Using the Firefox Web Browser (1).")
    @Parameters({"seleniumHost", "seleniumPort", "browser", "webSite"})
    public void firstTest(String seleniumHost, int seleniumPort, String browser, String webSite) throws Throwable {
        createSeleniumDriver(seleniumHost, seleniumPort, browser, webSite);
        runAmazonScenario();
        closeSeleniumSession();
    }

    @Test(groups = {"demo", "firefox", "default"}, description = "Test Amazon Comment Quality Using the Firefox Web Browser (2).")
    @Parameters({"seleniumHost", "seleniumPort", "browser", "webSite"})
    public void secondTest(String seleniumHost, int seleniumPort, String browser, String webSite) throws Throwable {
        createSeleniumDriver(seleniumHost, seleniumPort, browser, webSite);
        runAmazonScenario();
        closeSeleniumSession();
        closeSeleniumSession();
    }

    @Test(groups = {"demo", "firefox", "default"}, description = "Test Amazon Comment Quality Using the Firefox Web Browser (3).")
    @Parameters({"seleniumHost", "seleniumPort", "browser", "webSite"})
    public void thirdTest(String seleniumHost, int seleniumPort, String browser, String webSite) throws Throwable {
        createSeleniumDriver(seleniumHost, seleniumPort, browser, webSite);
        runAmazonScenario();
        closeSeleniumSession();
    }

    @Test(groups = {"demo", "firefox", "default"}, description = "Test Amazon Comment Quality Using the Firefox Web Browser (4).")
    @Parameters({"seleniumHost", "seleniumPort", "browser", "webSite"})
    public void fourthTest(String seleniumHost, int seleniumPort, String browser, String webSite) throws Throwable {
        createSeleniumDriver(seleniumHost, seleniumPort, browser, webSite);
        runAmazonScenario();
        closeSeleniumSession();
    }


}



