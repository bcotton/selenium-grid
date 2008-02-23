package com.thoughtworks.selenium.grid.examples.java;

import static com.thoughtworks.selenium.grid.tools.ThreadSafeSeleniumSessionStorage.closeSeleniumSession;
import static com.thoughtworks.selenium.grid.tools.ThreadSafeSeleniumSessionStorage.startSeleniumSession;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;


/**
 */
public class MethodologyBooksTest extends BookTest {


    @Test(groups = {"demo", "firefox", "default"}, description = "Test Amazon Comment Quality Using the Firefox Web Browser (1).")
    @Parameters({"seleniumHost", "seleniumPort", "browser", "webSite"})
    public void extremeProgrammingTest(String seleniumHost, int seleniumPort, String browser, String webSite) throws Throwable {
        try {
            startSeleniumSession(seleniumHost, seleniumPort, browser, webSite);
            checkBook("Extreme Programming Explained: Embrace Change (2nd Edition)",
                    "Extreme Programming Explained: Embrace Change (2nd Edition) (The XP Series)",
                    "Extreme Programming",
                    "0321278658");
        } finally {
            closeSeleniumSession();
        }
    }

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

    @Test(groups = {"demo", "firefox", "default"}, description = "Test Amazon Comment Quality Using the Firefox Web Browser (1).")
    @Parameters({"seleniumHost", "seleniumPort", "browser", "webSite"})
    public void planningExtremeProgrammingTest(String seleniumHost, int seleniumPort, String browser, String webSite) throws Throwable {
        try {
            startSeleniumSession(seleniumHost, seleniumPort, browser, webSite);
            checkBook("Planning Extreme Programming",
                    "Planning Extreme Programming (The XP Series)",
                    "Extreme Programming",
                    "0201710919");
        } finally {
            closeSeleniumSession();
        }
    }

    @Test(groups = {"demo", "firefox", "default"}, description = "Test Amazon Comment Quality Using the Firefox Web Browser (1).")
    @Parameters({"seleniumHost", "seleniumPort", "browser", "webSite"})
    public void extremeProgrammingInstalled(String seleniumHost, int seleniumPort, String browser, String webSite) throws Throwable {
        try {
            startSeleniumSession(seleniumHost, seleniumPort, browser, webSite);
            checkBook("Extreme Programming Installed",
                    "Extreme Programming Installed (The XP Series)",
                    "Extreme Programming Installed",
                    "0201708426");
        } finally {
            closeSeleniumSession();
        }
    }

    @Test(groups = {"demo", "firefox", "default"}, description = "Test Amazon Comment Quality Using the Firefox Web Browser (1).")
    @Parameters({"seleniumHost", "seleniumPort", "browser", "webSite"})
    public void implementingLeanSoftwareDevelopment(String seleniumHost, int seleniumPort, String browser, String webSite) throws Throwable {
        try {
            startSeleniumSession(seleniumHost, seleniumPort, browser, webSite);
            checkBook("Implementing Lean Software Development: From Concept to Cash",
                    "Implementing Lean Software Development: From Concept to Cash (The Addison-Wesley Signature Series)",
                    "Lean Software Development",
                    "0321437381");
        } finally {
            closeSeleniumSession();
        }
    }


}