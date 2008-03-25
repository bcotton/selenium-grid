package com.thoughtworks.selenium.grid.regressiontests;

import static com.thoughtworks.selenium.grid.tools.ThreadSafeSeleniumSessionStorage.*;
import org.testng.AssertJUnit;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/**
 * Check that Selenium Grid handles Unicode Characters properly
 */
public class UnicodeTests {

    @Test(groups = {"regression"}, description = "Test handling of unicode Characters")
    @Parameters({"seleniumHost", "seleniumPort", "browser"})
    public void unicode(String seleniumHost, int seleniumPort, String browser) throws Throwable {
//        try {
//            startSeleniumSession(seleniumHost, seleniumPort, browser, "http://amazon.fr");
//            session().open("/");
//            session().waitForPageToLoad("30000");
//            session().type("twotabsearchtextbox", "La Fièvre d'Urbicande");
//            session().click("Go");
//            session().waitForPageToLoad("30000");
//            AssertJUnit.assertTrue(session().isTextPresent("Les Cités Obscures, tome 2 : La Fièvre d'Urbicande"));
//            AssertJUnit.assertEquals("La Fièvre d'Urbicande", session().getValue("twotabsearchtextbox"));
//        } finally {
//            closeSeleniumSession();
//        }
    }

}