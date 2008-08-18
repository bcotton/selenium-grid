package com.thoughtworks.selenium.grid.agent;

import org.junit.Test;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import com.thoughtworks.selenium.grid.HttpParameters;

public class RemoteControlCommandTest {

    @Test
    public void portIs4444WhenNotSpecifiedAsAParameter() {
        final RemoteControlConfiguration configuration;
        
        configuration = new RemoteControlCommand().parseRemoteControlConfiguration(new HttpParameters());
        assertEquals(4444, configuration.port());
    }

    @Test
    public void portIsCanBeSpecifiedAsAParameter() {
        final RemoteControlConfiguration configuration;
        final HttpParameters httpParameters;

        httpParameters = new HttpParameters();
        httpParameters.put("port", "2424");
        configuration = new RemoteControlCommand().parseRemoteControlConfiguration(httpParameters);
        assertEquals(2424, configuration.port());
    }

}
