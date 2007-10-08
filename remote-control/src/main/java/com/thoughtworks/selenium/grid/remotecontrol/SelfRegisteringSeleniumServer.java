package com.thoughtworks.selenium.grid.remotecontrol;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.openqa.selenium.server.SeleniumServer;

import java.io.IOException;
import java.net.ConnectException;

/**
 * Selenium that registers/unregisters itself to a central Hub when it starts/stops.
 *
 * @author: Philippe Hanrigou
 */
public class SelfRegisteringSeleniumServer {

    private static final Log logger = LogFactory.getLog(SelfRegisteringSeleniumServer.class);
    private final HttpClient httpClient;
    private final String seleniumHubURL;
    private final String environment;
    private final String host;
    private final String port;

    public SelfRegisteringSeleniumServer(String seleniumHubURL, String environment, String host, String port) {
        this.seleniumHubURL = seleniumHubURL;
        this.environment = environment;
        this.host = host;
        this.port = port;
        this.httpClient = new HttpClient();
    }

    public static void main(String[] args) throws Exception {
        final SelfRegisteringSeleniumServer server;
        final OptionParser.Options options;

        options = new OptionParser().parseOptions(args);
        server = new SelfRegisteringSeleniumServer(
                options.hubURL(), options.environment(), options.host(), options.port());
        try {
            server.register();
            ensureUnregisterOnExit(server);
            server.launch(options.seleniumServerArgs());
        } catch (ConnectException e) {
            logger.error("Could not contact the Selenium Hub at '" + server.hubURL() + "' : " + e.getMessage()
                         + ". Check that the Hub is running and check its status at "
                         + server.hubURL() + "/console");
            throw e;
        }
    }

    public void register() throws IOException {
        logger.info("Registering to " + registrationURL());
        int status = httpClient.executeMethod(httpRequest(registrationURL()));
        if (200 != status) {
            throw new IllegalStateException("Could not register successfuly to " + seleniumHubURL
                    + " with environment '" + environment
                    + "'. Most likely this environment is not defined on the hub.");
        }
    }

    public void unregister
            () throws IOException {
        logger.info("Unregistering from " + registrationURL());
        httpClient.executeMethod(httpRequest(unregistrationURL()));
    }

    public void launch
            (String[] args) throws Exception {
        logger.info("Starting selenium server with options:");
        for (String arg : args) {
            logger.info(arg);
        }
        SeleniumServer.main(args);
    }

    protected String hubURL
            () {
        return seleniumHubURL;
    }

    protected String environment
            () {
        return environment;
    }

    protected String host
            () {
        return host;
    }

    protected String port
            () {
        return port;
    }

    protected String registrationURL
            () {
        return seleniumHubURL + "/registration-manager/register";
    }

    protected String unregistrationURL
            () {
        return seleniumHubURL + "/registration-manager/unregister";
    }


    protected PostMethod httpRequest(String targetURL) {
        final PostMethod postMethod;

        postMethod = new PostMethod(targetURL);
        postMethod.addParameter("host", host);
        postMethod.addParameter("port", port);
        postMethod.addParameter("environment", environment);
        return postMethod;
    }

    protected static void ensureUnregisterOnExit
            (
                    final SelfRegisteringSeleniumServer server) {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                try {
                    server.unregister();
                } catch (IOException e) {
                    logger.error("Could not unregister " + server, e);
                }
            }
        });
    }

}
