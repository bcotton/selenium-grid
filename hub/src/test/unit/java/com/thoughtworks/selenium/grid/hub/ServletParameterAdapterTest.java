package com.thoughtworks.selenium.grid.hub;

import com.thoughtworks.selenium.grid.HttpParameters;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class ServletParameterAdapterTest {

    @Test
    public void parametersAreCopiedWhenAMapIsProvidedToConstructor() {
        final Map<String, String[]> servletParameterMap;

        servletParameterMap = new HashMap<String, String[]>();
        servletParameterMap.put("a name", new String[] { "a value" });
        assertEquals("a value", new HttpParameters(servletParameterMap).get("a name"));
    }

    @Test
    public void getReturnsOnlyTheFirstValueOfAParameter() {
        final Map<String, String[]> servletParameterMap;

        servletParameterMap = new HashMap<String, String[]>();
        servletParameterMap.put("a name", new String[] { "first value", "second value" });
        assertEquals("first value", new HttpParameters(servletParameterMap).get("a name"));
    }

    @Test
    public void getReturnsNullWhenParameterValueIsAnEmptyArray() {
        final Map<String, String[]> servletParameterMap;

        servletParameterMap = new HashMap<String, String[]>();
        servletParameterMap.put("a name", new String[] { });
        assertEquals(null, new HttpParameters(servletParameterMap).get("a name"));
    }

    @Test
    public void putCanChangeAValueEventIfMapProvidedToConstructorIsFrozen() {
        final Map<String, String[]> servletParameterMap;
        final HttpParameters parameters;

        servletParameterMap = new HashMap<String, String[]>();
        servletParameterMap.put("a name", new String[] { "original value" });
        parameters = new HttpParameters(Collections.unmodifiableMap(servletParameterMap));
        parameters.put("a name", "new value");
        assertEquals("new value", parameters.get("a name"));
    }


    @Test
    public void namesIsEmptyWhenThereIsNoParameter() {
        assertTrue(new HttpParameters().names().isEmpty());
    }

    @Test
    public void namesContainsAllTheParameterNames() {
        final HttpParameters parameters = new HttpParameters();
        parameters.put("selenium", "grid");
        parameters.put("open", "qa");
        assertEquals(2, parameters.names().size());
        assertTrue(parameters.names().contains("selenium"));
        assertTrue(parameters.names().contains("open"));
    }

}
