package com.thoughtworks.selenium.grid;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Collections;

/**
 * Simpler interface for the parameter map available in a servlet container.
 * Parameters have a single value, and the map can be modified.
 *
 * @author Philippe Hanrigou
 */
public class HttpParameters {

    private Map<String, String[]> parameterMap;

    public HttpParameters(Map<String, String[]> parameterMap) {
        this.parameterMap = parameterMap;
    }

    public HttpParameters() {
        this.parameterMap = new HashMap<String, String[]>();
    }

    public String get(String name) {
        final String[] values = parameterMap.get(name);
        if (null == values || 0 == values.length) {
            return null;
        }
        return values[0];
    }

    public void put(String name, String value) {
        parameterMap = new HashMap<String, String[]>(parameterMap);
        parameterMap.put(name, new String[]{value});
    }

    public Set<String> names() {
        return parameterMap.keySet();
    }

    public String toString() {
        final StringBuilder builder;

        builder = new StringBuilder(150);
        
        for (String name : Collections.unmodifiableSet(parameterMap.keySet())) {
            builder.append(name);
            builder.append(" => \"");
            builder.append(get(name));
            builder.append("\", ");
        }
        if (!parameterMap.isEmpty()) {
          builder.deleteCharAt(builder.length() - 1);
          builder.deleteCharAt(builder.length() - 1);
        }

        return builder.toString();
    }

}
