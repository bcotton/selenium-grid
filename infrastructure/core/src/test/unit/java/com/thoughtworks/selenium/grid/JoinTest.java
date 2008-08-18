package com.thoughtworks.selenium.grid;

import org.junit.Test;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;

public class JoinTest {

    @Test
    public void toStringOnAnEmptyCollectionReturnsAnEmptyString() {
        assertEquals("", new Join(new ArrayList(), "does not matter").toString());
    }

    @Test
    public void toStringOnACollectionWithASingleElementReturnThisElementConvertedToAString() {
        final List collection;

        collection = new LinkedList();
        collection.add(new Object() {
            public String toString() {
                return "string conversion of single element";
            }
        });
        assertEquals("string conversion of single element",
                     new Join(collection, "does not matter").toString());
    }

    @Test
    public void toStringOnACollectionWithAMultipleElementReturnTheseElementsConvertedToAStringSeparatedByTheSepatorGivenInTheConstructor() {
        final List collection;

        collection = new LinkedList();
        collection.add(new Object() {
            public String toString() {
                return "string conversion of first element";
            }
        });
        collection.add(new Object() {
            public String toString() {
                return "string conversion of second element";
            }
        });
        assertEquals("string conversion of first element|the separator|string conversion of second element",
                     new Join(collection, "|the separator|").toString());
    }

}
