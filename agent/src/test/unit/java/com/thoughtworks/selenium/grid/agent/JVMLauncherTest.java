package com.thoughtworks.selenium.grid.agent;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class JVMLauncherTest {

    @Test
    public void classpathIsTheOneProvidedInTheConstructor() {
        final Classpath theClasspath;

        theClasspath = new Classpath();
        assertEquals(theClasspath, new JVMLauncher(theClasspath, "").classpath());
    }

    @Test
    public void javaLauncherPathIsAValidFilePath() {
        final String javaLauncherPath;

        javaLauncherPath = new JVMLauncher(new Classpath(), "").javaLauncherPath();
        assertTrue(javaLauncherPath,  new File(javaLauncherPath).exists());
    }

    @Test
    public void javaLauncherPathEndsWithJavaExecutable() {
        final String launcherPath;

        launcherPath = new JVMLauncher(new Classpath(), "").javaLauncherPath();
        if (-1 != System.getProperty("os.name").indexOf("Windows")) {
            assertTrue(launcherPath, launcherPath.endsWith(File.separator + "java.exe"));
        } else {
            assertTrue(launcherPath, launcherPath.endsWith(File.separator + "java"));
        }
    }

    @Test
    public void commandFirstArgumentIsTheJavaLauncher() {
        final JVMLauncher launcher;

        launcher = new JVMLauncher(new Classpath(), "") {
            public String javaLauncherPath() {
                return "/somewhere/bin/java";
            }
        };
        assertEquals("/somewhere/bin/java", launcher.command().get(0));
    }

    @Test
    public void commandSecondArgumentIsTheClasspathOption() {
        assertEquals("-cp", new JVMLauncher(new Classpath(), "").command().get(1));
    }

    @Test
    public void commandThirdArgumentIsTheQuotedClasspathString() {
        final Classpath classpath;

        classpath = new Classpath() {
            public String toString() {
                return "expected classpath";
            }
        };
        assertEquals("expected classpath", new JVMLauncher(classpath, "").command().get(2));
    }

    @Test
    public void commandForthArgumentIsTheMainClass() {
        assertEquals("the.main.Class",
                     new JVMLauncher(new Classpath(), "the.main.Class").command().get(3));
    }


    @Test
    public void newProcessBuilderRedirectStderrToStdout() {
        assertTrue(new JVMLauncher(new Classpath(), "").newProcessBuilder().redirectErrorStream());
    }

    @Test
    public void newProcessBuilderCommandIsTheReturnValueOfTheCommandMethod() {
        final List<String> expectedCommand;
        final JVMLauncher launcher;

        expectedCommand = new ArrayList<String>();
        launcher = new JVMLauncher(new Classpath(), "") {
            protected List<String> command() {
                return expectedCommand;
            }
        };
        assertEquals(expectedCommand, launcher.newProcessBuilder().command());
    }

}
