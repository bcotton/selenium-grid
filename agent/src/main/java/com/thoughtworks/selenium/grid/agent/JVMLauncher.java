package com.thoughtworks.selenium.grid.agent;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Launch a new JVM
 */
public class JVMLauncher {

    private final String jarFile;

    public JVMLauncher(String jarFile) {
        this.jarFile = jarFile;
    }

    public static void main(String args[]) throws InterruptedException, IOException {
        new JVMLauncher("remote-control/lib/selenium-server-1.0-SNAPSHOT.jar").launchNewJVM().waitForProg();
    }

    public JVMHandle launchNewJVM() throws IOException, InterruptedException {
        List<String> command = new ArrayList<String>();
        command.add(javaLauncherPath());
        command.add("-jar");
        command.add(jarFile);

        ProcessBuilder builder = new ProcessBuilder(command);
        Map<String, String> environ = builder.environment();
        builder.directory(new File("/Users/ph7/Projects/Selenium Grid/trunk"));
        builder.redirectErrorStream(true);

        final Process process = builder.start();
        return new JVMHandle(process);
    }


    public String javaLauncherPath() {
        File javaLauncher;

        javaLauncher = new File(System.getProperty("java.home") + "/bin/java");
        return javaLauncher.getAbsolutePath();
    }
    
}
