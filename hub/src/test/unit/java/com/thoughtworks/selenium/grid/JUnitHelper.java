package com.thoughtworks.selenium.grid;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;

/**
 * Utility methods for JUnit tests.
 */
public class JUnitHelper {

    public static void assertNotEquals(Object expected, Object actual) {
        assertFalse(actual.equals(expected));
    }

    public static void assertSameHashCodes(Object expected, Object actual) {
        assertEquals(expected.hashCode(), actual.hashCode());
    }

    public static void assertDifferentHashCodes(Object expected, Object actual) {
        assertNotEquals(expected.hashCode(), actual.hashCode());
    }

}
