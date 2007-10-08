package com.thoughtworks.selenium.grid.hub;

import static junit.framework.Assert.assertEquals;
import org.junit.Test;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class ServletParameterAdapterTest {

    @Test
    public void queryStringIsEmptyWhenRequestHasNoParameter() {
        assertEquals("", new ServletParametersAdapter().queryString());
    }

    @Test
    public void buildQueryStringContructsTheQueryStringFromRequestParameters() {
        final ServletParametersAdapter parameters;

        parameters = new ServletParametersAdapter();
        parameters.put("cmd", "testComplete");
        assertEquals("cmd=testComplete", parameters.queryString());
    }

    @Test
    public void buildQueryStringIsEscaped() {
        final ServletParametersAdapter parameters;

        parameters = new ServletParametersAdapter();
        parameters.put("param", "A value with space / slash");
        assertEquals("param=A+value+with+space+%2F+slash", parameters.queryString());
    }

    @Test
    public void buildQueryStringSeperatesParametersWithAmpersands() {
        final ServletParametersAdapter parameters;

        parameters = new ServletParametersAdapter();
        parameters.put("cmd", "testComplete");
        parameters.put("sessionId", "1234");
        assertEquals("cmd=testComplete&sessionId=1234", parameters.queryString());
    }

    @Test
    public void parametersAreCopiedWhenAMapIsProvidedToConstructor() {
        final Map<String, String[]> servletParameterMap;

        servletParameterMap = new HashMap<String, String[]>();
        servletParameterMap.put("a name", new String[] { "a value" });
        assertEquals("a value", new ServletParametersAdapter(servletParameterMap).get("a name"));
    }

    @Test
    public void getReturnsOnlyTheFirstValueOfAParameter() {
        final Map<String, String[]> servletParameterMap;

        servletParameterMap = new HashMap<String, String[]>();
        servletParameterMap.put("a name", new String[] { "first value", "second value" });
        assertEquals("first value", new ServletParametersAdapter(servletParameterMap).get("a name"));
    }

    @Test
    public void getReturnsNullWhenParameterDoesNotExist() {
        assertEquals(null, new ServletParametersAdapter().get("unknown parameter"));
    }

    @Test
    public void getReturnsNullWhenParameterValueIsNull() {
        final ServletParametersAdapter parameters = new ServletParametersAdapter();
        parameters.put("a name", null);
        assertEquals(null, parameters.get("a name"));
    }

    @Test
    public void getReturnsNullWhenParameterValueIsAnEmptyArray() {
        final Map<String, String[]> servletParameterMap;

        servletParameterMap = new HashMap<String, String[]>();
        servletParameterMap.put("a name", new String[] { });
        assertEquals(null, new ServletParametersAdapter(servletParameterMap).get("a name"));
    }

    @Test
    public void putCanChangeAValueEventIfMapProvidedToConstructorIsFrozen() {
        final Map<String, String[]> servletParameterMap;
        final ServletParametersAdapter parameters;

        servletParameterMap = new HashMap<String, String[]>();
        servletParameterMap.put("a name", new String[] { "original value" });
        parameters = new ServletParametersAdapter(Collections.unmodifiableMap(servletParameterMap));
        parameters.put("a name", "new value");
        assertEquals("new value", parameters.get("a name"));
    }

}
