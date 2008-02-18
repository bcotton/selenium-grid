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
public class TechnicalBookTest {


    @Test(groups = {"demo", "firefox", "default"}, description = "Test Amazon Comment Quality Using the Firefox Web Browser (1).")
    @Parameters({"seleniumHost", "seleniumPort", "browser", "webSite"})
    public void refactoringBookTest(String seleniumHost, int seleniumPort, String browser, String webSite) throws Throwable {
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
    public void gofBookTest(String seleniumHost, int seleniumPort, String browser, String webSite) throws Throwable {
        startSeleniumSession(seleniumHost, seleniumPort, browser, webSite);
        session().open("/");
        session().type("twotabsearchtextbox", "design patterns");
        session().click("navGoButtonPanel");
        session().waitForPageToLoad("60000");
        assertTrue(session().getLocation().startsWith("http://www.amazon.com/s/ref="));
        closeSeleniumSession();
    }

    @Test(groups = {"demo", "firefox", "default"}, description = "Test Amazon Comment Quality Using the Firefox Web Browser (2).")
    @Parameters({"seleniumHost", "seleniumPort", "browser", "webSite"})
    public void operatingSystems(String seleniumHost, int seleniumPort, String browser, String webSite) throws Throwable {
        startSeleniumSession(seleniumHost, seleniumPort, browser, webSite);
        session().open("/");
        session().type("twotabsearchtextbox", "operating system");
        session().click("navGoButtonPanel");
        session().waitForPageToLoad("60000");
        assertTrue(session().getLocation().startsWith("http://www.amazon.com/s/ref="));
        closeSeleniumSession();
    }

    @Test(groups = {"demo", "firefox", "default"}, description = "Test Amazon Comment Quality Using the Firefox Web Browser (2).")
    @Parameters({"seleniumHost", "seleniumPort", "browser", "webSite"})
    public void pragProgtest(String seleniumHost, int seleniumPort, String browser, String webSite) throws Throwable {
        startSeleniumSession(seleniumHost, seleniumPort, browser, webSite);
        session().open("/");
        session().type("twotabsearchtextbox", "pragmatic programmer");
        session().click("navGoButtonPanel");
        session().waitForPageToLoad("60000");
        assertTrue(session().getLocation().startsWith("http://www.amazon.com/s/ref="));
        closeSeleniumSession();
    }

    @Test(groups = {"demo", "firefox", "default"}, description = "Test Amazon Comment Quality Using the Firefox Web Browser (2).")
    @Parameters({"seleniumHost", "seleniumPort", "browser", "webSite"})
    public void extremeProgrammingTest(String seleniumHost, int seleniumPort, String browser, String webSite) throws Throwable {
        startSeleniumSession(seleniumHost, seleniumPort, browser, webSite);
        session().open("/");
        session().type("twotabsearchtextbox", "pragmatic programmer");
        session().click("navGoButtonPanel");
        session().waitForPageToLoad("60000");
        assertTrue(session().getLocation().startsWith("http://www.amazon.com/s/ref="));
        closeSeleniumSession();
    }

    @Test(groups = {"demo", "firefox", "default"}, description = "Test Amazon Comment Quality Using the Firefox Web Browser (2).")
    @Parameters({"seleniumHost", "seleniumPort", "browser", "webSite"})
    public void analysisPatternsTest(String seleniumHost, int seleniumPort, String browser, String webSite) throws Throwable {
        startSeleniumSession(seleniumHost, seleniumPort, browser, webSite);
        session().open("/");
        session().type("twotabsearchtextbox", "Ananlysis Patterns");
        session().click("navGoButtonPanel");
        session().waitForPageToLoad("60000");
        assertTrue(session().getLocation().startsWith("http://www.amazon.com/s/ref="));
        closeSeleniumSession();
    }


}