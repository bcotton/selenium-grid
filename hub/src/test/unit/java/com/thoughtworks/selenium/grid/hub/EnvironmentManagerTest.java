package com.thoughtworks.selenium.grid.hub;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import org.junit.Test;


public class EnvironmentManagerTest {

    @Test
    public void enviromentReturnsNoEnvironmentWhenNoneHaveBeenAdded() {
        assertTrue(new EnvironmentManager().environments().isEmpty());
    }

    @Test
    public void enviromentReturnAnEnvironmentWhenAdded() {
        final Environment knownEnvironment;
        final EnvironmentManager manager;

        manager = new EnvironmentManager();
        knownEnvironment = new Environment("an environment", "*browser");
        manager.addEnvironment(knownEnvironment);
        
        assertEquals(1, manager.environments().size());
        assertEquals(knownEnvironment, manager.environments().get(0));
    }

    @Test
    public void environmentReturnsNullWhenEnvironmentNameIsUnknown() {
        assertEquals(null, new EnvironmentManager().environment("unknown environment"));
    }

    @Test
    public void environmentReturnsEnvironmentMatchingANameWhenAdded() {
        final Environment knownEnvironment;
        final EnvironmentManager manager;

        manager = new EnvironmentManager();
        knownEnvironment = new Environment("an environment", "*browser");
        manager.addEnvironment(knownEnvironment);

        assertEquals(knownEnvironment, manager.environment("an environment"));
    }

}
