package com.thoughtworks.selenium.grid.hub.management.console.mvc;

import freemarker.template.TemplateException;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.util.Map;

/**
 * Document template whose content can be dynamically generated based on a collection of assigns variables.
 */
public class Template {

    private final freemarker.template.Template freemarkerTemplate;

    public Template(freemarker.template.Template freemarkerTemplate) {
        this.freemarkerTemplate = freemarkerTemplate;
    }

    public Template(Reader reader) throws IOException {
        this(new freemarker.template.Template("", reader, null, null));
    }

    public Template(String content) throws IOException {
        this(new StringReader(content));
    }

    public String render(Map<String, Object> assigns)  {
        final StringWriter out = new StringWriter();
        
        try {
            freemarkerTemplate.process(assigns, out);
        } catch (TemplateException e) {
            throw new RenderingException(e);
        } catch (IOException e) {
            throw new RenderingException(e);
        }
        return out.toString();
    }

    protected String content() throws IOException {
        final StringWriter writer = new StringWriter();
        freemarkerTemplate.dump(writer);
        return writer.toString();
    }

    protected freemarker.template.Template freemarkerTemplate() {
        return freemarkerTemplate;
    }

    /** Do not use. Workaround for the mocking framework */
    protected Template() {
        this.freemarkerTemplate = null;
    }


}
