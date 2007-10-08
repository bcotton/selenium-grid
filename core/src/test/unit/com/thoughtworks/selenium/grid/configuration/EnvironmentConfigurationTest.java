package com.thoughtworks.selenium.grid.configuration;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import org.junit.Test;


public class EnvironmentConfigurationTest {

    @Test
    public void getNameIsTheNameProvidedToConstructor() {
        assertEquals("an environment", new EnvironmentConfiguration("an environment", "a browser").getName());
    }

    @Test
    public void getBrowserIsTheBrowserProvidedToConstructor() {
        assertEquals("*some-browser", new EnvironmentConfiguration("an environment", "*some-browser").getBrowser());
    }

    @Test
    public void anEnvironmentInstanceIsEqualToItself() {
        final EnvironmentConfiguration anEnvironment = new EnvironmentConfiguration("same environment", "same browser");
        assertEquals(anEnvironment, anEnvironment);
    }

    @Test
    public void twoEnvironmentsWithTheSameNameAndBrowserAreEquals() {
        assertEquals(new EnvironmentConfiguration("same environment", "same browser"),
                     new EnvironmentConfiguration("same environment", "same browser"));
    }

    @Test
    public void twoEnvironmentsWithTheSameNameButDifferentBrowsersAreNotEquals() {
        assertFalse(new EnvironmentConfiguration("an environment", "*firefox").equals(
                    new EnvironmentConfiguration("an environment", "*ie")));
    }

    @SuppressWarnings({"EqualsBetweenInconvertibleTypes"})
    @Test
    public void anEnvironmentIsNotEqualToARandomObject() {
        assertFalse(new EnvironmentConfiguration("an environment", "*ie").equals("a random object"));
    }

    @Test
    public void twoEnvironmentsWithoutTheSameNameAreNotEquals() {
        final EnvironmentConfiguration anEnvironment = new EnvironmentConfiguration("an environment", "*ie");
        final EnvironmentConfiguration anotherEnvironment = new EnvironmentConfiguration("another environment", "*ie");
        assertFalse(anEnvironment.equals(anotherEnvironment));
    }

    @Test
    public void twoEnvironmentsWithTheSameNameAndBrowserHaveTheSameHashcode() {
        final EnvironmentConfiguration anEnvironment = new EnvironmentConfiguration("same environment", "same browser");
        final EnvironmentConfiguration anotherEnvironment = new EnvironmentConfiguration("same environment", "same browser");
        assertEquals(anEnvironment.hashCode(), anotherEnvironment.hashCode());
    }

    @Test
    public void twoEnvironmentsWithDifferentBrowsersDoNotHaveTheSameHashcode() {
        final EnvironmentConfiguration anEnvironment = new EnvironmentConfiguration("same environment", "a browser");
        final EnvironmentConfiguration anotherEnvironment = new EnvironmentConfiguration("same environment", "another browser");
        assertFalse(anEnvironment.hashCode() == anotherEnvironment.hashCode());
    }

    @Test
    public void twoEnvironmentsWithoutDifferentNamesDoNotHaveTheSameHashCode() {
        final EnvironmentConfiguration anEnvironment = new EnvironmentConfiguration("an environment", "*ie");
        final EnvironmentConfiguration anotherEnvironment = new EnvironmentConfiguration("another environment", "*ie");
        assertFalse(anEnvironment.hashCode() == anotherEnvironment.hashCode());
    }

    @Test
    public void toStringReturnAHumanFriendlyString() {
        assertEquals("[EnvironmentConfiguration name='Firefox / Linux', browser='*chrome']",
                     new EnvironmentConfiguration("Firefox / Linux", "*chrome").toString());
    }

    @Test
    public void coumpoundKeyReturnsNullNullWhenBothNameAndBrowserAreNull() {
        assertEquals("nullnull", new EnvironmentConfiguration(null, null).compoundKey());
    }

    @Test
    public void coumpoundKeyReturnsNameNullWhenBrowserIsNull() {
        assertEquals("a namenull", new EnvironmentConfiguration("a name", null).compoundKey());
    }

    @Test
    public void coumpoundKeyReturnsNullBrowserWhenNameIsNull() {
        assertEquals("nulla browser", new EnvironmentConfiguration(null, "a browser").compoundKey());
    }

    @Test
    public void coumpoundKeyReturnsConcatenatedNameAndBrowserWhenNoneAreNull() {
        assertEquals("a namea browser", new EnvironmentConfiguration("a name", "a browser").compoundKey());
    }

}