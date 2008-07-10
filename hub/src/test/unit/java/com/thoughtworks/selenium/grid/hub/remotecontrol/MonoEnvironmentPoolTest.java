package com.thoughtworks.selenium.grid.hub.remotecontrol;

import static junit.framework.Assert.assertEquals;
import org.jbehave.classmock.UsingClassMock;
import org.jbehave.core.mock.Mock;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class MonoEnvironmentPoolTest extends UsingClassMock {

    @Test
    public void registerAddsTheRemoteControlToTheProvisioner() {
        final Mock provisioner;
        final RemoteControlProxy remoteControl;
        final MonoEnvironmentPool pool;

        remoteControl = new RemoteControlProxy("", 0, "", 1, null);
        provisioner = mock(RemoteControlProvisioner.class);
        pool = new MonoEnvironmentPool((RemoteControlProvisioner) provisioner);
        provisioner.expects("add").with(remoteControl);

        pool.register(remoteControl);
        verifyMocks();
    }

    @Test
    public void unregisterRemovesTheRemoteControlToTheProvisioner() {
        final Mock provisioner;
        final RemoteControlProxy remoteControl;
        final MonoEnvironmentPool pool;

        remoteControl = new RemoteControlProxy("", 0, "", 1, null);
        provisioner = mock(RemoteControlProvisioner.class);
        pool = new MonoEnvironmentPool((RemoteControlProvisioner) provisioner);
        provisioner.expects("remove").with(remoteControl).will(returnValue(true));

        pool.unregister(remoteControl);
        verifyMocks();
    }

    @Test
    public void unregisterReturnsTheResultOfProvisionerRemove() {
        final Mock provisioner;
        final RemoteControlProxy remoteControl;
        final MonoEnvironmentPool pool;

        remoteControl = new RemoteControlProxy("", 0, "", 1, null);
        provisioner = mock(RemoteControlProvisioner.class);
        pool = new MonoEnvironmentPool((RemoteControlProvisioner) provisioner);
        provisioner.expects("remove").with(remoteControl).will(returnValue(false));

        assertEquals(false, pool.unregister(remoteControl));
        verifyMocks();
    }

    @Test
    public void onceUnregisteredARemoteControlIsNotassociatedWithAnyExistingSession() {
        final Mock provisioner;
        final RemoteControlProxy remoteControl;
        final MonoEnvironmentPool pool;

        remoteControl = new RemoteControlProxy("", 0, "", 1, null);
        provisioner = mock(RemoteControlProvisioner.class);
        pool = new MonoEnvironmentPool((RemoteControlProvisioner) provisioner);
        provisioner.expects("remove").with(remoteControl).will(returnValue(true));

        pool.associateWithSession(remoteControl, "a session id");
        pool.unregister(remoteControl);
        assertEquals(null, pool.retrieve("a session id"));
        verifyMocks();
    }

    @Test
    public void reserveReturnsTheRemoteControlReservedByTheProvisioner() {
        final Mock provisioner;
        final RemoteControlProxy remoteControl;
        final MonoEnvironmentPool pool;

        remoteControl = new RemoteControlProxy("", 0, "", 1, null);
        provisioner = mock(RemoteControlProvisioner.class);
        pool = new MonoEnvironmentPool((RemoteControlProvisioner) provisioner);
        provisioner.expects("reserve").will(returnValue(remoteControl));

        assertEquals(remoteControl, pool.reserve(null));
        verifyMocks();
    }

    @Test
    public void getRemoteControlReturnsNullWhenNoRemoteControlHasBeenAssociatedWithTheSessionId() {
        assertEquals(null, new MonoEnvironmentPool(new RemoteControlProvisioner()).retrieve("unknown session id"));
    }

    @Test
    public void getRemoteControlReturnsTheRemoteControlHasBeenAssociatedWithASpecificSession() {
        final MonoEnvironmentPool pool = new MonoEnvironmentPool(new RemoteControlProvisioner());
        final RemoteControlProxy remoteControl = new RemoteControlProxy("", 0, "", 1, null);

        pool.associateWithSession(remoteControl, "a session id");
        assertEquals(remoteControl, pool.retrieve("a session id"));
    }

    @Test(expected = IllegalStateException.class)
    public void associateWithSessionThrowsAnIllegalStateExceptionWhenSessionIdIsAlreadyRegistered() {
        final MonoEnvironmentPool pool = new MonoEnvironmentPool(new RemoteControlProvisioner());

        pool.associateWithSession(new RemoteControlProxy("", 0, "", 1, null), "shared session id");
        pool.associateWithSession(new RemoteControlProxy("", 0, "", 1, null), "shared session id");
    }

    @Test
    public void associateWithSessionKeepsTrackOfEachSessionIndependently() {
        final MonoEnvironmentPool pool = new MonoEnvironmentPool(new RemoteControlProvisioner());
        final RemoteControlProxy firstRemoteControl = new RemoteControlProxy("", 0, "", 1, null);
        final RemoteControlProxy secondRemoteControl = new RemoteControlProxy("", 0, "", 1, null);

        pool.associateWithSession(firstRemoteControl, "first session id");
        pool.associateWithSession(secondRemoteControl, "second session id");

        assertEquals(firstRemoteControl, pool.retrieve("first session id"));
        assertEquals(secondRemoteControl, pool.retrieve("second session id"));
    }

    @Test
    public void afterReleaseForSessionARemoteControlIsNotAssociatedWithASessionAnymore() {
        final Mock provisioner;
        final RemoteControlProxy remoteControl;
        final MonoEnvironmentPool pool;

        remoteControl = new RemoteControlProxy("", 0, "", 1, null);
        provisioner = mock(RemoteControlProvisioner.class);
        provisioner.expects("reserve").will(returnValue(remoteControl));
        pool = new MonoEnvironmentPool((RemoteControlProvisioner) provisioner);
        pool.reserve(null);
        pool.associateWithSession(remoteControl, "a session id");

        pool.releaseForSession("a session id");
        assertEquals(null, pool.retrieve("a session id"));
    }

    public void releaseForSessionDoesNotThrowsAnExceptionWhenSessionIsNotAssociated() {
        final MonoEnvironmentPool pool = new MonoEnvironmentPool(new RemoteControlProvisioner());
        pool.releaseForSession("unknown session id");
    }

    @Test
    public void releaseForSessionReleasesTheRemoteControlOnTheProvisioner() {
        final Mock provisioner;
        final RemoteControlProxy remoteControl;
        final MonoEnvironmentPool pool;

        remoteControl = new RemoteControlProxy("", 0, "", 1, null);
        provisioner = mock(RemoteControlProvisioner.class);
        pool = new MonoEnvironmentPool((RemoteControlProvisioner) provisioner);
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
        final MonoEnvironmentPool pool;

        remoteControl = new RemoteControlProxy("", 0, "", 1, null);
        provisioner = mock(RemoteControlProvisioner.class);
        pool = new MonoEnvironmentPool((RemoteControlProvisioner) provisioner);
        provisioner.expects("release").with(remoteControl);

        pool.release(remoteControl);
        assertEquals(null, pool.retrieve("a session id"));
        verifyMocks();
    }

    @Test
    public void availableRemoteControlsReturnAvailableRemoteControlsOfTheProvisioner() {
        final Mock provisioner;
        final List<RemoteControlProxy> expectedList;
        final MonoEnvironmentPool pool;

        expectedList = new ArrayList<RemoteControlProxy>();
        provisioner = mock(RemoteControlProvisioner.class);
        pool = new MonoEnvironmentPool((RemoteControlProvisioner) provisioner);
        provisioner.expects("availableRemoteControls").will(returnValue(expectedList));

        assertEquals(expectedList, pool.availableRemoteControls());
        verifyMocks();
    }

    @Test
    public void reservedRemoteControlsReturnAvailableRemoteControlsOfTheProvisioner() {
        final Mock provisioner;
        final List<RemoteControlProxy> expectedList;
        final MonoEnvironmentPool pool;

        expectedList = new ArrayList<RemoteControlProxy>();
        provisioner = mock(RemoteControlProvisioner.class);
        pool = new MonoEnvironmentPool((RemoteControlProvisioner) provisioner);
        provisioner.expects("reservedRemoteControls").will(returnValue(expectedList));

        assertEquals(expectedList, pool.reservedRemoteControls());
        verifyMocks();
    }

}
