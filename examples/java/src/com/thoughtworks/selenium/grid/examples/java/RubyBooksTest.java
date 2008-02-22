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
public class RubyBooksTest extends BookTest {

    @Test(groups = {"demo", "firefox", "default"}, description = "Test Amazon Comment Quality Using the Firefox Web Browser (1).")
    @Parameters({"seleniumHost", "seleniumPort", "browser", "webSite"})
    public void programmingRubyBookTest(String seleniumHost, int seleniumPort, String browser, String webSite) throws Throwable {
        try {
            startSeleniumSession(seleniumHost, seleniumPort, browser, webSite);
            checkBook("Programming Ruby: The Pragmatic Programmers' Guide, Second Edition",
                      "Programming Ruby: The Pragmatic Programmers' Guide, Second Edition",
                      "Ruby",
                      "0974514055");
        } finally {
            closeSeleniumSession();

        }
    }    

    @Test(groups = {"demo", "firefox", "default"}, description = "Test Amazon Comment Quality Using the Firefox Web Browser (1).")
    @Parameters({"seleniumHost", "seleniumPort", "browser", "webSite"})
    public void rubyWayBookTest(String seleniumHost, int seleniumPort, String browser, String webSite) throws Throwable {
        try {
            startSeleniumSession(seleniumHost, seleniumPort, browser, webSite);
            checkBook("The Ruby Way, Second Edition: Solutions and Techniques in Ruby Programming",
                      "The Ruby Way, Second Edition: Solutions and Techniques in Ruby Programming (2nd Edition) (Addison-Wesley Professional Ruby Series)",
                      "Ruby",
                      "0672328844");
        } finally {
            closeSeleniumSession();

        }
    }

}