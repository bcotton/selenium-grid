package com.thoughtworks.selenium.grid.hub.management.console.mvc;

import freemarker.template.TemplateException;
import static junit.framework.Assert.assertEquals;
import org.jbehave.classmock.UsingClassMock;
import org.junit.Test;

import java.io.IOException;
import java.io.StringReader;
import java.io.Writer;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class TemplateTest extends UsingClassMock {

    @Test
    public void freemarkerConfigurationIsTheOneProvidedInTheConstructor() throws IOException {
        final freemarker.template.Template expectedTemplate;

        expectedTemplate = new freemarker.template.Template(null, new StringReader(""), null);

      assertEquals(expectedTemplate, new Template(expectedTemplate).freemarkerTemplate());
    }

    @Test
    public void renderPlainContentAsIs() throws IOException, TemplateException {
        final Template template;

        template = new Template("Hello World");
        assertEquals("Hello World", template.render(null));
    }

    @Test
    public void renderInterpretsAssignedVariables() throws IOException, TemplateException {
        final Map<String, Object> assigns;
        final Template template;

        template = new Template("Hello ${somebody}");
        assigns = new HashMap<String, Object>();
        assigns.put("somebody", "Guys");
        assertEquals("Hello Guys", template.render(assigns));
    }

    @Test
    public void renderIteratesOnCollections() throws IOException, TemplateException {
        final Map<String, Object> bindings;
        final Template template;

        template = new Template("<#list items as item>* ${item}\n</#list>");
        bindings = new HashMap<String, Object>();
        bindings.put("items", Arrays.asList("one", "two", "three"));
        assertEquals("* one\n* two\n* three\n", template.render(bindings));
    }

    @Test
    public void readTemplateFromAReaderWhenProvidedInConstructor() throws IOException, TemplateException {
        final Template template;

        template = new Template(new StringReader("Template Content"));
        assertEquals("Template Content", template.render(null));
    }

    @Test
    public void readTemplateFromAFreemarkerTemplateWhenProvidedInConstructor() throws IOException, TemplateException {
        final freemarker.template.Template freemarkerTemplate;
        final Template template;

        freemarkerTemplate = new freemarker.template.Template(null, new StringReader("Template Content"), null);
        template = new Template(freemarkerTemplate);
        assertEquals("Template Content", template.render(null));
    }


    @Test(expected = RenderingException.class)
    public void ioExceptionsAreEncapsulatedAsRenderingExceptions() throws IOException {
        final freemarker.template.Template freemarkerTemplate;
        final Template template;

        freemarkerTemplate = new freemarker.template.Template(null, new StringReader(""), null, null) {
            public void process(Object o, Writer writer) throws TemplateException, IOException {
                throw new IOException();
            }
        };
        template = new Template(freemarkerTemplate);
        template.render(null);
    }

    @Test(expected = RenderingException.class)
    public void templateExceptionsAreEncapsulatedAsRenderingExceptions() throws IOException {
        final freemarker.template.Template freemarkerTemplate;
        final Template template;

        freemarkerTemplate = new freemarker.template.Template(null, new StringReader(""), null, null) {
            public void process(Object o, Writer writer) throws TemplateException, IOException {
                throw new TemplateException(null);
            }
        };
        template = new Template(freemarkerTemplate);
        template.render(null);
    }

    @Test
    public void contentReturnsTemplateContent() throws IOException {
        freemarker.template.Template freemarkerTemplate;
        final Template template;

        freemarkerTemplate = new freemarker.template.Template("", new StringReader("Expected Content"), null);
        template = new Template(freemarkerTemplate);
        assertEquals("Expected Content", template.content());
    }

}
