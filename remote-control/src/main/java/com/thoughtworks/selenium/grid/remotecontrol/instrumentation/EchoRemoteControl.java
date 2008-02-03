package com.thoughtworks.selenium.grid.remotecontrol.instrumentation;

import com.thoughtworks.selenium.grid.remotecontrol.SelfRegisteringRemoteControl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Fake Selenium Remote Control echoing received request. Useful for debugging and testing purposes. 
 *
 * @author Philippe Hanrigou
 */
public class EchoRemoteControl extends SelfRegisteringRemoteControl {

    private static final Log logger = LogFactory.getLog(SimplisticHttpServer.class);

    public EchoRemoteControl(String seleniumHubURL, String environment, String host, String port) {
        super(seleniumHubURL, environment, host, port);
    }

    public void launch(String[] args) throws Exception {
        new SimplisticHttpServer(5555).start();
    }

    public static void main(String[] args) throws Exception {
            final EchoRemoteControl remoteControl = new EchoRemoteControl("http://localhost:4444", "*chrome", "localhost", "5555");
//            remoteControl.register();
//            remoteControl.ensureUnregisterOnExit();
            remoteControl.launch(new String[0]);
    }

}
