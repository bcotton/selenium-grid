package com.thoughtworks.selenium.grid.remotecontrol;

import static junit.framework.Assert.assertEquals;
import org.junit.Test;

public class SelfRegisteringSeleniumServerTest {

    @Test
    public void hubURLIsTheOneProvidedInTheConstructor() {
        assertEquals("a url", new SelfRegisteringSeleniumServer("a url", "", "", "").hubURL());
    }

    @Test
    public void environmentIsTheOneProvidedInTheConstructor() {
        assertEquals("an environment", new SelfRegisteringSeleniumServer("", "an environment", "", "").environment());
    }

    @Test
    public void hostIsTheOneProvidedInTheConstructor() {
        assertEquals("a host", new SelfRegisteringSeleniumServer("", "", "a host", "").host());
    }

    @Test
    public void portIsTheOneProvidedInTheConstructor() {
        assertEquals("123", new SelfRegisteringSeleniumServer("", "", "a host", "123").port());
    }


}
