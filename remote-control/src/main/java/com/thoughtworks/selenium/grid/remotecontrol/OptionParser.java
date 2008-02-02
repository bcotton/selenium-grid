package com.thoughtworks.selenium.grid.remotecontrol;

import java.util.ArrayList;
import java.util.List;

/**
 * Basic option parser for Self-Registering Selenium Remote Control.
 *
 * @author: Philippe Hanrigou
 */
public class OptionParser {

    public static class Options {

        private String host;
        private String port;
        private String environment;
        private String hubURL;
        private List<String> seleniumServerOptions;

        protected Options() {
            this.host = "localhost";
            this.port = "5555";
            this.environment = "*chrome";
            this.hubURL = "http://localhost:4444";
            this.seleniumServerOptions = new ArrayList<String>(10);
        }

        public String host() {
            return host;
        }

        public String port() {
            return port;
        }

        public String environment() {
            return environment;
        }

        public String hubURL() {
            return hubURL;
        }

        public List<String> seleniumServerOptions() {
            return seleniumServerOptions;
        }

        public void setHost(String host) {
            this.host = host;
        }

        public void setPort(String port) {
            this.port = port;
        }

        public void setEnvironment(String environment) {
            this.environment = environment;
        }

        public void setHubURL(String hubURL) {
            this.hubURL = hubURL;
        }

        public String[] seleniumServerArgs() {
            return seleniumServerOptions.toArray(new String[seleniumServerOptions.size()]);
        }
    }

    protected Options parseOptions(String[] args) {
        final Options options = new Options();

        for (int i = 0; i < args.length; i++) {
            if ("--help".equalsIgnoreCase(args[i])) {
                usage(null);
                System.exit(1);
            } else if ("-host".equalsIgnoreCase(args[i])) {
                options.setHost(args[++i]);
            } else if ("-port".equalsIgnoreCase(args[i])) {
                options.setPort(args[++i]);
            } else if ("-env".equalsIgnoreCase(args[i])) {
                options.setEnvironment(args[++i]);
            } else if ("-hubURL".equalsIgnoreCase(args[i])) {
                options.setHubURL(args[++i]);
            } else {
                options.seleniumServerOptions.add(args[i]);
            }
        }

        options.seleniumServerOptions.add("-port");
        options.seleniumServerOptions.add(options.port);

        return options;
    }

    protected void usage(String msg) {
        if (msg != null) {
            println(msg + ":");
        }
        println("Usage: java -jar selenium-grid-remote-control*.jar -host <host> -port <port> -env <environment> -hubURL <url> [options]\n");
        println("-port <nnnn>: the port number the selenium server should use (default 5555)");
        println("-host <hostname>: hostname of the machine the selenium server is launched on (default localhost)");
        println("-env <environment>: environment offered by this selenium server (default *chrome)");
        println("-hubURL <url>: base url of the central Hub to register to (default http://localhost:4444)");
    }

    protected void println(String msg) {
        System.err.println(msg);
    }

}
