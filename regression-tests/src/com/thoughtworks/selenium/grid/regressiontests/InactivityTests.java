package com.thoughtworks.selenium.grid.regressiontests;

import com.thoughtworks.selenium.grid.tools.ThreadSafeSeleniumSessionStorage;
import org.testng.AssertJUnit;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/**
 * Check that Selenium Grid handles Unicode Characters properly
 */
public class InactivityTests {

    @Test(groups = {"regression"}, description = "Connection does not go away after some period of inactivity")
    @Parameters({"seleniumHost", "seleniumPort", "browser"})
    public void timeout(String seleniumHost, int seleniumPort, String browser) throws Throwable {
        try {
            ThreadSafeSeleniumSessionStorage.startSeleniumSession(seleniumHost, seleniumPort, browser, "http://amazon.com");
            ThreadSafeSeleniumSessionStorage.session().open("/");
            ThreadSafeSeleniumSessionStorage.session().getLocation();
            Thread.sleep(120000);
            ThreadSafeSeleniumSessionStorage.session().getLocation();
        } finally {
            ThreadSafeSeleniumSessionStorage.closeSeleniumSession();

        }
    }

}