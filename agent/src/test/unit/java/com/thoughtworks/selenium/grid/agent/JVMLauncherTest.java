package com.thoughtworks.selenium.grid.agent;

import org.junit.Test;
import org.junit.Assert;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.List;
import java.util.ArrayList;

public class JVMLauncherTest {

    @Test
    public void jarFileIsTheOneProvidedInTheConstructor() {
        assertEquals("/a/file.jar", new JVMLauncher("/a/file.jar").jarFile());
    }

    @Test
    public void javaLauncherPathIsAValidFilePath() {
        assertTrue(new File(new JVMLauncher("").javaLauncherPath()).exists());
    }

    @Test
    public void javaLauncherPathEndsWithJava() {
        assertTrue(new JVMLauncher("").javaLauncherPath().endsWith(File.separator + "java"));
    }

    @Test
    public void commandFirstArgumentIsTheJavaLauncher() {
        final JVMLauncher launcher;

        launcher = new JVMLauncher("") {
            public String javaLauncherPath() {
                return "/somewhere/bin/java";
            }
        };
        assertEquals("/somewhere/bin/java", launcher.command().get(0));
    }

    @Test
    public void commandSecondArgumentIsDashJar() {
        assertEquals("-jar", new JVMLauncher("").command().get(1));
    }

    @Test
    public void commandThirdArgumentIsTheJarFile() {
        assertEquals("/a/file.jar", new JVMLauncher("/a/file.jar").command().get(2));
    }

    @Test
    public void newProcessBuilderRedirectStderrToStdout() {
        assertTrue(new JVMLauncher("").newProcessBuilder().redirectErrorStream());
    }

    @Test
    public void newProcessBuilderCommandIsTheReturnValueOfTheCommandMethod() {
        final List<String> expectedCommand;
        final JVMLauncher launcher;

        expectedCommand = new ArrayList<String>();
        launcher = new JVMLauncher("") {
            protected List<String> command() {
                return expectedCommand;
            }
        };
        assertEquals(expectedCommand, launcher.newProcessBuilder().command());
    }

}
