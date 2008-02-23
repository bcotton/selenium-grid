package com.thoughtworks.selenium.grid.examples.java;

import static com.thoughtworks.selenium.grid.tools.ThreadSafeSeleniumSessionStorage.closeSeleniumSession;
import static com.thoughtworks.selenium.grid.tools.ThreadSafeSeleniumSessionStorage.startSeleniumSession;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;


/**
 */
public class ProjectManagementBooksTest extends BookTest {


    @Test(groups = {"demo", "firefox", "default"}, description = "Test Amazon Comment Quality Using the Firefox Web Browser (1).")
    @Parameters({"seleniumHost", "seleniumPort", "browser", "webSite"})
    public void shipIt(String seleniumHost, int seleniumPort, String browser, String webSite) throws Throwable {
        try {
            startSeleniumSession(seleniumHost, seleniumPort, browser, webSite);
            checkBook("Ship it! A Practical Guide to Successful Software Projects",
                    "Ship it! A Practical Guide to Successful Software Projects",
                    "Ship It",
                    "0974514047");
        } finally {
            closeSeleniumSession();
        }
    }

    @Test(groups = {"demo", "firefox", "default"}, description = "Test Amazon Comment Quality Using the Firefox Web Browser (1).")
    @Parameters({"seleniumHost", "seleniumPort", "browser", "webSite"})
    public void releaseIt(String seleniumHost, int seleniumPort, String browser, String webSite) throws Throwable {
        try {
            startSeleniumSession(seleniumHost, seleniumPort, browser, webSite);
            checkBook("Release It!: Design and Deploy Production-Ready Software",
                    "Release It!: Design and Deploy Production-Ready Software (Pragmatic Programmers) (Pragmatic Programmers)",
                    "Release It",
                    "0978739213");
        } finally {
            closeSeleniumSession();
        }
    }

    @Test(groups = {"demo", "firefox", "default"}, description = "Test Amazon Comment Quality Using the Firefox Web Browser (1).")
    @Parameters({"seleniumHost", "seleniumPort", "browser", "webSite"})
    public void behindCloseDoors(String seleniumHost, int seleniumPort, String browser, String webSite) throws Throwable {
        try {
            startSeleniumSession(seleniumHost, seleniumPort, browser, webSite);
            checkBook("Behind Closed Doors: Secrets of Great Management",
                    "Behind Closed Doors: Secrets of Great Management (Pragmatic Programmers)",
                    "Ship It",
                    "0976694026");
        } finally {
            closeSeleniumSession();
        }
    }

    @Test(groups = {"demo", "firefox", "default"}, description = "Test Amazon Comment Quality Using the Firefox Web Browser (1).")
    @Parameters({"seleniumHost", "seleniumPort", "browser", "webSite"})
    public void agileRetrospectives(String seleniumHost, int seleniumPort, String browser, String webSite) throws Throwable {
        try {
            startSeleniumSession(seleniumHost, seleniumPort, browser, webSite);
            checkBook("Agile Retrospectives: Making Good Teams Great",
                    "Agile Retrospectives: Making Good Teams Great",
                    "Agile Retrospectives",
                    "0977616649");
        } finally {
            closeSeleniumSession();
        }
    }

    @Test(groups = {"demo", "firefox", "default"}, description = "Test Amazon Comment Quality Using the Firefox Web Browser (1).")
    @Parameters({"seleniumHost", "seleniumPort", "browser", "webSite"})
    public void theMythicalManMonth(String seleniumHost, int seleniumPort, String browser, String webSite) throws Throwable {
        try {
            startSeleniumSession(seleniumHost, seleniumPort, browser, webSite);
            checkBook("The Mythical Man-Month: Essays on Software Engineering",
                    "The Mythical Man-Month: Essays on Software Engineering, Anniversary Edition (2nd Edition)",
                    "Mythical Man-Month",
                    "0201835959");
        } finally {
            closeSeleniumSession();
        }
    }

}