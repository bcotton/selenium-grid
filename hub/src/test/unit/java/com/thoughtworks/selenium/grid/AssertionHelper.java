package com.thoughtworks.selenium.grid;

import junit.framework.Assert;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.assertEquals;

/**
 * Helper Class for Custom Assertions.
 */
public class AssertionHelper {

    public static void assertNotEquals(Object anObject, Object another) {
        assertFalse(anObject.equals(another));
    }

    public static void assertSameHashCode(Object anObject, Object another) {
        assertEquals(anObject.hashCode(), another.hashCode());
    }

    public static void assertDifferentHashCode(Object anObject, Object another) {
        assertNotEquals(anObject.hashCode(), another.hashCode());
    }

}
