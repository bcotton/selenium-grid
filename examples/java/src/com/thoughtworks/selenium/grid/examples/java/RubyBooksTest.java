package com.thoughtworks.selenium.grid.examples.java;

import static com.thoughtworks.selenium.grid.tools.ThreadSafeSeleniumSessionStorage.closeSeleniumSession;
import static com.thoughtworks.selenium.grid.tools.ThreadSafeSeleniumSessionStorage.startSeleniumSession;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;


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

    @Test(groups = {"demo", "firefox", "default"}, description = "Test Amazon Comment Quality Using the Firefox Web Browser (1).")
    @Parameters({"seleniumHost", "seleniumPort", "browser", "webSite"})
    public void agileWebDevelomentWithRailsTest(String seleniumHost, int seleniumPort, String browser, String webSite) throws Throwable {
        try {
            startSeleniumSession(seleniumHost, seleniumPort, browser, webSite);
            checkBook("Agile Web Development with Rails",
                    "Agile Web Development with Rails, 2nd Edition",
                    "Ruby",
                    "0977616630");
        } finally {
            closeSeleniumSession();
        }
    }

    @Test(groups = {"demo", "firefox", "default"}, description = "Test Amazon Comment Quality Using the Firefox Web Browser (1).")
    @Parameters({"seleniumHost", "seleniumPort", "browser", "webSite"})
    public void advancedRailsRecipesTest(String seleniumHost, int seleniumPort, String browser, String webSite) throws Throwable {
        try {
            startSeleniumSession(seleniumHost, seleniumPort, browser, webSite);
            checkBook("Advanced Rails Recipes: 72 New Ways to Build Stunning Rails Apps",
                    "Advanced Rails Recipes: 72 New Ways to Build Stunning Rails Apps",
                    "Rails Recipes",
                    "0978739221");
        } finally {
            closeSeleniumSession();
        }
    }

    @Test(groups = {"demo", "firefox", "default"}, description = "Test Amazon Comment Quality Using the Firefox Web Browser (1).")
    @Parameters({"seleniumHost", "seleniumPort", "browser", "webSite"})
    public void railsWayTest(String seleniumHost, int seleniumPort, String browser, String webSite) throws Throwable {
        try {
            startSeleniumSession(seleniumHost, seleniumPort, browser, webSite);
            checkBook("The Rails Way",
                    "The Rails Way (Addison-Wesley Professional Ruby Series)",
                    "Rails",
                    "0321445619");
        } finally {
            closeSeleniumSession();
        }
    }

}