package com.thoughtworks.selenium.grid;

import java.net.URL;
import java.net.URLClassLoader;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.io.DataInputStream;

/**
 * Instrospect Java Classpath at Runtime. Useful for Debuuging.
 */
public class ClasspathHelper {

    public static String readFromClasspath(String path) throws IOException {
        final ClassLoader classLoader;
        final String content;

        InputStream stream = null;
        classLoader = Thread.currentThread().getContextClassLoader();
        stream = classLoader.getResourceAsStream(path);
        if (null == stream) {
            throw new RuntimeException("cannot find '" + path + "' in classpath");
        }

        content = read(stream);
        return content;
    }
    
    public static void dumpClasspath(Object javaObject, PrintStream outputStream) {
        final URLClassLoader classLoader;
        
        classLoader = (URLClassLoader) javaObject.getClass().getClassLoader();
        for (URL url : classLoader.getURLs()) {
            outputStream.println(url.getFile());
        }
    }

    protected static String read(InputStream in) throws IOException {
        DataInputStream ds;
        final StringBuffer sb;

        sb = new StringBuffer();
        String line = null;
        ds = new DataInputStream(in);
        while ((line = ds.readLine()) != null) {
            sb.append(line);
            sb.append("\n");
        }
        return sb.toString();
    }

}
