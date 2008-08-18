package com.thoughtworks.selenium.grid.agent;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Launch new JVMs running an application packaged as a JAR file.
 */
public class JVMLauncher {

    private final Classpath classpath;
    private final String mainClass;
    
    public JVMLauncher(Classpath classpath, String mainClass) {
        this.classpath = classpath;
        this.mainClass = mainClass;
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

    protected Classpath classpath() {
        return classpath;
    }

    protected List<String> command() {
        final List<String> command;
        
        command = new ArrayList<String>(3);
        command.add(javaLauncherPath());
        command.add("-cp");
        command.add(classpath.toString());
        command.add(mainClass);

        return command;
    }
    
    protected String javaLauncherPath() {
        File javaLauncher;

        javaLauncher = new File(System.getProperty("java.home") + "/bin/java");
        return javaLauncher.getAbsolutePath();
    }
    
}
