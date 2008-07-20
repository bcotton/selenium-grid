package com.thoughtworks.selenium.grid.agent;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import java.io.IOException;

public class JMVMLauncherTest {
    public static int SECOND = 1000;

    @Test
    public void canLaunchAnAgentAndStopIt() throws IOException, InterruptedException {
        final JVMLauncher launcher;
        final JVMHandle handle;

        launcher = new JVMLauncher("/Users/ph7/Projects/Selenium Grid/trunk/agent/target/dist/lib/selenium-grid-agent-standalone-1.0.1.jar");
        handle = launcher.launchNewJVM();
        assertTrue(handle.alive());
        Thread.sleep(4 * SECOND);
        handle.kill();
        Thread.sleep(1 * SECOND);
        assertFalse(handle.alive());
    }

    @Test
    public void canCaptureProcessOutut() throws IOException, InterruptedException { 
        new JVMLauncher("remote-control/lib/selenium-server-1.0-SNAPSHOT.jar").launchNewJVM().waitForProg(System.out);
    }

}
