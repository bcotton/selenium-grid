package com.thoughtworks.selenium.grid.demo;

import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

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
public class WebTestInvolvingMultiEnvironments extends AmazonCommentTestBase {

    @Parameters({"seleniumHost", "seleniumPort", "firstEnvironment"})
    @Test(groups = {"demo", "multiEnvironment"}, description = "Test Amazon Comment Quality Using the Firefox Web Browser on Window.")
    public void testForFirstEnvironment(String seleniumHost, int seleniumPort, String firstEnvironment) throws Throwable {
        runAmazonScenario(seleniumHost, seleniumPort, firstEnvironment);
    }

    @Parameters({"seleniumHost", "seleniumPort", "secondEnvironment"})
    @Test(groups = {"demo", "multiEnvironment"}, description = "Test Amazon Comment Quality Using the Firefox Web Browser on Windows (2).")
    public void testForSecondEnvironment(String seleniumHost, int seleniumPort, String secondEnvironment) throws Throwable {
        runAmazonScenario(seleniumHost, seleniumPort, secondEnvironment);
    }

    @Parameters({"seleniumHost", "seleniumPort", "thirdEnvironment"})
    @Test(groups = {"demo", "multiEnvironment"}, description = "Test Amazon Comment Quality Using the Firefox Web Browser on the Mac.")
    public void testForThirdEnvironment(String seleniumHost, int seleniumPort, String thirdEnvironment) throws Throwable {
        runAmazonScenario(seleniumHost, seleniumPort, thirdEnvironment);
    }

}