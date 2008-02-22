package com.thoughtworks.selenium.grid.examples.java;

import static com.thoughtworks.selenium.grid.tools.ThreadSafeSeleniumSessionStorage.closeSeleniumSession;
import static com.thoughtworks.selenium.grid.tools.ThreadSafeSeleniumSessionStorage.startSeleniumSession;
import static com.thoughtworks.selenium.grid.tools.ThreadSafeSeleniumSessionStorage.session;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import static org.testng.AssertJUnit.assertTrue;
import static org.testng.AssertJUnit.assertEquals;


/**
 */
public class MethodologyBooksTest extends BookTest {


    @Test(groups = {"demo", "firefox", "default"}, description = "Test Amazon Comment Quality Using the Firefox Web Browser (1).")
    @Parameters({"seleniumHost", "seleniumPort", "browser", "webSite"})
    public void pragmaticProgrammerTest(String seleniumHost, int seleniumPort, String browser, String webSite) throws Throwable {
        try {
            startSeleniumSession(seleniumHost, seleniumPort, browser, webSite);
            checkBook("The Pragmatic Programmer: From Journeyman to Master",
                    "The Pragmatic Programmer: From Journeyman to Master",
                    "Pragmatic Programmer",
                    "020161622");
        } finally {
            closeSeleniumSession();
        }
    }

}