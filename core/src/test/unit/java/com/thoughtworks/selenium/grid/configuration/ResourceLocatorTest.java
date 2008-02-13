package com.thoughtworks.selenium.grid.configuration;

import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.assertNotNull;
import org.junit.Test;


public class ResourceLocatorTest {

    @Test
    public void retrieveResourcesByAbsolutePath() {
        assertNotNull(new ResourceLocator(ResourceLocator.class).retrieveContent("/java/lang/Object.class"));
    }

    @Test
    public void retrieveResourcesByPathRelativeToRootClass() {
        assertNotNull(new ResourceLocator(Math.class).retrieveContent("Object.class"));
    }

    @Test(expected = IllegalStateException.class)
    public void throwAnIllegalStateExceptionWhenResourceCannotBeFound() {
        assertNotNull(new ResourceLocator(Math.class).retrieveContent("does/not/exists.class"));
    }

    @Test
    public void canRetrieveGridConfigurationFromClasspath() {
        final String content = new ResourceLocator(ResourceLocator.class).retrieveContent("/grid_configuration.yml");
        assertTrue(content.trim().startsWith("hub:"));
    }

}