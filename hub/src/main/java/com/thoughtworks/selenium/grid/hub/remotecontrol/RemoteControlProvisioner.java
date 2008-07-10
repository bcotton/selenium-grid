package com.thoughtworks.selenium.grid.hub.remotecontrol;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Central authority to track registered remote controls and grant exclusive access
 * to a remote control for a while.
 *
 * A client will block if it attempts to reserve a remote control and none is available.
 * The call will return as soon as a remote control becomes available again.
 */
public class RemoteControlProvisioner {

    private static final Log LOGGER = LogFactory.getLog(RemoteControlProvisioner.class);
    private final List<RemoteControlProxy> remoteControls;
    private final Lock remoteControlListLock;
    private final Condition remoteControlAvailable;


    public RemoteControlProvisioner() {
        remoteControls = new LinkedList<RemoteControlProxy>();
        remoteControlListLock = new ReentrantLock();
        remoteControlAvailable = remoteControlListLock.newCondition();
    }

    public RemoteControlProxy reserve() {
        final RemoteControlProxy remoteControl;

        try {
            remoteControlListLock.lock();

            if (remoteControls.isEmpty()) {
                return null;
            }

            remoteControl = blockUntilARemoteControlIsAvailable();
            remoteControl.registerNewSession();
            LOGGER.info("Reserved remote control" + remoteControl);
            return remoteControl;
        } finally {
            remoteControlListLock.unlock();
        }
    }

    public void release(RemoteControlProxy remoteControl) {
        try {
            remoteControlListLock.lock();
            remoteControl.unregisterSession();
            LOGGER.info("Released remote control" + remoteControl);
            signalThatARemoteControlHasBeenMadeAvailable();
        } finally {
            remoteControlListLock.unlock();
        }
    }

    public void add(RemoteControlProxy newRemoteControl) {
        try {
            remoteControlListLock.lock();
            if (remoteControls.contains(newRemoteControl)) {
                throw new IllegalStateException("attempting to add a remote control that is already registered: " + newRemoteControl);
            }
            remoteControls.add(newRemoteControl);
            signalThatARemoteControlHasBeenMadeAvailable();
        } finally {
            remoteControlListLock.unlock();
        }
    }

    public boolean remove(RemoteControlProxy remoteControl) {
        try {
            remoteControlListLock.lock();
            return remoteControls.remove(remoteControl);
        } finally {
            remoteControlListLock.unlock();
        }
    }

    /**
     * Not thread safe.
     *
     * @return All available remote controls. Never null.
     */
    public List<RemoteControlProxy> availableRemoteControls() {
        LinkedList<RemoteControlProxy> availableremoteControls;

        availableremoteControls = new LinkedList<RemoteControlProxy>();
        for (RemoteControlProxy remoteControl : remoteControls) {
            if (remoteControl.canHandleNewSession()) {
                availableremoteControls.add(remoteControl);
            }
        }
        return Arrays.asList(availableremoteControls.toArray(new RemoteControlProxy[availableremoteControls.size()]));
    }

    /**
     * Not thread safe.
     *
     * @return All reserved remote controls. Never null.
     */
    public List<RemoteControlProxy> reservedRemoteControls() {
        LinkedList<RemoteControlProxy> reservedRemoteControls;

        reservedRemoteControls = new LinkedList<RemoteControlProxy>();
        for (RemoteControlProxy remoteControl : remoteControls) {
            if (remoteControl.concurrentSesssionCount() >= 1) {
                reservedRemoteControls.add(remoteControl);
            }
        }
        return Arrays.asList(reservedRemoteControls.toArray(new RemoteControlProxy[reservedRemoteControls.size()]));
    }

    protected RemoteControlProxy blockUntilARemoteControlIsAvailable() {
        RemoteControlProxy availableRemoteControl;

        while(true) {
            try {
                availableRemoteControl = findNextAvailableRemoteControl();
                while (null == availableRemoteControl) {
                    LOGGER.info("Waiting for an remote control...");
                    waitForARemoteControlToBeAvailable();
                    availableRemoteControl = findNextAvailableRemoteControl();
                }
                return availableRemoteControl;
            } catch (InterruptedException e) {
                LOGGER.error("Interrupted while reserving remote control", e);
            }
        }
    }

    /**
     * Non-blocking, not thread-safe
     *
     * @return Next Available remote control. Null if none is available.
     */
    protected RemoteControlProxy findNextAvailableRemoteControl() {
        for (RemoteControlProxy remoteControl : remoteControls) {
            if (remoteControl.canHandleNewSession()) {
                return remoteControl;
            }
        }
        return null;
    }

    protected void waitForARemoteControlToBeAvailable() throws InterruptedException {
        remoteControlAvailable.await();
    }

    protected void signalThatARemoteControlHasBeenMadeAvailable() {
        remoteControlAvailable.signalAll();
    }

    
}
