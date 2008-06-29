package com.thoughtworks.selenium.grid.demo;

import static com.thoughtworks.selenium.grid.tools.ThreadSafeSeleniumSessionStorage.session;
import static org.testng.AssertJUnit.assertEquals;
import static org.testng.AssertJUnit.assertTrue;

/**
 * Base class for Amazon Web Acceptance tests
 */
public abstract class AmazonCommentTestBase {

    protected void runAmazonScenario() throws Exception {
        session().open("/");
        session().type("twotabsearchtextbox", "refactoring");
        session().click("navGoButtonPanel");
        session().waitForPageToLoad("60000");
        assertTrue(session().getLocation().startsWith("http://www.amazon.com/s/ref="));
        session().click("link=Refactoring: Improving the Design of Existing Code (The Addison-Wesley Object Technology Series)");
        session().waitForPageToLoad("60000");
        assertEquals("1", session().getValue("name=quantity"));
        assertTrue(session().isTextPresent("excellent"));
        assertTrue(session().isTextPresent("Hidden Treasure"));
    }

}
