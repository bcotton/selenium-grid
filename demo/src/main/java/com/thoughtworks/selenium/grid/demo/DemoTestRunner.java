package com.thoughtworks.selenium.grid.demo;

import junit.framework.TestSuite;
import junit.textui.TestRunner;
import org.kohsuke.junit.ParallelTestSuite;

/**
 * Run Selenium tests in sequence or in Parallel
 */
public class DemoTestRunner {

    private final TestSuite testSuite;
    private final String mode;

    public DemoTestRunner(String mode, TestSuite testSuite) {
        this.testSuite = testSuite;
        this.mode = mode;
    }

    public static void main(String[] args) {
        parseOptions(args).run();
    }

    public void run() {
        final long start = System.currentTimeMillis();
        TestRunner.run(testSuite);
        final long end = System.currentTimeMillis();
        System.out.println("Executed tests " + mode + " in " + ((end - start) / 1000.0) + " seconds");
    }

    public void addTestsForMultipleRunsOnTheSameBrowserAndEnvironment() {
        for (int i = 0; i < 4; i++) {
            testSuite.addTest(new MartinOnAmazonSeleniumTest("testAmazonOnFirefox"));
        }
    }

    public void addTestsForMultipleEnvironment() {
        testSuite.addTest(new MartinOnAmazonSeleniumTest("testAmazonOnFirefoxOnWindows"));
        testSuite.addTest(new MartinOnAmazonSeleniumTest("testAmazonOnFirefoxOnWindows"));
        testSuite.addTest(new MartinOnAmazonSeleniumTest("testAmazonOnFirefoxOnMac"));
    }


    protected static DemoTestRunner parseOptions(String args[]) {
        boolean multipleEnvironment = false;
        final DemoTestRunner runner;
        boolean runInParallel = false;

        for (String arg : args) {
            if ("--parallel".equalsIgnoreCase(arg)) {
                runInParallel = true;
            } else if ("--multiple-environments".equalsIgnoreCase(arg)) {
                multipleEnvironment = true;
            }
        }
        if (runInParallel) {
            runner = new DemoTestRunner("parallel", new ParallelTestSuite());
        } else {
            runner = new DemoTestRunner("one by one", new TestSuite());
        }
        if (multipleEnvironment) {
            runner.addTestsForMultipleEnvironment();
        } else {
            runner.addTestsForMultipleRunsOnTheSameBrowserAndEnvironment();
        }
        return runner;
    }

}