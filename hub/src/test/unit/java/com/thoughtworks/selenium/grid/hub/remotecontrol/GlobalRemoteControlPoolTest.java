package com.thoughtworks.selenium.grid.hub.remotecontrol;

import com.thoughtworks.selenium.grid.hub.Environment;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;
import static junit.framework.Assert.assertSame;
import static junit.framework.Assert.assertTrue;
import org.jbehave.classmock.UsingClassMock;
import org.jbehave.core.mock.Mock;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.ArrayList;


public class GlobalRemoteControlPoolTest extends UsingClassMock {

    @Test
    public void registerAddsTheRemoteControlToTheProvisioner() {
        final Mock provisioner;
        final RemoteControlProxy remoteControl;
        final GlobalRemoteControlPool pool;

        remoteControl = new RemoteControlProxy("", 0, "an environment", 1, null);
        provisioner = mock(RemoteControlProvisioner.class);
        pool = new GlobalRemoteControlPool() {
            protected RemoteControlProvisioner getProvisioner(String environment) {
                assertEquals("an environment", environment);
                return (RemoteControlProvisioner) provisioner;
            }
        };
        provisioner.expects("add").with(remoteControl);

        pool.register(remoteControl);
        verifyMocks();
    }

    @Test
    public void unregisterReturnsTheResultOfProvisionerRemove() {
        final Mock provisioner;
        final RemoteControlProxy remoteControl;
        final GlobalRemoteControlPool pool;

        remoteControl = new RemoteControlProxy("", 0, "an environment", 1, null);
        provisioner = mock(RemoteControlProvisioner.class);
        pool = new GlobalRemoteControlPool() {
            protected RemoteControlProvisioner getProvisioner(String environment) {
                assertEquals("an environment", environment);
                return (RemoteControlProvisioner) provisioner;
            }
        };

        provisioner.expects("remove").with(remoteControl).will(returnValue(false));
        assertEquals(false, pool.unregister(remoteControl));
        verifyMocks();
    }


    @Test
    public void unregisterRemovesTheRemoteControlToTheProvisioner() {
        final Mock provisioner;
        final RemoteControlProxy remoteControl;
        final GlobalRemoteControlPool pool;

        remoteControl = new RemoteControlProxy("", 0, "an environment", 1, null);
        provisioner = mock(RemoteControlProvisioner.class);
        pool = new GlobalRemoteControlPool() {
            protected RemoteControlProvisioner getProvisioner(String environment) {
                assertEquals("an environment", environment);
                return (RemoteControlProvisioner) provisioner;
            }
        };

        provisioner.expects("remove").with(remoteControl).will(returnValue(true));
        assertTrue(pool.unregister(remoteControl));
        verifyMocks();
    }

    @Test
    public void onceUnregisteredARemoteControlIsNotassociatedWithAnyExistingSession() {
        final RemoteControlProxy remoteControl;
        final GlobalRemoteControlPool pool;

        remoteControl = new RemoteControlProxy("", 0, "", 1, null);
        pool = new GlobalRemoteControlPool();

        pool.register(remoteControl);
        pool.associateWithSession(remoteControl, "a session id");
        pool.unregister(remoteControl);
        assertEquals(null, pool.retrieve("a session id"));
        verifyMocks();
    }

    @Test
    public void reserveReturnsTheRemoteControlReservedByTheProvisioner() {
        final Mock provisioner;
        final RemoteControlProxy remoteControl;
        final GlobalRemoteControlPool pool;

        remoteControl = new RemoteControlProxy("", 0, "an environment", 1, null);
        provisioner = mock(RemoteControlProvisioner.class);
        pool = new GlobalRemoteControlPool() {
            protected RemoteControlProvisioner getProvisioner(String environment) {
                assertEquals("an environment", environment);
                return (RemoteControlProvisioner) provisioner;
            }
        };

        provisioner.expects("reserve").will(returnValue(remoteControl));
        assertEquals(remoteControl, pool.reserve(new Environment("an environment", "")));
        verifyMocks();
    }

    @Test
    public void getRemoteControlReturnsNullWhenNoRemoteControlHasBeenAssociatedWithTheSessionId() {
        assertEquals(null, new GlobalRemoteControlPool().retrieve("unknown session id"));
    }

    @Test
    public void getRemoteControlReturnsTheRemoteControlHasBeenAssociatedWithASpecificSession() {
        final GlobalRemoteControlPool pool = new GlobalRemoteControlPool();
        final RemoteControlProxy remoteControl = new RemoteControlProxy("", 0, "", 1, null);

        pool.associateWithSession(remoteControl, "a session id");
        assertEquals(remoteControl, pool.retrieve("a session id"));
    }

