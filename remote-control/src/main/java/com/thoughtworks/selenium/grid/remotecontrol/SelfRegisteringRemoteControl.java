package com.thoughtworks.selenium.grid.remotecontrol;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.server.SeleniumServer;

import java.io.IOException;

/*
 * Selenium Remote Control that registers/unregisters itself to a central Hub when it starts/stops.
 *
 * @author Philippe Hanrigou
 */
public class SelfRegisteringRemoteControl {


    private static final Log logger = LogFactory.getLog(SelfRegisteringRemoteControlLauncher.class);
    private final String seleniumHubURL;
    private final String environment;
    private final String host;
    private final String port;

    public SelfRegisteringRemoteControl(String seleniumHubURL, String environment, String host, String port) {
        this.seleniumHubURL = seleniumHubURL;
        this.environment = environment;
        this.host = host;
        this.port = port;
    }

    public void register() throws IOException {
        new RegistrationRequest(seleniumHubURL, host, port, environment).execute();
    }

    public void unregister() throws IOException {
        new UnregistrationRequest(seleniumHubURL, host, port, environment).execute();
    }

    public String hubURL() {
        return seleniumHubURL;
    }

    public String environment() {
        return environment;
    }

    public String host() {
        return host;
    }

    public String port() {
        return port;
    }


    public void launch(String[] args) throws Exception {
        logger.info("Starting selenium server with options:");
        for (String arg : args) {
            logger.info(arg);
        }
        SeleniumServer.main(args);
    }

    protected void ensureUnregisterOnExit() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                try {
                    unregister();
                } catch (IOException e) {
                    logger.error("Could not unregister " + this, e);
                }
            }
        });
    }
}
