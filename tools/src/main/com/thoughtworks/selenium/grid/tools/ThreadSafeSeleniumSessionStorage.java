package com.thoughtworks.selenium.grid.tools;

import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Utility class making it easy to write tests based on Selenium driver in a multi-thread context.
 *
 * @author Philippe Hanrigou
 */
public class ThreadSafeSeleniumSessionStorage {

    private final static Log logger = LogFactory.getLog(ThreadSafeSeleniumSessionStorage.class);

    /** Thread local Selenium driver instance so that we can run in multi-threaded mode. */
    static ThreadLocal<Selenium> threadLocalSelenium = new ThreadLocal<Selenium>();


    public static void startSeleniumSession(String seleniumHost, int seleniumPort, String browser, String webSite) {
        logger.info("Contacting Selenium RC at " + seleniumHost + ":" + seleniumPort);
        threadLocalSelenium.set(new DefaultSelenium(seleniumHost, seleniumPort, browser, webSite));
        session().start();
    }

    public static void closeSeleniumSession() throws Exception {
        if (null != session()) {
            session().stop();
            resetSession();
        }
    }

    public static Selenium session() {
      return threadLocalSelenium.get();
    }

    
    public static void resetSession() {
      threadLocalSelenium.set(null);
    }

}