package com.thoughtworks.selenium.grid.agent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;
import org.junit.Test;

import java.io.File;

public class ClasspathTest {

    @Test
    public void entriesIsEmptyByDefault() {
        assertArrayEquals(new String[0], new Classpath().entries());
    }

    @Test
    public void anEntryCanBeRetrievedOnceAdded() {
        final Classpath classpath;

        classpath = new Classpath();
        classpath.add("/an/entry");
        assertArrayEquals(new String[] { "/an/entry" }, classpath.entries());
    }

    @Test
    public void entriesAreOrderedAsThereWereAdded() {
        final Classpath classpath;

        classpath = new Classpath();
        classpath.add("/first/entry");
        classpath.add("/second/entry");
        assertArrayEquals(new String[] { "/first/entry", "/second/entry" }, classpath.entries());
    }

    @Test
    public void toStringIsAnEmptyStringWhenNoEntriesWereAdded() {
        assertEquals("", new Classpath().toString());
    }

    @Test
    public void toStringReturnThePathEntryWhenThereIsASingleOne() {
        final Classpath classpath;

        classpath = new Classpath();
        classpath.add("/single/entry");
        assertEquals("/single/entry", classpath.toString());
    }

    @Test
    public void toStringReturnsClasspathEntriesSeperatedByTheClasspathSeperator() {
        final Classpath classpath;

        classpath = new Classpath();
        classpath.add("/first/entry");
        classpath.add("/second/entry");
        assertEquals("/first/entry" + File.pathSeparator + "/second/entry", classpath.toString());
    }

}
