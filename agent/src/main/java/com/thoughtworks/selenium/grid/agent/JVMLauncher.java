package com.thoughtworks.selenium.grid.agent;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Launch new JVMs running an application packaged as a JAR file.
 */
public class JVMLauncher {

    private final String jarFile;
    
    public JVMLauncher(String jarFile) {
        this.jarFile = jarFile;
    }

    public JVMHandle launchNewJVM() throws IOException, InterruptedException {
        return new JVMHandle(newProcessBuilder().start());
    }

    protected ProcessBuilder newProcessBuilder() {
        final ProcessBuilder builder;
        
        builder = new ProcessBuilder(command());
        builder.redirectErrorStream(true);
        
        return builder;
    }

    protected String jarFile() {
        return jarFile;
    }

    protected List<String> command() {
        final List<String> command;
        
        command = new ArrayList<String>(3);
        command.add(javaLauncherPath());
        command.add("-jar");
        command.add(jarFile);

        return command;
    }
    
    protected String javaLauncherPath() {
        File javaLauncher;

        javaLauncher = new File(System.getProperty("java.home") + "/bin/java");
        return javaLauncher.getAbsolutePath();
    }
    
}
