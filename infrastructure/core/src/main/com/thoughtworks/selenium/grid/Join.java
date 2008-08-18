package com.thoughtworks.selenium.grid;

import java.util.Collection;

/**
 * Returns a string created by converting each element of the array to
 * a string, separated by speparator.
 */
public class Join extends Object {

    private final Collection collection;
    private final String separator;

    public Join(Collection collection, String separator) {
        this.collection = collection;
        this.separator = separator;
    }

    public String toString() {
        final StringBuffer joinedString;

        if (collection.isEmpty()) {
            return "";
        }
        
        joinedString = new StringBuffer();
        for (Object entry : collection) {
            joinedString.append(entry.toString());
            joinedString.append(separator);
        }

        return joinedString.substring(0, joinedString.lastIndexOf(separator));
    }
}
