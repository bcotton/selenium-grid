package com.thoughtworks.selenium.grid.hub.management.console.mvc;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import org.jbehave.classmock.UsingClassMock;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;


public class TemplateResolverTest extends UsingClassMock {

    @Test
    public void getContentResolvesResourceFromClassProvidedInConstructor() throws IOException {
        Reader reader = new TemplateResolver(TemplateResolverTest.class).getContent("a_template.html");
        assertNotNull(reader);
        assertEquals("The Template Content", new BufferedReader(reader).readLine());
    }

    @Test(expected = RenderingException.class)
    public void throwsARenderingExceptionWhenTemplateCannotBeResolved() throws IOException {
        new TemplateResolver(TemplateResolverTest.class).getContent("a_template_that_does_not_exist.html");
    }

    @Test
    public void getWrapsResolvedContentInATemplateObject() throws IOException {
        TemplateResolver stubbedResolver;

        stubbedResolver = new TemplateResolver(null) {
            protected Reader getContent(String templateName) throws IOException {
                assertEquals("a template", templateName);
                return new StringReader("Expected Content");
            }
        };

        assertEquals("Expected Content", stubbedResolver.get("a template").content());
    }

}