    @Test(expected = IllegalStateException.class)
    public void associateWithSessionThrowsAnIllegalStateExceptionWhenSessionIdIsAlreadyRegistered() {
        final GlobalRemoteControlPool pool = new GlobalRemoteControlPool();

        pool.associateWithSession(new RemoteControlProxy("", 0, "", 1, null), "shared session id");
        pool.associateWithSession(new RemoteControlProxy("", 0, "", 1, null), "shared session id");
    }

    @Test
    public void associateWithSessionKeepsTrackOfEachSessionIndependently() {
        final GlobalRemoteControlPool pool = new GlobalRemoteControlPool();
        final RemoteControlProxy firstRemoteControl = new RemoteControlProxy("", 0, "", 1, null);
        final RemoteControlProxy secondRemoteControl = new RemoteControlProxy("", 0, "", 1, null);

        pool.associateWithSession(firstRemoteControl, "first session id");
        pool.associateWithSession(secondRemoteControl, "second session id");

        assertEquals(firstRemoteControl, pool.retrieve("first session id"));
        assertEquals(secondRemoteControl, pool.retrieve("second session id"));
    }

    @Test
    public void afterReleaseForSessionARemoteControlIsNotAssociatedWithASessionAnymore() {
        final RemoteControlProxy remoteControl;
        final GlobalRemoteControlPool pool;                                                

        remoteControl = new RemoteControlProxy("", 0, "an environment", 1, null);
        pool = new GlobalRemoteControlPool();
        pool.register(remoteControl);
        pool.reserve(new Environment("an environment", ""));
        pool.associateWithSession(remoteControl, "a session id");

        pool.releaseForSession("a session id");
        assertEquals(null, pool.retrieve("a session id"));
    }

    public void releaseForSessionDoesNotThrowsAnExceptionWhenSessionIsNotAssociated() {
        final GlobalRemoteControlPool pool = new GlobalRemoteControlPool();
        pool.releaseForSession("unknown session id");
    }

    @Test
    public void releaseForSessionReleasesTheRemoteControlOnTheProvisioner() {
        final Mock provisioner;
        final RemoteControlProxy remoteControl;
        final GlobalRemoteControlPool pool;

        remoteControl = new RemoteControlProxy("", 0, "an environment", 1, null);
        provisioner = mock(RemoteControlProvisioner.class);
        pool = new GlobalRemoteControlPool() {
            protected RemoteControlProvisioner getProvisioner(String environment) {
                assertEquals("an environment", environment);
                return (RemoteControlProvisioner) provisioner;
            }
        };
        pool.register(remoteControl);
        pool.associateWithSession(remoteControl, "a session id");

        provisioner.expects("release").with(remoteControl);
        pool.releaseForSession("a session id");
        assertEquals(null, pool.retrieve("a session id"));
        verifyMocks();
    }

    @Test
    public void releaseReleasesTheRemoteControlOnTheProvisioner() {
        final Mock provisioner;
        final RemoteControlProxy remoteControl;
        final GlobalRemoteControlPool pool;

        remoteControl = new RemoteControlProxy("", 0, "an environment", 1, null);
        provisioner = mock(RemoteControlProvisioner.class);
        pool = new GlobalRemoteControlPool() {
            protected RemoteControlProvisioner getProvisioner(String environment) {
                assertEquals("an environment", environment);
                return (RemoteControlProvisioner) provisioner;
            }
        };

        provisioner.expects("release").with(remoteControl);

        pool.release(remoteControl);
        assertEquals(null, pool.retrieve("a session id"));
        verifyMocks();
    }

//    @Test
//    public void availableRemoteControlsReturnAvailableRemoteControlsOfTheProvisioner() {
//        final Mock provisioner;
//        final List<RemoteControlProxy> expectedList;
//        final GlobalRemoteControlPool pool;
//
//        expectedList = new ArrayList<RemoteControlProxy>();
//        provisioner = mock(RemoteControlProvisioner.class);
//        pool = new GlobalRemoteControlPool();
//        provisioner.expects("availableRemoteControls").will(returnValue(expectedList));
//
//        assertEquals(expectedList, pool.availableRemoteControls());
//        verifyMocks();
//    }

//    @Test
//    public void reservedRemoteControlsReturnAvailableRemoteControlsOfTheProvisioner() {
//        final Mock provisioner;
//        final List<RemoteControlProxy> expectedList;
//        final GlobalRemoteControlPool pool;
//
//        expectedList = new ArrayList<RemoteControlProxy>();
//        provisioner = mock(RemoteControlProvisioner.class);
//        pool = new GlobalRemoteControlPool();
//        provisioner.expects("reservedRemoteControls").will(returnValue(expectedList));
//
//        assertEquals(expectedList, pool.reservedRemoteControls());
//        verifyMocks();
//    }

    
    @Test
    public void reserveReturnsTheRemoteControlReservedOnEnvironmentPool() {
        final GlobalRemoteControlPool pool;
        final Environment environment;
        final RemoteControlProxy remoteControl;

        remoteControl = new RemoteControlProxy("", 0, "an environment", 1, null);
        environment = new Environment("an environment", "*chrome");
        pool = new GlobalRemoteControlPool();
        pool.register(remoteControl);

        assertEquals(remoteControl, pool.reserve(environment));
        verifyMocks();
    }

