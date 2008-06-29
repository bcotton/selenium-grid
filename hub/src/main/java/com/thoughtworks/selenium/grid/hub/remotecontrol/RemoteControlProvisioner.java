package com.thoughtworks.selenium.grid.hub.remotecontrol;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Central authority to track registered remote controls and grant exclusive access
 * to a remote control for a while.
 *
 * A client will block if it attempts to reserve a remote control and none is available.
 * The call will return as soon as a remote control becomes available again.
 */
public class RemoteControlProvisioner {

    private static final Log logger = LogFactory.getLog(RemoteControlProvisioner.class);
    private final BlockingQueue<RemoteControlProxy> availableRemoteControls;
    private final List<RemoteControlProxy> reservedRemoteControls;


    public RemoteControlProvisioner() {
        availableRemoteControls = new LinkedBlockingQueue<RemoteControlProxy>();
        reservedRemoteControls = new LinkedList<RemoteControlProxy>();
    }

    public RemoteControlProxy reserve() {
        final RemoteControlProxy availableRemoteControl;

        if (availableRemoteControls.isEmpty() && reservedRemoteControls.isEmpty()) {
            return null;
        }
        try {
            availableRemoteControl = availableRemoteControls.take();
            synchronized (reservedRemoteControls) {
                reservedRemoteControls.add(availableRemoteControl);
            }
            logger.info("Reserved remote control" + availableRemoteControl);

            return availableRemoteControl;
        } catch (InterruptedException e) {
            logger.error("Interrupted while reserving remote control", e);
            return null;
        }
    }

    public void release(RemoteControlProxy remoteControl) {
        synchronized (reservedRemoteControls) {
            if (reservedRemoteControls.remove(remoteControl)) {
                availableRemoteControls.offer(remoteControl);
                logger.info("Released remote control" + remoteControl);
            }
        }
    }

    public void add(RemoteControlProxy newRemoteControl) {
        synchronized (availableRemoteControls) {
            availableRemoteControls.add(newRemoteControl);
        }
    }

    public boolean remove(RemoteControlProxy remoteControl) {
        synchronized (availableRemoteControls) {
            synchronized (reservedRemoteControls) {
                if (reservedRemoteControls.remove(remoteControl)) {
                    return true;
                } else if (availableRemoteControls.remove(remoteControl)) {
                    return true;
                }
                return false;
            }
        }
    }

    /** Not thread safe. */
    public List<RemoteControlProxy> availableRemoteControls() {
        return Arrays.asList(availableRemoteControls.toArray(new RemoteControlProxy[availableRemoteControls.size()]));
    }

    /** Not thread safe. */
    public List<RemoteControlProxy> reservedRemoteControls() {
        return Arrays.asList(reservedRemoteControls.toArray(new RemoteControlProxy[reservedRemoteControls.size()]));
    }

}
