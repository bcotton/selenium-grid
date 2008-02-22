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
        try {
            startSeleniumSession(seleniumHost, seleniumPort, browser, webSite);
            checkBook("Refactoring: Improving the Design of Existing Code",
                    "Refactoring: Improving the Design of Existing Code (The Addison-Wesley Object Technology Series)",
                    "Refactoring",
                    "0201485672");
        } finally {
            closeSeleniumSession();

        }
    }


    @Test(groups = {"demo", "firefox", "default"}, description = "Test Amazon Comment Quality Using the Firefox Web Browser (1).")
    @Parameters({"seleniumHost", "seleniumPort", "browser", "webSite"})
    public void designPatternTest(String seleniumHost, int seleniumPort, String browser, String webSite) throws Throwable {
        try {
            startSeleniumSession(seleniumHost, seleniumPort, browser, webSite);
            checkBook("Design Patterns: Elements of Reusable Object-Oriented Software",
                    "Design Patterns: Elements of Reusable Object-Oriented Software (Addison-Wesley Professional Computing Series)",
                    "Design Patterns",
                    "0201633612");
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


    protected void checkBook(String title, String thumbnail, String keywords, String isbn) {
        session().open("/");
        session().type("twotabsearchtextbox", keywords);
        session().click("navGoButtonPanel");
        session().waitForPageToLoad("60000");
        assertTrue(session().isTextPresent(title));
        session().click("//img[@alt='" + thumbnail + "']");
        session().waitForPageToLoad("60000");
        assertEquals("1", session().getValue("name=quantity"));
//        assertTrue(session().isTextPresent("excellent"));
        assertTrue(session().isTextPresent("ISBN-10: " + isbn));
        session().click("link=See all Editorial Reviews");
        session().waitForPageToLoad("60000");
        session().goBack();
        session().waitForPageToLoad("60000");
        assertEquals("1", session().getValue("quantity"));
        session().select("quantity", "label=5");
        session().click("submit.add-to-cart");
        session().waitForPageToLoad("60000");
        assertTrue(session().isTextPresent("Added to your\nShopping Cart:"));
        assertTrue(session().isTextPresent(title));
        assertTrue(session().isTextPresent("quantity: 5"));
    }
}