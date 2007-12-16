package com.thoughtworks.selenium.grid;

import java.net.URL;
import java.net.URLClassLoader;
import java.io.IOException;
import java.io.InputStream;

/**
 * Instrospect Java Classpath at Runtime. Useful for Debuuging.
 */
public class ClasspathHelper {

    public static String readFromClasspath(String path) throws IOException {
        final String content;

        InputStream stream = null;
        ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
        stream = classLoader.getResourceAsStream(path);
        if (null == stream) {
            throw new RuntimeException("cannot find '" + path + "' in classpath");
        }

        content = read(stream);
        return content;
    }
    
    public static void dumpClasspath(Object javaObject) {
        URLClassLoader classLoader = (URLClassLoader) javaObject.getClass().getClassLoader();
        for (URL url : classLoader.getURLs()) {
            System.out.println(url.getFile());
        }
    }

    protected static String read(InputStream in)
            throws IOException {
        StringBuffer sb = new StringBuffer();
        String line = null;
        java.io.DataInputStream ds = new java.io.DataInputStream(in);
        while ((line = ds.readLine()) != null) {
            sb.append(line);
            sb.append("\n");
        }
        return sb.toString();
    }

}
