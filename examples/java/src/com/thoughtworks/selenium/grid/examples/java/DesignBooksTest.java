package com.thoughtworks.selenium.grid.examples.java;

import org.testng.annotations.Test;


/**
 */
public class DesignBooksTest extends BookTest {

    @Test(groups = {"example", "firefox", "default"}, description = "Test Amazon Comment Quality Using the Firefox Web Browser (1).")
    public void refactoring() throws Throwable {
        checkBook("Refactoring: Improving the Design of Existing Code",
                "Refactoring: Improving the Design of Existing Code (The Addison-Wesley Object Technology Series)",
                "Refactoring",
                "0201485672");
    }


    @Test(groups = {"example", "firefox", "default"}, description = "Test Amazon Comment Quality Using the Firefox Web Browser (1).")
    public void designPatterns() throws Throwable {
        checkBook("Design Patterns: Elements of Reusable Object-Oriented Software",
                "Design Patterns: Elements of Reusable Object-Oriented Software (Addison-Wesley Professional Computing Series)",
                "Design Patterns",
                "0201633612");
    }

    @Test(groups = {"example", "firefox", "default"}, description = "Test Amazon Comment Quality Using the Firefox Web Browser (1).")
    public void testDrivenDevelopment() throws Throwable {
        checkBook("Test Driven Development: By Example",
                "Test Driven Development: By Example (The Addison-Wesley Signature Series)",
                "Test Driven Development",
                "0321146530");
    }

    @Test(groups = {"example", "firefox", "default"}, description = "Test Amazon Comment Quality Using the Firefox Web Browser (1).")
    public void enterpriseDesignPatterns() throws Throwable {
        checkBook("Patterns of Enterprise Application Architecture",
                "Patterns of Enterprise Application Architecture (The Addison-Wesley Signature Series)",
                "enterprise design patterns",
                "0321127420");
    }

    @Test(groups = {"example", "firefox", "default"}, description = "Test Amazon Comment Quality Using the Firefox Web Browser (1).")
    public void domainDrivenDesign() throws Throwable {
        checkBook("Domain-Driven Design: Tackling Complexity in the Heart of Software",
                "Domain-Driven Design: Tackling Complexity in the Heart of Software",
                "domain driven design",
                "0321125215");
    }

}