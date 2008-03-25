package com.thoughtworks.selenium.grid.regressiontests;

import static com.thoughtworks.selenium.grid.tools.ThreadSafeSeleniumSessionStorage.*;
import static org.testng.Assert.assertTrue;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

/**
 * Check that Selenium Grid handles Unicode Characters properly
 * <p/>
 * ****************************************************************
 * ************************* WARNING ******************************
 * <p/>
 * This source file is UTF-8 encoded. Make sure your editor
 * treats it as such. Otherwise Cyrilic string will display
 * like garbage and this test will not work.
 * ****************************************************************
 */
public class CyrilicTests {

    public static final String TIMEOUT = "120000";

    @Test(groups = {"regression", "cyrilic"}, description = "Click on a Cyrilic link")
    @Parameters({"seleniumHost", "seleniumPort", "browser"})
    public void clickOnCyrilicLink(String seleniumHost, int seleniumPort, String browser) throws Exception {
        try {
            startSeleniumSession(seleniumHost, seleniumPort, browser, "https://cp.masterhost.ru");
            session().open("/");
            session().click("link=Регистрация");
            session().waitForPageToLoad("60000");
        } finally {
            closeSeleniumSession();
        }
    }

    @Test(groups = {"regression", "cyrilic"}, description = "Click on a Cyrilic link")
    @Parameters({"seleniumHost", "seleniumPort", "browser"})
    public void typeCyrilic(String seleniumHost, int seleniumPort, String browser) throws Exception {
        try {
            startSeleniumSession(seleniumHost, seleniumPort, browser, "http://google.ru");
            session().open("/");
            session().type("q", "Регистрация");
            session().click("btnG");
            session().waitForPageToLoad("60000");
        } finally {
            closeSeleniumSession();
        }
    }

    /**
     * This test not work with cyrilic inputs
     * <p/>
     * Little details: in this test on the page "contact info" some fields
     * last_name, first_name, middle_name can contailn only letters. But when
     * test runs in these fields has typed '??????????' (wrong symbols). In this
     * case application do not pass to the next step and test fails.
     *
     * @throws Exception
     */
    @Test(groups = {"regression", "cyrilic"}, description = "Click, select and type Cyrilic")
    @Parameters({"seleniumHost", "seleniumPort", "browser"})
    public void typeCyrilicInInputFields(String seleniumHost, int seleniumPort, String browser) throws Exception {
        try {
            startSeleniumSession(seleniumHost, seleniumPort, browser, "https://cp.masterhost.ru");
            /*
            * Registration
            */
            session().open("/");
            session().click("link=Регистрация");
            session().waitForPageToLoad("60000");

            /*
            * Agreement
            */
            assertTrue(session().isTextPresent("регистрация для клиентов"));
            session().click("nextBtn");
            session().waitForPageToLoad("60000");
            assertTrue(session().isTextPresent("регистрация для физического лица"));
            session().check("agreed");
            session().click("nextBtn");
            session().waitForPageToLoad("60000");

            /*
            * Contact info
            */
            assertTrue(session().isTextPresent("регистрация для физического лица"));
            session().type("last_name", "Федоров");
            session().type("first_name", "Федор");
            session().type("middle_name", "Федорович");
            session().type("eng_name", "Fedor F Fedorov");
            session().type("email", "dbykadorov@masterhost.ru");
            session().type("phone", "+7 495 1234567");
            session().type("fax", "+7 495 1234567");
            session().type("post_zip_code", "123456");
            session().select("post_country", "Албания");
            session().type("post_city", "Москва");
            session().type("post_street", "ул. Садовая, д.1, к. 2, кв. 5");
            session().click("nextBtn");
            session().waitForPageToLoad("60000");

            /*
            * Other personal data
            */
            assertTrue(session().isTextPresent("регистрация для физического лица"));
            session().select("birth_month", "label=марта");
        } finally {
            closeSeleniumSession();
        }

    }


}