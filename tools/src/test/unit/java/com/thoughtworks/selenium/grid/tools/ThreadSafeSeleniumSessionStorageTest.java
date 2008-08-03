package com.thoughtworks.selenium.grid.tools;

import org.junit.Test;
public class ThreadSafeSeleniumSessionStorageTest {

    @Test
    public void forConvenienceYouCanCloseTheSessionEvenIfThereIsNoCurrentSession() throws Exception {
        ThreadSafeSeleniumSessionStorage.closeSeleniumSession();
    }
    
}
