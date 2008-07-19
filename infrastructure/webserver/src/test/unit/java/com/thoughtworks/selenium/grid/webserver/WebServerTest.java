package com.thoughtworks.selenium.grid.webserver;

import org.jbehave.classmock.UsingClassMock;
import org.jbehave.core.mock.Mock;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;
import org.mortbay.jetty.Server;

public class WebServerTest extends UsingClassMock {


    @Test
    public void portIsTheOneGivenInTheConstructor() {
        assertEquals(24, new WebServer(24, null).port());
    }

    @Test
    public void routeResolverIsTheOneGivenInTheConstructor() {
        assertEquals(String.class, new WebServer(0, String.class).routeResolverClass());
    }


    @Test
    public void waitForShutdownJoinsOnTheJettyServer() throws Exception {
        final WebServer webServer;
        final Mock jettyServer;

        jettyServer = mock(Server.class);
        jettyServer.expects("join");

        webServer = new WebServer(0, null) {
            protected Server httpServer() {
                return (Server) jettyServer;
            }
        };

        webServer.waitForShutdown();
        jettyServer.verify();
    }

    @Test
    public void startListeningForIncomingRequestsStartsTheJettyServer() throws Exception {
        final BooleanHolder jettyServerStarted;
        final WebServer webServer;

        /*
         * Due to some classic Java paranoia, Jetty's start method is final,
         * so we have to resort to a convoluted testing strategy.
         */
        jettyServerStarted = new BooleanHolder();
        webServer = new WebServer(0, null) {
            protected Server httpServer() {
                return new Server() {
                    protected void doStart() throws Exception {
                        jettyServerStarted.changeTo(true);
                    }
                };
            }
        };

        webServer.startListeningForIncomingRequests();
        assertTrue("Did not start the Jetty server as expected", jettyServerStarted.value());
    }

    /*
     * Class holding a boolean. Useful to pass as a final reference to inner classes
     * so that they can modify the encapsulated boolean value.
     */
    public static class BooleanHolder {
        private boolean isTrue;

        public boolean value() {
            return isTrue;
        }

        public void changeTo(boolean newValue) {
            isTrue = newValue;
        }
    }

}
