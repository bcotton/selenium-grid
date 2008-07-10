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
        new Environment(null, "a browser", (DynamicRemoteControlPool) mock(DynamicRemoteControlPool.class));
    }

    @Test
    public void nameIsTheNameProvidedToConstructor() {
      assertEquals("an environment", new Environment("an environment", "a browser", null).name());
    }

    @Test
    public void browserIsTheBrowserProvidedToConstructor() {
      assertEquals("some browser", new Environment("an environment", "some browser", null).browser());
    }

    @Test
    public void poolIsThePoolProvidedToConstructor() {
      DynamicRemoteControlPool aPool = (DynamicRemoteControlPool) mock(DynamicRemoteControlPool.class);
      assertSame(aPool, new Environment("an environment", "some browser", aPool).pool());
    }

    @Test
    public void anEnvironmentIsNotEqualToNull() {
      assertNotEquals(null, new Environment("an env", "a browser", null));
    }

    @Test
    public void anEnvironmentIsNotEqualToARandomObject() {
      assertNotEquals("random", new Environment("an env", "a browser", null));
    }

    @Test
    public void twoEnvironmentsWithTheSameNameAreEquals() {
      assertEquals(new Environment("an environment", "*firefox", null), new Environment("an environment", "*ie", null));
    }

    @Test
    public void twoEnvironmentsWithDifferentNamesAreNotEqual() {
      assertNotEquals(new Environment("an environment", "*browser", null),
                      new Environment("another environment", "*browser", null));
    }

    @Test
    public void twoEnvironmentsWithTheSameNameHaveTheSameHashcode() {
      assertSameHashCode(new Environment("an environment", "*browser", null),
                         new Environment("an environment", "*browser", null));
    }

    @Test
    public void twoEnvironmentsWithDifferentNamesDoNotHaveTheSameHashcode() {
      assertDistinctHashCodes(new Environment("an environment", "*browser", null),
                              new Environment("another environment", "*browser", null));
    }

    @Test
    public void toStringReturnsAHumanFriendlyString() {
      assertEquals("[Environment name='Firefox / Linux', browser='*chrome']",
                   new Environment("Firefox / Linux", "*chrome", null).toString());
    }

}
