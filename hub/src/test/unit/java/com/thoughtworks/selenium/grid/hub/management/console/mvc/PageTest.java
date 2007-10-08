package com.thoughtworks.selenium.grid.hub.management.console.mvc;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import org.junit.Test;

public class PageTest {

    @Test
    public void templateIsTheOneProvidedInTheConstructor() {
        assertEquals("a template", new Page("a template").template());
    }

    @Test
    public void assignsAreEmptyAfterCreation() {
        assertTrue(new Page(null).assigns().isEmpty());
    }

    @Test
    public void setAddsAKeyValuePairToAssigns() {
        final Page page = new Page(null);
        page.set("a name", "a value");
        assertEquals("a value", page.assigns().get("a name"));
    }

    @Test
    public void setAcceptsArbitraryObjectsAsValue() {
        final Page page = new Page(null);
        final Object anonymousObject = new Object() {};
        page.set("a name", anonymousObject);
        assertEquals(anonymousObject, page.assigns().get("a name"));
    }

}
