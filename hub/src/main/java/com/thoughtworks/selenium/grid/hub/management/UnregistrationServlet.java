package com.thoughtworks.selenium.grid.hub.management;

import com.thoughtworks.selenium.grid.hub.remotecontrol.DynamicRemoteControlPool;
import com.thoughtworks.selenium.grid.hub.remotecontrol.RemoteControlProxy;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet used by Selenium Remote Controls to unregister themselves to the Hub.
 */
public class UnregistrationServlet extends RegistrationManagementServlet {

    private static final Log logger = LogFactory.getLog(UnregistrationServlet.class);

    protected void process(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final RemoteControlProxy newRemoteControl;
        final DynamicRemoteControlPool pool;

        logger.info("Unregistering remote control...");
        newRemoteControl = RemoteControlParser.parse(request);
        pool = registry().remoteControlPool();
        pool.unregister(newRemoteControl);
        logger.info("Unregistered " + newRemoteControl);
        writeSuccessfulResponse(response);
    }

}
