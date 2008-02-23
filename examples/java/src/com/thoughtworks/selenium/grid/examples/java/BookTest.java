package com.thoughtworks.selenium.grid.examples.java;

import static com.thoughtworks.selenium.grid.tools.ThreadSafeSeleniumSessionStorage.session;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;


/**
 */
public class BookTest {

//    @BeforeTest
//    @Parameters({"seleniumHost", "seleniumPort", "browser", "webSite"})
//    public void startSession(String seleniumHost, int seleniumPort, String browser, String webSite) {
//        startSeleniumSession(seleniumHost, seleniumPort, browser, webSite);
//    }
//
//    @AfterTest
//    @Parameters({"seleniumHost", "seleniumPort", "browser", "webSite"})
//    public void closeSession() throws Exception {
//        closeSeleniumSession();
//    }


    protected void checkBook(String title, String thumbnail, String keywords, String isbn) {
        session().open("/");
        session().select("url", "Books");
        session().type("twotabsearchtextbox", keywords);
        session().click("navGoButtonPanel");
        session().waitForPageToLoad("60000");
        assertTrue(session().isTextPresent(title));
        session().click("//img[@alt=\"" + thumbnail + "\"]");
        session().waitForPageToLoad("60000");
        assertEquals("1", session().getValue("name=quantity"));
        assertTrue(session().isTextPresent("ISBN-10: " + isbn));
        session().click("link=Explore similar items");
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