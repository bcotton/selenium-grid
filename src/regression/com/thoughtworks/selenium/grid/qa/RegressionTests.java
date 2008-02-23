package com.thoughtworks.selenium.grid.qa;
import static com.thoughtworks.selenium.grid.tools.ThreadSafeSeleniumSessionStorage.closeSeleniumSession;
import static com.thoughtworks.selenium.grid.tools.ThreadSafeSeleniumSessionStorage.startSeleniumSession;
import static com.thoughtworks.selenium.grid.tools.ThreadSafeSeleniumSessionStorage.session;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;
import static org.testng.AssertJUnit.assertTrue;
import static org.testng.AssertJUnit.assertEquals;

/**
 * Check that Selenium Grid handles Unicode Characters properly
 */
public class RegressionTests {

    @Test(groups = {"demo", "firefox", "default"}, description = "Test handling of unicode Characters")
    @Parameters({"seleniumHost", "seleniumPort", "browser"})
    public void unicode(String seleniumHost, int seleniumPort, String browser) throws Throwable {
        try {
            startSeleniumSession(seleniumHost, seleniumPort, browser, "http://amazon.fr");
            session().open("/");
            session().waitForPageToLoad("30000");
            session().type("twotabsearchtextbox", "La Fièvre d'Urbicande");
            session().click("Go");
            session().waitForPageToLoad("30000");
            assertTrue(session().isTextPresent("Les Cités Obscures, tome 2 : La Fièvre d'Urbicande"));
            assertEquals("La Fièvre d'Urbicande", session().getValue("twotabsearchtextbox"));
        } finally {
            closeSeleniumSession();

        }
    }


    @Test(groups = {"demo", "firefox", "default"}, description = "Connection does not go away after some period of inactivity")
    @Parameters({"seleniumHost", "seleniumPort", "browser"})
    public void timeout(String seleniumHost, int seleniumPort, String browser) throws Throwable {
        try {
            startSeleniumSession(seleniumHost, seleniumPort, browser, "http://amazon.com");
            session().open("/");
            session().getLocation();
            Thread.sleep(120000);
            session().getLocation();
        } finally {
            closeSeleniumSession();

        }
    }



}