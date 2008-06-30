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

    protected static String read(InputStream inputStream) throws IOException {
        DataInputStream dataInputStream;
        final StringBuffer buffer;

        buffer = new StringBuffer();
        String line = null;
        dataInputStream = new DataInputStream(inputStream);
        while ((line = dataInputStream.readLine()) != null) {
            buffer.append(line);
            buffer.append('\n');
        }
        return buffer.toString();
    }

}
