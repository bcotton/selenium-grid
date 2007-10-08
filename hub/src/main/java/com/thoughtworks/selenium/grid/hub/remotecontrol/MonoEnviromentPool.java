package com.thoughtworks.selenium.grid.hub.remotecontrol;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import com.thoughtworks.selenium.grid.hub.Environment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Remote Control Pool dedicated to a single environment.
 */
public class MonoEnviromentPool implements DynamicRemoteControlPool {

    private static final Log logger = LogFactory.getLog(MonoEnviromentPool.class);

    private final RemoteControlProvisioner provisioner;
    private final Map<String, RemoteControlProxy> currentSessionMap;


    public MonoEnviromentPool(RemoteControlProvisioner reservationManager) {
        this.provisioner = reservationManager;
        this.currentSessionMap = new HashMap<String, RemoteControlProxy>();
    }

    public void register(RemoteControlProxy newRemoteControl) {
        provisioner.add(newRemoteControl);
    }

    public boolean unregister(RemoteControlProxy remoteControl) {
        final boolean status;

        status = provisioner.remove(remoteControl);
        if (currentSessionMap.containsValue(remoteControl)) {
            removeForSessionMap(remoteControl);
        }
        return status;
    }

    public RemoteControlProxy reserve(Environment environment) {
        return provisioner.reserve();
    }

    public synchronized void associateWithSession(RemoteControlProxy remoteControl, String sessionId) {
        logger.debug("Asssociating " + sessionId + " => " + remoteControl);
        if (currentSessionMap.containsKey(sessionId)) {
            throw new IllegalStateException(
                    "Session '" + sessionId + "' is already asssociated with " + currentSessionMap.get(sessionId));
        }
        synchronized (currentSessionMap) {
            currentSessionMap.put(sessionId, remoteControl);
        }
        if (logger.isDebugEnabled()) {
            logSessionMap();
        }
    }

    public synchronized void releaseForSession(String sessionId) {
        final RemoteControlProxy remoteControl;

        logger.debug("Releasing remote control for session : '" + sessionId + "'");
        synchronized (currentSessionMap) {
            remoteControl = currentSessionMap.remove(sessionId);
        }
        provisioner.release(remoteControl);
    }

    public synchronized void release(RemoteControlProxy remoteControl) {
        provisioner.release(remoteControl);
    }

    public synchronized RemoteControlProxy retrieve(String sessionId) {
        if (logger.isDebugEnabled()) {
            logger.debug("Retrieving remote control for session : '" + sessionId + "'");
            logSessionMap();
        }
        synchronized (currentSessionMap) {
            return currentSessionMap.get(sessionId);
        }

    }

    protected void logSessionMap() {
        for (Map.Entry<String, RemoteControlProxy> entry : currentSessionMap.entrySet()) {
            logger.debug(entry.getKey() + " => " + entry.getValue());
        }
    }

    protected void removeForSessionMap(RemoteControlProxy remoteControl) {
        for (Map.Entry<String, RemoteControlProxy> entry : currentSessionMap.entrySet()) {
            if (entry.getValue().equals(remoteControl)) {
                currentSessionMap.remove(entry.getKey());
            }
        }
    }

    public List<RemoteControlProxy> availableRemoteControls() {
        return provisioner.availableRemoteControls();
    }

    public List<RemoteControlProxy> reservedRemoteControls() {
        return provisioner.reservedRemoteControls();
    }
}

