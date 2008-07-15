package com.thoughtworks.selenium.grid.hub;

import static com.thoughtworks.selenium.grid.AssertionHelper.assertDistinctHashCodes;
import static com.thoughtworks.selenium.grid.AssertionHelper.assertNotEquals;
import static com.thoughtworks.selenium.grid.AssertionHelper.assertSameHashCode;
import com.thoughtworks.selenium.grid.hub.remotecontrol.DynamicRemoteControlPool;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertSame;
import org.jbehave.classmock.UsingClassMock;
import org.junit.Test;


public class EnvironmentTest extends UsingClassMock {

    @Test(expected = IllegalArgumentException.class)
    public void throwsAnIllegalArgumentExceptionWhenNameIsNull() {
        new Environment(null, "a browser");
    }

    @Test
    public void nameIsTheNameProvidedToConstructor() {
      assertEquals("an environment", new Environment("an environment", "a browser").name());
    }

    @Test
    public void browserIsTheBrowserProvidedToConstructor() {
      assertEquals("some browser", new Environment("an environment", "some browser").browser());
    }

    @Test
    public void anEnvironmentIsNotEqualToNull() {
      assertNotEquals(null, new Environment("an env", "a browser"));
    }

    @Test
    public void anEnvironmentIsNotEqualToARandomObject() {
      assertNotEquals("random", new Environment("an env", "a browser"));
    }

    @Test
    public void twoEnvironmentsWithTheSameNameAreEquals() {
      assertEquals(new Environment("an environment", "*firefox"), new Environment("an environment", "*ie"));
    }

    @Test
    public void twoEnvironmentsWithDifferentNamesAreNotEqual() {
      assertNotEquals(new Environment("an environment", "*browser"),
                      new Environment("another environment", "*browser"));
    }

    @Test
    public void twoEnvironmentsWithTheSameNameHaveTheSameHashcode() {
      assertSameHashCode(new Environment("an environment", "*browser"),
                         new Environment("an environment", "*browser"));
    }

    @Test
    public void twoEnvironmentsWithDifferentNamesDoNotHaveTheSameHashcode() {
      assertDistinctHashCodes(new Environment("an environment", "*browser"),
                              new Environment("another environment", "*browser"));
    }

    @Test
    public void toStringReturnsAHumanFriendlyString() {
      assertEquals("[Environment name='Firefox / Linux', browser='*chrome']",
                   new Environment("Firefox / Linux", "*chrome").toString());
    }

}
