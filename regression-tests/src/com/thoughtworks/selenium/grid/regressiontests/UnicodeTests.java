package com.thoughtworks.selenium.grid.regressiontests;

import com.thoughtworks.selenium.grid.tools.ThreadSafeSeleniumSessionStorage;
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
        try {
            ThreadSafeSeleniumSessionStorage.startSeleniumSession(seleniumHost, seleniumPort, browser, "http://amazon.fr");
            ThreadSafeSeleniumSessionStorage.session().open("/");
            ThreadSafeSeleniumSessionStorage.session().waitForPageToLoad("30000");
            ThreadSafeSeleniumSessionStorage.session().type("twotabsearchtextbox", "La Fièvre d'Urbicande");
            ThreadSafeSeleniumSessionStorage.session().click("Go");
            ThreadSafeSeleniumSessionStorage.session().waitForPageToLoad("30000");
            AssertJUnit.assertTrue(ThreadSafeSeleniumSessionStorage.session().isTextPresent("Les Cités Obscures, tome 2 : La Fièvre d'Urbicande"));
            AssertJUnit.assertEquals("La Fièvre d'Urbicande", ThreadSafeSeleniumSessionStorage.session().getValue("twotabsearchtextbox"));
        } finally {
            ThreadSafeSeleniumSessionStorage.closeSeleniumSession();

        }
    }

}