package com.thoughtworks.selenium.grid.regressiontests;

import org.testng.annotations.Parameters;
import com.thoughtworks.selenium.grid.tools.ThreadSafeSeleniumSessionStorage;

/**
 * Check that Selenium Grid handles Unicode Characters properly
 */
public class HugeRequestTests {

    @org.testng.annotations.Test(groups = {"regression" }, description = "Type a huge chunk of text")
    @Parameters({"seleniumHost", "seleniumPort", "browser"})
    public void typeHugeText(String seleniumHost, int seleniumPort, String browser) throws Throwable {
        try {
            final String hugeText = hugeText();
            ThreadSafeSeleniumSessionStorage.startSeleniumSession(seleniumHost, seleniumPort, browser, "http://amazon.com");
            ThreadSafeSeleniumSessionStorage.session().open("/");
            ThreadSafeSeleniumSessionStorage.session().type("twotabsearchtextbox", hugeText);
        } finally {
            ThreadSafeSeleniumSessionStorage.closeSeleniumSession();

        }
    }

    protected String hugeText() {
        final StringBuilder builder = new StringBuilder(10 * 1024);
        for (int i = 0; i < 1024; i++) {
            builder.append("1234567890");
        }
        return builder.toString();
    }

}
