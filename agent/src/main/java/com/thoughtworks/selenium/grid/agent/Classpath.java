package com.thoughtworks.selenium.grid.agent;

import com.thoughtworks.selenium.grid.Join;

import java.util.List;
import java.util.LinkedList;
import java.io.File;

/**
 * Describes a simple Java classpath.
 */
public class Classpath {

    private final List<String> entries;

    public Classpath() {
      entries = new LinkedList<String>();
    }

    public String[] entries() {
        return entries.toArray(new String[entries.size()]);
    }

    public void add(String newEntry) {
        entries.add(newEntry);
    }

    public String toString() {
        return new Join(entries, File.pathSeparator).toString();
    }
    
}