    @Test
    public void getPoolForSessionReturnsThePoolThatHaveBeenAssociatedWithASession() {
        final RemoteControlProxy remoteControl;
        final GlobalRemoteControlPool pool;

        remoteControl = new RemoteControlProxy("host", 0, "an environment", 1, null);
        pool = new GlobalRemoteControlPool();
        pool.register(remoteControl);
        pool.associateWithSession(remoteControl, "a session id");

        assertSame(remoteControl, pool.getRemoteControlForSession("a session id"));
        verifyMocks();
    }

    @Test
    public void getPoolForSessionReturnsNullWhenNoPoolHaveBeenAssociatedWithThisSession() {
        assertNull(new GlobalRemoteControlPool().getRemoteControlForSession("a session id"));
    }

    @Test
    public void aRemoteControlIsNotAsssociateWithTheSesssionAfterCallingReleaseForSession() {
        final RemoteControlProxy remoteControl;
        final GlobalRemoteControlPool pool;

        remoteControl = new RemoteControlProxy("host", 0, "an environment", 1, null);
        pool = new GlobalRemoteControlPool();

        pool.register(remoteControl);
        pool.reserve(new Environment("an environment", ""));
        pool.associateWithSession(remoteControl, "a session id");
        pool.releaseForSession("a session id");

        assertEquals(null, pool.getRemoteControlForSession("a session id"));
    }

    @Test
    public void releaseDelegatesCallToEnvironmentPoolAndForgetsAboutThisSession() {
        final RemoteControlProxy remoteControl;
        final GlobalRemoteControlPool pool;

        remoteControl = new RemoteControlProxy("host", 0, "an environment", 1, null);
        pool = new GlobalRemoteControlPool();

        pool.register(remoteControl);
        pool.reserve(new Environment("an environment", ""));
        pool.release(remoteControl);
        assertEquals(0 , remoteControl.concurrentSesssionCount());
    }

    @Test
    public void getReturnsRemoteControlThatHaveBeenAssociatedWithASession() {
        final RemoteControlProxy remoteControl;
        final GlobalRemoteControlPool pool;
        remoteControl = new RemoteControlProxy("host", 0, "an environment", 1, null);
        pool = new GlobalRemoteControlPool();

        pool.register(remoteControl);
        pool.associateWithSession(remoteControl, "a session id");

        assertEquals(remoteControl, pool.retrieve("a session id"));
    }

    @Test
    public void availableRemoteControlsReturnAnEmptyArrayWhenThereIsNoEnvironment() {
        assertTrue(new GlobalRemoteControlPool().availableRemoteControls().isEmpty());
        verifyMocks();
    }

//    @Test
//    public void availableRemoteControlsReturnAvailableRemoteControlsForAllEnvironments() {
//        final List<RemoteControlProxy> remoteControlsForFirstPool;
//        final List<RemoteControlProxy> remoteControlsForSecondPool;
//        final List<RemoteControlProxy> availableRemoteControls;
//        final EnvironmentManager environmentManager;
//        final Environment firstEnvironment;
//        final Environment secondEnvironment;
//        final Mock firstEnvironmentPool;
//        final Mock secondEnvironmentPool;
//
//        remoteControlsForFirstPool = Arrays.asList(new RemoteControlProxy("first host", 0, "first environment", 1, null));
//        remoteControlsForSecondPool = Arrays.asList(
//                new RemoteControlProxy("second host", 0, "second environment", 1, null),
//                new RemoteControlProxy("third host", 0, "second environment", 1, null)
//        );
//        firstEnvironmentPool = mock(DynamicRemoteControlPool.class);
//        secondEnvironmentPool = mock(DynamicRemoteControlPool.class);
//        firstEnvironment = new Environment("first environment", "*chrome", (DynamicRemoteControlPool) firstEnvironmentPool);
//        secondEnvironment = new Environment("second environment", "*chrome", (DynamicRemoteControlPool) secondEnvironmentPool);
//        environmentManager = new EnvironmentManager();
//        environmentManager.addEnvironment(firstEnvironment);
//        environmentManager.addEnvironment(secondEnvironment);
//
//        firstEnvironmentPool.stubs("availableRemoteControls").will(returnValue(remoteControlsForFirstPool));
//        secondEnvironmentPool.stubs("availableRemoteControls").will(returnValue(remoteControlsForSecondPool));
//
//        availableRemoteControls = new GlobalRemoteControlPool().availableRemoteControls();
//        assertEquals(3, availableRemoteControls.size());
//        assertTrue(availableRemoteControls.containsAll(remoteControlsForFirstPool));
//        assertTrue(availableRemoteControls.containsAll(remoteControlsForSecondPool));
//        verifyMocks();
//    }

