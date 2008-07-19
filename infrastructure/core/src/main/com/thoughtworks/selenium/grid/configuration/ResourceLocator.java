package com.thoughtworks.selenium.grid.configuration;

import com.thoughtworks.selenium.grid.IOHelper;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.net.URL;

/**
 * Retrieve resources from classpath.
 */
public class ResourceLocator {

    private final Class root;
    private static final int COPY_BUFFER_SIZE = 1024;

    /**
     * Constructor.
     *
     * @param root  Resources will be resolved relative to this class id their path do not start with a slash.
     *              Should not be null.
     */
    public ResourceLocator(Class root) {
        this.root = root;
    }


    public String retrieveContent(String resourcePath) {
        final StringWriter writer = new StringWriter();
        InputStream inputStream = null;
        URL resource;

        try {
            resource = root.getResource(resourcePath);
            if (null == resource) {
                throw new IllegalStateException("Could not find '" + resourcePath + "' in classpath. Root is " + root);
            }
            inputStream = resource.openStream();
            IOHelper.copyStream(inputStream, writer, COPY_BUFFER_SIZE);
            return writer.toString();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            IOHelper.close(writer);
            IOHelper.close(inputStream);
        }
    }

}
