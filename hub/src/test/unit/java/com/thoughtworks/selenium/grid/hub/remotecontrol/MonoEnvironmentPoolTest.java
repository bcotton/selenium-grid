package com.thoughtworks.selenium.grid.hub.remotecontrol;

import static junit.framework.Assert.assertEquals;
import org.jbehave.classmock.UsingClassMock;
import org.jbehave.core.mock.Mock;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;


public class MonoEnvironmentPoolTest extends UsingClassMock {

    @Test
    public void registerAddsTheRemoteControlToTheReservationManager() {
        final Mock reservationManager;
        final RemoteControlProxy remoteControl;
        final MonoEnviromentPool pool;

        remoteControl = new RemoteControlProxy("", 0, "", null);
        reservationManager = mock(RemoteControlProvisioner.class);
        pool = new MonoEnviromentPool((RemoteControlProvisioner) reservationManager);
        reservationManager.expects("add").with(remoteControl);

        pool.register(remoteControl);
        verifyMocks();
    }

    @Test
    public void unregisterRemovesTheRemoteControlToTheReservationManager() {
        final Mock reservationManager;
        final RemoteControlProxy remoteControl;
        final MonoEnviromentPool pool;

        remoteControl = new RemoteControlProxy("", 0, "", null);
        reservationManager = mock(RemoteControlProvisioner.class);
        pool = new MonoEnviromentPool((RemoteControlProvisioner) reservationManager);
        reservationManager.expects("remove").with(remoteControl).will(returnValue(true));

        pool.unregister(remoteControl);
        verifyMocks();
    }

    @Test
    public void unregisterReturnsTheResultOfReservationManagerRemove() {
        final Mock reservationManager;
        final RemoteControlProxy remoteControl;
        final MonoEnviromentPool pool;

        remoteControl = new RemoteControlProxy("", 0, "", null);
        reservationManager = mock(RemoteControlProvisioner.class);
        pool = new MonoEnviromentPool((RemoteControlProvisioner) reservationManager);
        reservationManager.expects("remove").with(remoteControl).will(returnValue(false));

        assertEquals(false, pool.unregister(remoteControl));
        verifyMocks();
    }

    @Test
    public void onceUnregisteredARemoteControlIsNotassociatedWithAnyExistingSession() {
        final Mock reservationManager;
        final RemoteControlProxy remoteControl;
        final MonoEnviromentPool pool;

        remoteControl = new RemoteControlProxy("", 0, "", null);
        reservationManager = mock(RemoteControlProvisioner.class);
        pool = new MonoEnviromentPool((RemoteControlProvisioner) reservationManager);
        reservationManager.expects("remove").with(remoteControl).will(returnValue(true));

        pool.associateWithSession(remoteControl, "a session id");
        pool.unregister(remoteControl);
        assertEquals(null, pool.retrieve("a session id"));
        verifyMocks();
    }

    @Test
    public void reserveReturnsTheRemoteControlReservedByTheReservationManager() {
        final Mock reservationManager;
        final RemoteControlProxy remoteControl;
        final MonoEnviromentPool pool;

        remoteControl = new RemoteControlProxy("", 0, "", null);
        reservationManager = mock(RemoteControlProvisioner.class);
        pool = new MonoEnviromentPool((RemoteControlProvisioner) reservationManager);
        reservationManager.expects("reserve").will(returnValue(remoteControl));

        assertEquals(remoteControl, pool.reserve(null));
        verifyMocks();
    }

    @Test
    public void getRemoteControlReturnsNullWhenNoRemoteControlHasBeenAssociatedWithTheSessionId() {
        assertEquals(null, new MonoEnviromentPool(new RemoteControlProvisioner()).retrieve("unknown session id"));
    }

    @Test
    public void getRemoteControlReturnsTheRemoteControlHasBeenAssociatedWithASpecificSession() {
        final MonoEnviromentPool pool = new MonoEnviromentPool(new RemoteControlProvisioner());
        final RemoteControlProxy remoteControl = new RemoteControlProxy("", 0, "", null);

        pool.associateWithSession(remoteControl, "a session id");
        assertEquals(remoteControl, pool.retrieve("a session id"));
    }

