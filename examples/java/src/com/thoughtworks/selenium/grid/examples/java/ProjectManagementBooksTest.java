package com.thoughtworks.selenium.grid.examples.java;

import org.testng.annotations.Test;


/**
 */
public class ProjectManagementBooksTest extends BookTest {


    @Test(groups = {"example", "firefox", "default"}, description = "Test Amazon Comment Quality Using the Firefox Web Browser (1).")
    public void shipIt() throws Throwable {
        checkBook("Ship it! A Practical Guide to Successful Software Projects",
                "Ship it! A Practical Guide to Successful Software Projects",
                "Ship It",
                "0974514047");
    }

    @Test(groups = {"example", "firefox", "default"}, description = "Test Amazon Comment Quality Using the Firefox Web Browser (1).")
    public void releaseIt() throws Throwable {
        checkBook("Release It!: Design and Deploy Production-Ready Software",
                "Release It!: Design and Deploy Production-Ready Software (Pragmatic Programmers) (Pragmatic Programmers)",
                "Release It",
                "0978739213");
    }

    @Test(groups = {"example", "firefox", "default"}, description = "Test Amazon Comment Quality Using the Firefox Web Browser (1).")
    public void behindCloseDoors() throws Throwable {
        checkBook("Behind Closed Doors: Secrets of Great Management",
                "Behind Closed Doors: Secrets of Great Management (Pragmatic Programmers)",
                "Ship It",
                "0976694026");
    }

    @Test(groups = {"example", "firefox", "default"}, description = "Test Amazon Comment Quality Using the Firefox Web Browser (1).")
    public void agileRetrospectives() throws Throwable {
        checkBook("Agile Retrospectives: Making Good Teams Great",
                "Agile Retrospectives: Making Good Teams Great",
                "Agile Retrospectives",
                "0977616649");
    }

    @Test(groups = {"example", "firefox", "default"}, description = "Test Amazon Comment Quality Using the Firefox Web Browser (1).")
    public void theMythicalManMonth() throws Throwable {
        checkBook("The Mythical Man-Month: Essays on Software Engineering",
                "The Mythical Man-Month: Essays on Software Engineering, Anniversary Edition (2nd Edition)",
                "Mythical Man-Month",
                "0201835959");
    }

}