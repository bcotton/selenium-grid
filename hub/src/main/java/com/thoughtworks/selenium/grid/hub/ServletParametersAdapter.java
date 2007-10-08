package com.thoughtworks.selenium.grid.hub;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * Simpler interface for the parameter map available in a servlet container.
 * Parameters have a single value, and the map can be modified.
 */
public class ServletParametersAdapter {

    private Map<String, String[]> servletParameterMap;

    public ServletParametersAdapter(Map<String, String[]> servletParameterMap) {
        this.servletParameterMap = servletParameterMap;
    }

    public ServletParametersAdapter() {
        this.servletParameterMap = new HashMap<String, String[]>();
    }

    public String get(String name) {
        final String[] values = servletParameterMap.get(name);
        if (null == values || 0 == values.length) {
            return null;
        }
        return values[0];
    }

    public void put(String name, String value) {
        servletParameterMap = new HashMap<String, String[]>(servletParameterMap);
        servletParameterMap.put(name, new String[] { value });
    }

    public String queryString() {
        final StringBuilder builder;

        builder = new StringBuilder(150);
        if (servletParameterMap.isEmpty()) {
            return "";
        }
        for (String name : servletParameterMap.keySet()) {
            builder.append(encode(name));
            builder.append("=").append(encode(get(name)));
            builder.append("&");
        }
        builder.deleteCharAt(builder.length() - 1);

        return builder.toString();
    }

    protected String encode(String value) {
        try {
            return URLEncoder.encode(value, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("Problem while encoding '" + value + "'", e);
        }
    }

}