    @Test(expected = IllegalStateException.class)
    public void associateWithSessionThrowsAnIllegalStateExceptionWhenSessionIdIsAlreadyRegistered() {
        final MonoEnviromentPool pool = new MonoEnviromentPool(new RemoteControlProvisioner());

        pool.associateWithSession(new RemoteControlProxy("", 0, "", null), "shared session id");
        pool.associateWithSession(new RemoteControlProxy("", 0, "", null), "shared session id");
    }

    @Test
    public void associateWithSessionKeepsTrackOfEachSessionIndependently() {
        final MonoEnviromentPool pool = new MonoEnviromentPool(new RemoteControlProvisioner());
        final RemoteControlProxy firstRemoteControl = new RemoteControlProxy("", 0, "", null);
        final RemoteControlProxy secondRemoteControl = new RemoteControlProxy("", 0, "", null);

        pool.associateWithSession(firstRemoteControl, "first session id");
        pool.associateWithSession(secondRemoteControl, "second session id");

        assertEquals(firstRemoteControl, pool.retrieve("first session id"));
        assertEquals(secondRemoteControl, pool.retrieve("second session id"));
    }

    @Test
    public void afterReleaseForSessionARemoteControlIsNotAssociatedWithASessionAnymore() {
        final MonoEnviromentPool pool = new MonoEnviromentPool(new RemoteControlProvisioner());
        final RemoteControlProxy remoteControl = new RemoteControlProxy("", 0, "", null);
        pool.associateWithSession(remoteControl, "a session id");

        pool.releaseForSession("a session id");
        assertEquals(null, pool.retrieve("a session id"));
    }

    public void releaseForSessionDoesNotThrowsAnExceptionWhenSessionIsNotAssociated() {
        final MonoEnviromentPool pool = new MonoEnviromentPool(new RemoteControlProvisioner());
        pool.releaseForSession("unknown session id");
    }

    @Test
    public void releaseForSessionReleasesTheRemoteControlOnTheReservationManager() {
        final Mock reservationManager;
        final RemoteControlProxy remoteControl;
        final MonoEnviromentPool pool;

        remoteControl = new RemoteControlProxy("", 0, "", null);
        reservationManager = mock(RemoteControlProvisioner.class);
        pool = new MonoEnviromentPool((RemoteControlProvisioner) reservationManager);
        pool.associateWithSession(remoteControl, "a session id");
        reservationManager.expects("release").with(remoteControl);

        pool.releaseForSession("a session id");
        assertEquals(null, pool.retrieve("a session id"));
        verifyMocks();
    }

    @Test
    public void releaseReleasesTheRemoteControlOnTheReservationManager() {
        final Mock reservationManager;
        final RemoteControlProxy remoteControl;
        final MonoEnviromentPool pool;

        remoteControl = new RemoteControlProxy("", 0, "", null);
        reservationManager = mock(RemoteControlProvisioner.class);
        pool = new MonoEnviromentPool((RemoteControlProvisioner) reservationManager);
        reservationManager.expects("release").with(remoteControl);

        pool.release(remoteControl);
        assertEquals(null, pool.retrieve("a session id"));
        verifyMocks();
    }

    @Test
    public void availableRemoteControlsReturnAvailableRemoteControlsOfTheReservationManager() {
        final Mock reservationManager;
        final List<RemoteControlProxy> expectedList;
        final MonoEnviromentPool pool;

        expectedList = new ArrayList<RemoteControlProxy>();
        reservationManager = mock(RemoteControlProvisioner.class);
        pool = new MonoEnviromentPool((RemoteControlProvisioner) reservationManager);
        reservationManager.expects("availableRemoteControls").will(returnValue(expectedList));

        assertEquals(expectedList, pool.availableRemoteControls());
        verifyMocks();
    }

    @Test
    public void reservedRemoteControlsReturnAvailableRemoteControlsOfTheReservationManager() {
        final Mock reservationManager;
        final List<RemoteControlProxy> expectedList;
        final MonoEnviromentPool pool;

        expectedList = new ArrayList<RemoteControlProxy>();
        reservationManager = mock(RemoteControlProvisioner.class);
        pool = new MonoEnviromentPool((RemoteControlProvisioner) reservationManager);
        reservationManager.expects("reservedRemoteControls").will(returnValue(expectedList));

        assertEquals(expectedList, pool.reservedRemoteControls());
        verifyMocks();
    }

}
