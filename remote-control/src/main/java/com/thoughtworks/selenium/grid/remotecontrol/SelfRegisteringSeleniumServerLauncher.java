package com.thoughtworks.selenium.grid.remotecontrol;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.net.ConnectException;

/**
 * Launch a Self-registering Selenium Remote Control.
 *
 * @author: Philippe Hanrigou
 */
public class SelfRegisteringSeleniumServerLauncher {

    private static final Log logger = LogFactory.getLog(SelfRegisteringSeleniumServerLauncher.class);


    public static void main(String[] args) throws Exception {
        final SelfRegisteringSeleniumServer server;
        final OptionParser.Options options;

        options = new OptionParser().parseOptions(args);
        server = new SelfRegisteringSeleniumServer(
                options.hubURL(), options.environment(), options.host(), options.port());
        try {
            server.register();
            server.ensureUnregisterOnExit();
            server.launch(options.seleniumServerArgs());
        } catch (ConnectException e) {
            logger.error("Could not contact the Selenium Hub at '" + server.hubURL() + "' : " + e.getMessage()
                    + ". Check that the Hub is running and check its status at "
                    + server.hubURL() + "/console");
            throw e;
        }
    }

}
