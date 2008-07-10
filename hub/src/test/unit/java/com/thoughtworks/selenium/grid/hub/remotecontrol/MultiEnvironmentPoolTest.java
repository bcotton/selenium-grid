package com.thoughtworks.selenium.grid.hub.remotecontrol;

import com.thoughtworks.selenium.grid.hub.Environment;
import com.thoughtworks.selenium.grid.hub.EnvironmentManager;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertSame;
import static junit.framework.Assert.assertTrue;
import org.jbehave.classmock.UsingClassMock;
import org.jbehave.core.mock.Mock;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;


public class MultiEnvironmentPoolTest extends UsingClassMock {

    @Test
    public void reserveReturnsTheRemoteControlReservedOnEnvironmentPool() {
        final Mock environmentPool;
        final MultiEnvironmentPool pool;
        final EnvironmentManager environmentManager;
        final RemoteControlProxy remoteControl;
        final Environment environment;

        environmentManager = new EnvironmentManager();
        remoteControl = new RemoteControlProxy("", 0, "", 1, null);
        pool = new MultiEnvironmentPool(environmentManager);
        environmentPool = mock(DynamicRemoteControlPool.class);
        environment = new Environment("an environment", "*chrome", (DynamicRemoteControlPool) environmentPool);
        environmentManager.addEnvironment(environment);
        environmentPool.expects("reserve").with(environment).will(returnValue(remoteControl));

        assertEquals(remoteControl, pool.reserve(environment));
        verifyMocks();
    }

    @Test
    public void getPoolForSessionReturnsThePoolThatHaveBeenAssociatedWithASession() {
        final EnvironmentManager environmentManager;
        final RemoteControlProxy remoteControl;
        final MultiEnvironmentPool pool;
        final Environment environment;
        final Mock environmentPool;

        environmentManager = new EnvironmentManager();
        remoteControl = new RemoteControlProxy("host", 0, "an environment", 1, null);
        pool = new MultiEnvironmentPool(environmentManager);
        environmentPool = mock(DynamicRemoteControlPool.class);
        environment = new Environment("an environment", "*chrome", (DynamicRemoteControlPool) environmentPool);
        environmentManager.addEnvironment(environment);
        environmentPool.expects("associateWithSession").with(remoteControl, "a session id");

        pool.associateWithSession(remoteControl, "a session id");

        assertSame(environmentPool, pool.getPoolForSession("a session id"));
        verifyMocks();
    }

    @Test
    public void getPoolForSessionReturnsNullWhenNoPoolHaveBeenAssociatedWithThisSession() {
        final MultiEnvironmentPool pool;
        final EnvironmentManager environmentManager;

        environmentManager = new EnvironmentManager();
        pool = new MultiEnvironmentPool(environmentManager);

        assertEquals(null, pool.getPoolForSession("a session id"));
    }

    @Test
    public void releaseForSessionDelegatesCallToEnvironmentPoolAndForgetsAboutThisSession() {
        final EnvironmentManager environmentManager;
        final RemoteControlProxy remoteControl;
        final MultiEnvironmentPool pool;
        final Environment environment;
        final Mock environmentPool;

        environmentManager = new EnvironmentManager();
        remoteControl = new RemoteControlProxy("host", 0, "an environment", 1, null);
        pool = new MultiEnvironmentPool(environmentManager);
        environmentPool = mock(DynamicRemoteControlPool.class);
        environment = new Environment("an environment", "*chrome", (DynamicRemoteControlPool) environmentPool);
        environmentManager.addEnvironment(environment);
        environmentPool.expects("associateWithSession").with(remoteControl, "a session id");
        environmentPool.expects("releaseForSession").with("a session id");

        pool.associateWithSession(remoteControl, "a session id");
        pool.releaseForSession("a session id");

        assertEquals(null, pool.getPoolForSession("a session id"));
        verifyMocks();
    }

    @Test
    public void releaseDelegatesCallToEnvironmentPoolAndForgetsAboutThisSession() {
        final EnvironmentManager environmentManager;
        final RemoteControlProxy remoteControl;
        final Environment newEnvironment;
        final MultiEnvironmentPool pool;
        final Mock environmentPool;

        environmentManager = new EnvironmentManager();
        remoteControl = new RemoteControlProxy("host", 0, "an environment", 1, null);
        pool = new MultiEnvironmentPool(environmentManager);
        environmentPool = mock(DynamicRemoteControlPool.class);
        newEnvironment = new Environment("an environment", "*chrome", (DynamicRemoteControlPool) environmentPool);
        environmentManager.addEnvironment(newEnvironment);
        environmentPool.expects("release").with(remoteControl);

        pool.release(remoteControl);
        verifyMocks();
    }

    @Test
    public void getReturnsRemoteControlThatHaveBeenAssociatedWithASession() {
        final EnvironmentManager environmentManager;
        final RemoteControlProxy remoteControl;
        final Environment environment;
        final MultiEnvironmentPool pool;
        final Mock environmentPool;

        environmentManager = new EnvironmentManager();
        remoteControl = new RemoteControlProxy("host", 0, "an environment", 1, null);
        pool = new MultiEnvironmentPool(environmentManager);
        environmentPool = mock(DynamicRemoteControlPool.class);
        environment = new Environment("an environment", "*chrome", (DynamicRemoteControlPool) environmentPool);
        environmentManager.addEnvironment(environment);
        environmentPool.expects("associateWithSession").with(remoteControl, "a session id");
        environmentPool.expects("retrieve").with("a session id").will(returnValue(remoteControl));

        pool.associateWithSession(remoteControl, "a session id");

        assertEquals(remoteControl, pool.retrieve("a session id"));
        verifyMocks();
    }

