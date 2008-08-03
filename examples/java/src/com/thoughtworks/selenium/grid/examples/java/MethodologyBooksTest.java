package com.thoughtworks.selenium.grid.examples.java;

import org.testng.annotations.Test;


/**
 */
public class MethodologyBooksTest extends BookTest {


    @Test(groups = {"example", "firefox", "default"}, description = "Test Amazon Comment Quality Using the Firefox Web Browser (1).")
    public void extremeProgrammingTest() throws Throwable {
        checkBook("Extreme Programming Explained: Embrace Change (2nd Edition)",
                "Extreme Programming Explained: Embrace Change (2nd Edition) (The XP Series)",
                "Extreme Programming",
                "0321278658");
    }

    @Test(groups = {"example", "firefox", "default"}, description = "Test Amazon Comment Quality Using the Firefox Web Browser (1).")
    public void pragmaticProgrammerTest() throws Throwable {
        checkBook("The Pragmatic Programmer: From Journeyman to Master",
                "The Pragmatic Programmer: From Journeyman to Master",
                "Pragmatic Programmer",
                "020161622");
    }

    @Test(groups = {"example", "firefox", "default"}, description = "Test Amazon Comment Quality Using the Firefox Web Browser (1).")
    public void planningExtremeProgrammingTest() throws Throwable {
        checkBook("Planning Extreme Programming",
                "Planning Extreme Programming (The XP Series)",
                "Extreme Programming",
                "0201710919");
    }

    @Test(groups = {"example", "firefox", "default"}, description = "Test Amazon Comment Quality Using the Firefox Web Browser (1).")
    public void extremeProgrammingInstalled() throws Throwable {
        checkBook("Extreme Programming Installed",
                "Extreme Programming Installed (The XP Series)",
                "Extreme Programming Installed",
                "0201708426");
    }

    @Test(groups = {"example", "firefox", "default"}, description = "Test Amazon Comment Quality Using the Firefox Web Browser (1).")
    public void implementingLeanSoftwareDevelopment() throws Throwable {
        checkBook("Implementing Lean Software Development: From Concept to Cash",
                "Implementing Lean Software Development: From Concept to Cash (The Addison-Wesley Signature Series)",
                "Lean Software Development",
                "0321437381");
    }


}