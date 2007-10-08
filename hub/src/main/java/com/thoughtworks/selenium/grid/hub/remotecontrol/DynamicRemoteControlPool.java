package com.thoughtworks.selenium.grid.hub.remotecontrol;

import java.util.List;

/**
 * Remote control pool that grows/shrinks when remote control
 * register/unregister themselves.
 */
public interface DynamicRemoteControlPool extends RemoteControlPool {

    void register(RemoteControlProxy newRemoteControl);

    boolean unregister(RemoteControlProxy remoteControl);

    public List<RemoteControlProxy> availableRemoteControls();

    public List<RemoteControlProxy> reservedRemoteControls();

}
