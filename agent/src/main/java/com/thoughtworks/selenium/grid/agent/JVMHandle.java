package com.thoughtworks.selenium.grid.agent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;

/**
 * Handle to a process started with @{link JVM
 */
public class JVMHandle {
    private final Process process;

    public JVMHandle(Process process) throws IOException {
        this.process = process;
    }

    public int waitForProg(PrintStream outputStream) throws IOException {
        InputStream is = null;
        InputStreamReader isr = null;

        is = process.getInputStream();
        isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);

        String line;        
        while ((line = br.readLine()) != null) {
            outputStream.println(line);
        }

        return process.exitValue();
    }

    public void kill() {
        process.destroy();
    }

    public boolean alive() {
        try {
            process.exitValue();
            return false;
        } catch (IllegalThreadStateException e) {
            return true;
        }
    }

}