    @Test
    public void reservedRemoteControlsReturnAnEmptyArrayWhenThereIsNoEnvironment() {
        assertTrue(new GlobalRemoteControlPool().reservedRemoteControls().isEmpty());
        verifyMocks();
    }

//    @Test
//    public void reservedRemoteControlsReturnAvailableRemoteControlsForAllEnvironments() {
//        final List<RemoteControlProxy> remoteControlsForFirstPool;
//        final List<RemoteControlProxy> remoteControlsForSecondPool;
//        final List<RemoteControlProxy> reservedRemoteControls;
//        final EnvironmentManager environmentManager;
//        final Environment firstEnvironment;
//        final Environment secondEnvironment;
//        final Mock firstEnvironmentPool;
//        final Mock secondEnvironmentPool;
//        GlobalRemoteControlPool pool;
//
//        remoteControlsForFirstPool = Arrays.asList(new RemoteControlProxy("first host", 0, "first environment", 1, null));
//        remoteControlsForSecondPool = Arrays.asList(
//                new RemoteControlProxy("second host", 0, "second environment", 1, null),
//                new RemoteControlProxy("third host", 0, "second environment", 1, null)
//        );
//        firstEnvironmentPool = mock(DynamicRemoteControlPool.class);
//        secondEnvironmentPool = mock(DynamicRemoteControlPool.class);
//        firstEnvironment = new Environment("first environment", "*chrome");
//        secondEnvironment = new Environment("second environment", "*chrome");
//        environmentManager = new EnvironmentManager();
//        environmentManager.addEnvironment(firstEnvironment);
//        environmentManager.addEnvironment(secondEnvironment);
//
//        firstEnvironmentPool.stubs("reservedRemoteControls").will(returnValue(remoteControlsForFirstPool));
//        secondEnvironmentPool.stubs("reservedRemoteControls").will(returnValue(remoteControlsForSecondPool));
//
//        pool = new GlobalRemoteControlPool();
//        pool.add
//        reservedRemoteControls = pool.reservedRemoteControls();
//        assertEquals(3, reservedRemoteControls.size());
//        assertTrue(reservedRemoteControls.containsAll(remoteControlsForFirstPool));
//        assertTrue(reservedRemoteControls.containsAll(remoteControlsForSecondPool));
//        verifyMocks();
//    }

//    @Test
//    public void fixme() {
//        fail("look hard at impl, espcialy sync code");
//    }

    @Test
    public void logSessionMapDoesNotBombWhenThereIsNoSession() {
        final GlobalRemoteControlPool pool;

        pool = new GlobalRemoteControlPool();
        pool.logSessionMap();
    }

    @Test
    public void logSessionMapDoesNotBombWhenThereIsSomeRegisteredSession() {
        final RemoteControlProxy aRemoteControl;
        final RemoteControlProxy anotherRemoteControl;
        final GlobalRemoteControlPool pool;

        aRemoteControl = new RemoteControlProxy("host", 4444, "an environment", 1, null);
        anotherRemoteControl = new RemoteControlProxy("host", 4445, "an environment", 1, null);
        pool = new GlobalRemoteControlPool();

        pool.register(aRemoteControl);
        pool.register(anotherRemoteControl);
        pool.reserve(new Environment("an environment", ""));
        pool.reserve(new Environment("an environment", ""));
        pool.associateWithSession(aRemoteControl, "a session id");
        pool.associateWithSession(anotherRemoteControl, "another session id");
        pool.logSessionMap();
    }

}