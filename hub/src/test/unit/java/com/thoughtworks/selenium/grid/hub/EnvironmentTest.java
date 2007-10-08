package com.thoughtworks.selenium.grid.hub;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import org.junit.Test;
import com.thoughtworks.selenium.grid.hub.remotecontrol.MonoEnviromentPool;


public class EnvironmentTest {

    @Test(expected = IllegalArgumentException.class)
    public void throwsAnIllegalArgumentExceptionWhenNameIsNull() {
        new Environment(null, "a browser", new MonoEnviromentPool(null));
    }

    @Test
    public void nameIsTheNameProvidedToConstructor() {
        assertEquals("an environment", new Environment("an environment", "a browser", null).name());
    }

    @Test
    public void browserIsTheBrowserProvidedToConstructor() {
        assertEquals("*some-browser", new Environment("an environment", "*some-browser", null).browser());
    }

    public void poolIsTheRemoteControlPoolProvidedToConstructor() {
        new Environment("Firefox / Linux", "*chrome", null).pool();
    }

    @Test
    public void twoEnvironmentsWithTheSameNameAreEquals() {
        assertEquals(new Environment("an environment", "*firefox", null),
                     new Environment("an environment", "*ie", null));
    }

    @SuppressWarnings({"EqualsBetweenInconvertibleTypes"})
    @Test
    public void anEnvironmentIsNotEqualToARandomObject() {
        assertFalse(new Environment("an environment", "*ie", null).equals("a random object"));
    }

    @Test
    public void twoEnvironmentsWithoutTheSameNameAreNotEquals() {
        final Environment anEnvironment = new Environment("an environment", "*ie", null);
        final Environment anotherEnvironment = new Environment("another environment", "*ie", null);
        assertFalse(anEnvironment.equals(anotherEnvironment));
    }

    @Test
    public void twoEnvironmentsWithTheSameNameHaveTheSameHashcode() {
        final Environment anEnvironment = new Environment("an environment", "*ie", null);
        final Environment anotherEnvironment = new Environment("an environment", "*firefox", null);
        assertEquals(anEnvironment.hashCode(), anotherEnvironment.hashCode());
    }

    @Test
    public void twoEnvironmentsWithoutTheSameNameDoNotHaveTheSameHashCode() {
        final Environment anEnvironment = new Environment("an environment", "*ie", null);
        final Environment anotherEnvironment = new Environment("another environment", "*ie", null);
        assertFalse(anEnvironment.hashCode() == anotherEnvironment.hashCode());
    }

    @Test
    public void toStringReturnAHumanFriendlyString() {
        assertEquals("[Environment name='Firefox / Linux', browser='*chrome']",
                     new Environment("Firefox / Linux", "*chrome", null).toString());
    }

}
