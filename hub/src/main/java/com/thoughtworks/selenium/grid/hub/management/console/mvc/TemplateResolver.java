package com.thoughtworks.selenium.grid.hub.management.console.mvc;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;

/**
 * Find a template by name by looking it up on the current classpath.
 */
public class TemplateResolver {

    private final Class resourceRoot;

    public TemplateResolver(Class resourceRoot) {
        this.resourceRoot = resourceRoot;
    }

    public Template get(String templateName) {
        final Reader templateReader;

        try {
            templateReader = getContent(templateName);
            return new Template(new freemarker.template.Template(templateName, templateReader, null));
        } catch (IOException e) {
            throw new RenderingException(e);
        }
    }

    protected Reader getContent(String templateName) throws IOException {
        final URL res = resourceRoot.getResource(templateName);
        if (null == res) {
            throw new RenderingException("Could not find '" + templateName + "' in classpath. Resolving from '"
                    + resourceRoot + "'");
        }
        return new InputStreamReader(res.openStream());
    }
}
