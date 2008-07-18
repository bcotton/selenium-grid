package com.thoughtworks.selenium.grid.remotecontrol;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * Created by IntelliJ IDEA.
 * User: ph7
 * Date: Jul 15, 2008
 * Time: 12:13:00 AM
 * To change this template use File | Settings | File Templates.
 */
public class JVMHandle {
    private final Process process;

    public JVMHandle(Process process) throws IOException {
        this.process = process;
    }

    public int waitForProg() throws IOException {
        InputStream is = process.getInputStream();
        InputStreamReader isr = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(isr);
        String line;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }
        System.out.println(">>> got " + process.exitValue());
        System.out.println("Program terminated!");

        return process.exitValue();

    }

    public void kill() {
        process.destroy();
    }

}
