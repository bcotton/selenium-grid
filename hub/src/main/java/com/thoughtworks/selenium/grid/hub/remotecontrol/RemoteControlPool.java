package com.thoughtworks.selenium.grid.hub.remotecontrol;

import com.thoughtworks.selenium.grid.hub.Environment;

/**
 * Pool of remote controls that can be used to process Selenese requests.
 */
public interface RemoteControlPool {

    /**
     * Gain exclusive access to a remote control it is released.
     *
     * @param environment  Environment that the remote control must provide. Should not be null.
     * @return Reserved remote control. Never null.
     * @see com.thoughtworks.selenium.grid.hub.remotecontrol.RemoteControlPool#release(RemoteControlProxy)
     * @see com.thoughtworks.selenium.grid.hub.remotecontrol.RemoteControlPool#releaseForSession(String)
     */
    RemoteControlProxy reserve(Environment environment);

    /**
     * Associates a reserved remote control with a Selenese session id. Once associated
     * the remote control can easily be retrieved by session id.
     *
     * @param remoteControl Reserved remote control to be associated with session. Should not be null.
     * @param sessionId     Id of the session to associate the remote control with. Should not be null.
     * @see com.thoughtworks.selenium.grid.hub.remotecontrol.RemoteControlPool#retrieve(String)
     */
    void associateWithSession(RemoteControlProxy remoteControl, String sessionId);

    /**
     * Returns the remote control associated with a specific Selenese session.
     *
     * @param sessionId  Id of the Selenese session. Should not be null.
     * @return Associated remote control.
     */
    RemoteControlProxy retrieve(String sessionId);

    /**
     * Release a remote control that was previously reserved, so it can be used to serve other
     * Selenese sessions.
     *
     * @param remoteControl  Previously reserved remote control. Should not be null.
     */
    void release(RemoteControlProxy remoteControl);

    /**
     * Release a remote control that was previously reserved, so it can be used to serve other
     * Selenese sessions.
     *
     * @param sessionId  Id of the selenese session the remote control is associated with. Should not be null.
     */
    void releaseForSession(String sessionId);

}