    @Test
    public void availableRemoteControlsReturnAnEmptyArrayWhenThereIsNoEnvironment() {
        final EnvironmentManager environmentManager;

        environmentManager = new EnvironmentManager();
        assertTrue(new MultiEnvironmentPool(environmentManager).availableRemoteControls().isEmpty());
        verifyMocks();
    }

    @Test
    public void availableRemoteControlsReturnAvailableRemoteControlsForAllEnvironments() {
        final List<RemoteControlProxy> remoteControlsForFirstPool;
        final List<RemoteControlProxy> remoteControlsForSecondPool;
        final List<RemoteControlProxy> availableRemoteControls;
        final EnvironmentManager environmentManager;
        final Environment firstEnvironment;
        final Environment secondEnvironment;
        final Mock firstEnvironmentPool;
        final Mock secondEnvironmentPool;

        remoteControlsForFirstPool = Arrays.asList(new RemoteControlProxy("first host", 0, "first environment", 1, null));
        remoteControlsForSecondPool = Arrays.asList(
                new RemoteControlProxy("second host", 0, "second environment", 1, null),
                new RemoteControlProxy("third host", 0, "second environment", 1, null)
        );
        firstEnvironmentPool = mock(DynamicRemoteControlPool.class);
        secondEnvironmentPool = mock(DynamicRemoteControlPool.class);
        firstEnvironment = new Environment("first environment", "*chrome", (DynamicRemoteControlPool) firstEnvironmentPool);
        secondEnvironment = new Environment("second environment", "*chrome", (DynamicRemoteControlPool) secondEnvironmentPool);
        environmentManager = new EnvironmentManager();
        environmentManager.addEnvironment(firstEnvironment);
        environmentManager.addEnvironment(secondEnvironment);

        firstEnvironmentPool.stubs("availableRemoteControls").will(returnValue(remoteControlsForFirstPool));
        secondEnvironmentPool.stubs("availableRemoteControls").will(returnValue(remoteControlsForSecondPool));

        availableRemoteControls = new MultiEnvironmentPool(environmentManager).availableRemoteControls();
        assertEquals(3, availableRemoteControls.size());
        assertTrue(availableRemoteControls.containsAll(remoteControlsForFirstPool));
        assertTrue(availableRemoteControls.containsAll(remoteControlsForSecondPool));
        verifyMocks();
    }

    @Test
    public void reservedRemoteControlsReturnAnEmptyArrayWhenThereIsNoEnvironment() {
        final EnvironmentManager environmentManager;

        environmentManager = new EnvironmentManager();
        assertTrue(new MultiEnvironmentPool(environmentManager).reservedRemoteControls().isEmpty());
        verifyMocks();
    }

    @Test
    public void reservedRemoteControlsReturnAvailableRemoteControlsForAllEnvironments() {
        final List<RemoteControlProxy> remoteControlsForFirstPool;
        final List<RemoteControlProxy> remoteControlsForSecondPool;
        final List<RemoteControlProxy> reservedRemoteControls;
        final EnvironmentManager environmentManager;
        final Environment firstEnvironment;
        final Environment secondEnvironment;
        final Mock firstEnvironmentPool;
        final Mock secondEnvironmentPool;

        remoteControlsForFirstPool = Arrays.asList(new RemoteControlProxy("first host", 0, "first environment", 1, null));
        remoteControlsForSecondPool = Arrays.asList(
                new RemoteControlProxy("second host", 0, "second environment", 1, null),
                new RemoteControlProxy("third host", 0, "second environment", 1, null)
        );
        firstEnvironmentPool = mock(DynamicRemoteControlPool.class);
        secondEnvironmentPool = mock(DynamicRemoteControlPool.class);
        firstEnvironment = new Environment("first environment", "*chrome", (DynamicRemoteControlPool) firstEnvironmentPool);
        secondEnvironment = new Environment("second environment", "*chrome", (DynamicRemoteControlPool) secondEnvironmentPool);
        environmentManager = new EnvironmentManager();
        environmentManager.addEnvironment(firstEnvironment);
        environmentManager.addEnvironment(secondEnvironment);

        firstEnvironmentPool.stubs("reservedRemoteControls").will(returnValue(remoteControlsForFirstPool));
        secondEnvironmentPool.stubs("reservedRemoteControls").will(returnValue(remoteControlsForSecondPool));

        reservedRemoteControls = new MultiEnvironmentPool(environmentManager).reservedRemoteControls();
        assertEquals(3, reservedRemoteControls.size());
        assertTrue(reservedRemoteControls.containsAll(remoteControlsForFirstPool));
        assertTrue(reservedRemoteControls.containsAll(remoteControlsForSecondPool));
        verifyMocks();
    }

}
