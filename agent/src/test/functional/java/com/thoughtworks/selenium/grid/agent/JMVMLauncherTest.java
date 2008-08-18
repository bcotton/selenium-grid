package com.thoughtworks.selenium.grid.agent;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import java.io.IOException;

public class JMVMLauncherTest {
    public static int SECOND = 1000;

    @Test
    public void canLaunchAnAgentAndStopIt() throws IOException, InterruptedException {
        final Classpath classpath;
        final JVMLauncher launcher;
        final JVMHandle handle;

        classpath = new Classpath();
        classpath.add("/Users/ph7/Projects/Selenium Grid/agent/target/dist/lib/selenium-grid-agent-standalone-1.0.1.jar");
        launcher = new JVMLauncher(classpath, "com.thoughtworks.selenium.grid.agent.AgentServer");
        handle = launcher.launchNewJVM();
        assertTrue(handle.alive());
        Thread.sleep(4 * SECOND);
        handle.kill();
        Thread.sleep(1 * SECOND);
        assertFalse(handle.alive());
    }

    
    @Test
    public void canCaptureProcessOutut() throws IOException, InterruptedException { 
        final Classpath classpath;
        final JVMLauncher launcher;
        final JVMHandle handle;

        classpath = new Classpath();
        classpath.add("/Users/ph7/Projects/Selenium Grid/remote-control/lib/selenium-server-1.0-SNAPSHOT.jar");
        classpath.add("/Users/ph7/Projects/Selenium Grid/remote-control/target/dist/lib/selenium-grid-remote-control-standalone-1.0.1.jar");
        launcher = new JVMLauncher(classpath, "com.thoughtworks.selenium.grid.remotecontrol.SelfRegisteringRemoteControlLauncher");
        handle = launcher.launchNewJVM();
        assertTrue(handle.alive());
        handle.waitForProg(System.out);
    }

    
}
