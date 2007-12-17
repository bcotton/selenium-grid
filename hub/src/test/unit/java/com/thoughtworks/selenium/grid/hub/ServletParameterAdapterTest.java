package com.thoughtworks.selenium.grid.hub;

import static junit.framework.Assert.assertEquals;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class ServletParameterAdapterTest {

    @Test
    public void parametersAreCopiedWhenAMapIsProvidedToConstructor() {
        // Moved to JRuby but not working yet -- Clint
        final Map<String, String[]> servletParameterMap;

        servletParameterMap = new HashMap<String, String[]>();
        servletParameterMap.put("a name", new String[] { "a value" });
        assertEquals("a value", new ServletParametersAdapter(servletParameterMap).get("a name"));
    }

    @Test
    public void getReturnsOnlyTheFirstValueOfAParameter() {
        // Moved to JRuby but not working yet -- Clint
        final Map<String, String[]> servletParameterMap;

        servletParameterMap = new HashMap<String, String[]>();
        servletParameterMap.put("a name", new String[] { "first value", "second value" });
        assertEquals("first value", new ServletParametersAdapter(servletParameterMap).get("a name"));
    }

    @Test
    public void getReturnsNullWhenParameterValueIsAnEmptyArray() {
        // Moved to JRuby but not working yet -- Clint
        final Map<String, String[]> servletParameterMap;

        servletParameterMap = new HashMap<String, String[]>();
        servletParameterMap.put("a name", new String[] { });
        assertEquals(null, new ServletParametersAdapter(servletParameterMap).get("a name"));
    }

    @Test
    public void putCanChangeAValueEventIfMapProvidedToConstructorIsFrozen() {
        // Moved to JRuby but not working yet -- Clint
        final Map<String, String[]> servletParameterMap;
        final ServletParametersAdapter parameters;

        servletParameterMap = new HashMap<String, String[]>();
        servletParameterMap.put("a name", new String[] { "original value" });
        parameters = new ServletParametersAdapter(Collections.unmodifiableMap(servletParameterMap));
        parameters.put("a name", "new value");
        assertEquals("new value", parameters.get("a name"));
    }

}
