package com.thoughtworks.selenium.grid.hub.remotecontrol;

import com.thoughtworks.selenium.grid.hub.Environment;
import com.thoughtworks.selenium.grid.hub.EnvironmentManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Remote Control Pool aware of requested environment and each remote control capabilities.
 */
public class MultiEnvironmentPool implements DynamicRemoteControlPool {

    private static final Log logger = LogFactory.getLog(MultiEnvironmentPool.class);
    private final EnvironmentManager environmentManager;
    private final Map<String, Environment> sessionIdMap;

    public MultiEnvironmentPool(EnvironmentManager environmentManager) {
        this.environmentManager = environmentManager;
        this.sessionIdMap = new HashMap<String, Environment>();
    }

    public void register(RemoteControlProxy newRemoteControl) {
        getPool(newRemoteControl.environment()).register(newRemoteControl);
    }

    public boolean unregister(RemoteControlProxy remoteControl) {
        return getPool(remoteControl.environment()).unregister(remoteControl);
    }

    public RemoteControlProxy reserve(Environment environment) {
        final RemoteControlPool environmentPool;

        environmentPool = environment.pool();
        return environmentPool.reserve(environment);
    }

    public void associateWithSession(RemoteControlProxy remoteControl, String sessionId) {
        final DynamicRemoteControlPool pool;

        logger.info("Associating session id='" + sessionId + "' =>" + remoteControl
                + " for environment " + remoteControl.environment());
        pool = getPool(remoteControl.environment());
        pool.associateWithSession(remoteControl, sessionId);
        synchronized (sessionIdMap) {
            Environment env = environmentManager.environment(remoteControl.environment());
            sessionIdMap.put(sessionId, env);
        }
    }

    public RemoteControlProxy retrieve(String sessionId) {
        return getPoolForSession(sessionId).retrieve(sessionId);
    }

    public void release(RemoteControlProxy remoteControl) {
        getPool(remoteControl.environment()).release(remoteControl);
    }

    public void releaseForSession(String sessionId) {
        logger.info("Releasing pool for session id='" + sessionId + "'");
        getPoolForSession(sessionId).releaseForSession(sessionId);
        synchronized (sessionIdMap) {
            sessionIdMap.remove(sessionId);
        }
    }

    public List<RemoteControlProxy> availableRemoteControls() {
        final List<RemoteControlProxy> availableRemoteControls;

        availableRemoteControls = new LinkedList<RemoteControlProxy>();
        for (Environment environment : environmentManager.environments()) {
            availableRemoteControls.addAll(environment.pool().availableRemoteControls());
        }

        return availableRemoteControls;
    }

    public List<RemoteControlProxy> reservedRemoteControls() {
        final List<RemoteControlProxy> reservedRemoteControls;

        reservedRemoteControls = new LinkedList<RemoteControlProxy>();
        for (Environment environment : environmentManager.environments()) {
            reservedRemoteControls.addAll(environment.pool().reservedRemoteControls());
        }

        return reservedRemoteControls;
    }

    protected DynamicRemoteControlPool getPool(String environment) {
        return environmentManager.environment(environment).pool();
    }

    public DynamicRemoteControlPool getPoolForSession(String sessionId) {
        synchronized (sessionIdMap) {
            final Environment environment = sessionIdMap.get(sessionId);
            if (null == environment) {
                return null;
            }
            return environment.pool();
        }
    }

}
