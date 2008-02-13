package com.thoughtworks.selenium.grid.hub;

import com.thoughtworks.selenium.grid.hub.remotecontrol.commands.NewBrowserSessionCommand;
import com.thoughtworks.selenium.grid.hub.remotecontrol.commands.SeleneseCommand;
import com.thoughtworks.selenium.grid.hub.remotecontrol.commands.TestCompleteCommand;
import com.thoughtworks.selenium.grid.HttpParameters;

/**
 * Parse HTTP commands targeting a Remote Control
 *
 * @author: Philippe Hanrigou
 */
public class HttpCommandParser {

    public static final String NEW_BROWSER_SESSION = "getNewBrowserSession";
    private static final String TEST_COMPLETE = "testComplete";
    private final HttpParameters parameters;

    public HttpCommandParser(HttpParameters parameters) {
        this.parameters = parameters;
    }

    public SeleneseCommand parse(EnvironmentManager environmentManager) {
        final String command = parameters.get("cmd");
        if (command.equals(NEW_BROWSER_SESSION)) {
            final Environment environment;
            final String environmentName;

            environmentName = parameters.get("1");
            environment = environmentManager.environment(environmentName);
            if (null == environment) {
                throw new CommandParsingException("ERROR: Unknown environment '" + environmentName + "'");
            }
            parameters.put("1", environment.browser());
            return new NewBrowserSessionCommand(environment, parameters);
        } else if (command.equals(TEST_COMPLETE)) {
            return new TestCompleteCommand(retrieveSessionId(parameters), parameters);
        } else {
            return new SeleneseCommand(retrieveSessionId(parameters), parameters);
        }
    }

    public HttpParameters parameters() {
        return parameters;
    }

    protected String retrieveSessionId(HttpParameters parameters) {
        final String sessionId = parameters.get("sessionId");
        if (null == sessionId) {
            throw new CommandParsingException("ERROR: No sessionId provided. Most likely your original newBrowserSession command failed.");
        }
        return sessionId;
    }
}
