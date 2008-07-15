package com.thoughtworks.selenium.grid.hub.remotecontrol;

import com.thoughtworks.selenium.grid.hub.Environment;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Monolithic Remote Control Pool keeping track of all environment and all sessions.
 */
public class GlobalRemoteControlPool implements DynamicRemoteControlPool {

    private static final Log LOGGER = LogFactory.getLog(GlobalRemoteControlPool.class);
    private final Map<String, RemoteControlProxy> remoteControlsBySessionIds;
    private final Map<String, RemoteControlProvisioner> provisionersByEnvironment;

    public GlobalRemoteControlPool() {
        this.remoteControlsBySessionIds = new HashMap<String, RemoteControlProxy>();
        this.provisionersByEnvironment = new HashMap<String, RemoteControlProvisioner>();
    }

    public void register(RemoteControlProxy newRemoteControl) {
        final RemoteControlProvisioner provisioner;

        synchronized(provisionersByEnvironment) {
            if (null == getProvisioner(newRemoteControl.environment())) {
                createNewProvisionerForEnvironment(newRemoteControl.environment());
            }
            provisioner = getProvisioner(newRemoteControl.environment());
            provisioner.add(newRemoteControl);
        }
    }

    public boolean unregister(RemoteControlProxy remoteControl) {
        final boolean status;

        synchronized(provisionersByEnvironment) {
            synchronized (remoteControlsBySessionIds) {
                status = getProvisioner(remoteControl.environment()).remove(remoteControl);
                if (remoteControlsBySessionIds.containsValue(remoteControl)) {
                    removeFromSessionMap(remoteControl);
                }
            }
        }
        return status;
    }

    public RemoteControlProxy reserve(Environment environment) {
        return getProvisioner(environment.name()).reserve();
    }

    public void associateWithSession(RemoteControlProxy remoteControl, String sessionId) {
        LOGGER.info("Associating session id='" + sessionId + "' =>" + remoteControl
                    + " for environment " + remoteControl.environment());
        if (LOGGER.isDebugEnabled()) {
            LOGGER.debug("Asssociating " + sessionId + " => " + remoteControl);
        }
        synchronized (remoteControlsBySessionIds) {
            if (remoteControlsBySessionIds.containsKey(sessionId)) {
                throw new IllegalStateException(
                        "Session '" + sessionId + "' is already asssociated with " + remoteControlsBySessionIds.get(sessionId));
            }
            synchronized (remoteControlsBySessionIds) {
                remoteControlsBySessionIds.put(sessionId, remoteControl);
            }
        }
        if (LOGGER.isDebugEnabled()) {
            logSessionMap();
        }
    }

    public RemoteControlProxy retrieve(String sessionId) {
        return getRemoteControlForSession(sessionId);
    }

    public void release(RemoteControlProxy remoteControl) {
        getProvisioner(remoteControl.environment()).release(remoteControl);
    }

    public void releaseForSession(String sessionId) {
        LOGGER.info("Releasing pool for session id='" + sessionId + "'");

        final RemoteControlProxy remoteControl;
        remoteControl = getRemoteControlForSession(sessionId);

        synchronized (remoteControlsBySessionIds) {
            remoteControlsBySessionIds.remove(sessionId);
        }
        getProvisioner(remoteControl.environment()).release(remoteControl);
    }

    public List<RemoteControlProxy> availableRemoteControls() {
        final List<RemoteControlProxy> availableRemoteControls;

        availableRemoteControls = new LinkedList<RemoteControlProxy>();
        for (RemoteControlProvisioner provisioner : provisionersByEnvironment.values()) {
            availableRemoteControls.addAll(provisioner.availableRemoteControls());
        }

        return availableRemoteControls;
    }

    public List<RemoteControlProxy> reservedRemoteControls() {
        final List<RemoteControlProxy> reservedRemoteControls;

        reservedRemoteControls = new LinkedList<RemoteControlProxy>();
        for (RemoteControlProvisioner provisioner : provisionersByEnvironment.values()) {
            reservedRemoteControls.addAll(provisioner.reservedRemoteControls());
        }

        return reservedRemoteControls;
    }

    protected RemoteControlProvisioner getProvisioner(String environment) {
        return provisionersByEnvironment.get(environment);
    }

    protected RemoteControlProxy getRemoteControlForSession(String sessionId) {
        return remoteControlsBySessionIds.get(sessionId);
    }

    protected void removeFromSessionMap(RemoteControlProxy remoteControl) {
        for (Map.Entry<String, RemoteControlProxy> entry : remoteControlsBySessionIds.entrySet()) {
            if (entry.getValue().equals(remoteControl)) {
                remoteControlsBySessionIds.remove(entry.getKey());
            }
        }
    }

    protected void logSessionMap() {
        for (Map.Entry<String, RemoteControlProxy> entry : remoteControlsBySessionIds.entrySet()) {
            LOGGER.debug(entry.getKey() + " => " + entry.getValue());
        }
    }

    protected void createNewProvisionerForEnvironment(String environemntName) {
        provisionersByEnvironment.put(environemntName, new RemoteControlProvisioner());
    }

}
