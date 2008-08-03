package com.thoughtworks.selenium.grid.examples.java;

import org.testng.annotations.Test;


/**
 */
public class RubyBooksTest extends BookTest {

    @Test(groups = {"example", "firefox", "default"}, description = "Test Amazon Comment Quality Using the Firefox Web Browser (1).")
    public void programmingRubyBookTest() throws Throwable {
        checkBook("Programming Ruby: The Pragmatic Programmers' Guide, Second Edition",
                "Programming Ruby: The Pragmatic Programmers' Guide, Second Edition",
                "Ruby",
                "0974514055");
    }

    @Test(groups = {"example", "firefox", "default"}, description = "Test Amazon Comment Quality Using the Firefox Web Browser (1).")
    public void rubyWayBookTest() throws Throwable {
        checkBook("The Ruby Way, Second Edition: Solutions and Techniques in Ruby Programming",
                "The Ruby Way, Second Edition: Solutions and Techniques in Ruby Programming (2nd Edition) (Addison-Wesley Professional Ruby Series)",
                "Ruby",
                "0672328844");
    }

    @Test(groups = {"example", "firefox", "default"}, description = "Test Amazon Comment Quality Using the Firefox Web Browser (1).")
    public void agileWebDevelomentWithRailsTest() throws Throwable {
        checkBook("Agile Web Development with Rails",
                "Agile Web Development with Rails, 2nd Edition",
                "Rails",
                "0977616630");
    }

    @Test(groups = {"example", "firefox", "default"}, description = "Test Amazon Comment Quality Using the Firefox Web Browser (1).")
    public void advancedRailsRecipesTest() throws Throwable {
        checkBook("Advanced Rails Recipes",
                "Advanced Rails Recipes",
                "Rails Recipes",
                "0978739221");
    }

    @Test(groups = {"example", "firefox", "default"}, description = "Test Amazon Comment Quality Using the Firefox Web Browser (1).")
    public void railsWayTest() throws Throwable {
        checkBook("The Rails Way",
                "The Rails Way (Addison-Wesley Professional Ruby Series)",
                "Rails",
                "0321445619");
    }

}