package com.thoughtworks.selenium.grid.hub;

import static junit.framework.Assert.fail;

/**
 * Execute a block of code in a new thread after expiration of the delay specified in the constructor.
 * Useful for simple testing of multi-threaded code.
 */
public abstract class ConcurrentAction {

    public static final int ONE_SECOND_DELAY = 1;
    public static final int MILLISECONDS = 1000;

    /**
     * Execute a block of code in a new thread after expiration of a delay of 1 second.
     */
    public ConcurrentAction() {
        this(ONE_SECOND_DELAY);
    }

    /**
     * Execute a block of code in a new thread after expiration of the delay specified in the constructor.
     *
     * @param delayInSeconds  Minimum sleep time before executing the block of code.
     */
    public ConcurrentAction(final int delayInSeconds) {
        new Thread() {
            public void run() {
                try {
                    sleep(delayInSeconds * MILLISECONDS);
                } catch (InterruptedException e) {
                    fail("Unexpected state");
                }
                execute();
            }
        }.start();
    }

    /**
     * Block of code to execute in the new thread.
     */
    public abstract void execute();
    
}
