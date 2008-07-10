package com.thoughtworks.selenium.grid;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;

/**
 * Helper Class for Custom Assertions.
 */
public class AssertionHelper {

    public static void assertNotEquals(Object anObject, Object theOther) {
        if (null == anObject) {
            assertFalse(null == theOther);
        } else {
          assertFalse(anObject.equals(theOther));
        }
    }

    public static void assertSameHashCode(Object anObject, Object another) {
        assertEquals(anObject.hashCode(), another.hashCode());
    }

    public static void assertDistinctHashCodes(Object anObject, Object another) {
        assertNotEquals(anObject.hashCode(), another.hashCode());
    }

}
