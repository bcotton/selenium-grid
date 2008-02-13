package com.thoughtworks.selenium.grid.hub.remotecontrol;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import org.jbehave.classmock.UsingClassMock;
import org.jbehave.core.mock.Mock;
import org.junit.Test;

import java.io.IOException;

import com.thoughtworks.selenium.grid.HttpParameters;
import com.thoughtworks.selenium.grid.Response;
import com.thoughtworks.selenium.grid.HttpClient;


public class RemoteControlProxyTest extends UsingClassMock {

    @Test(expected = IllegalArgumentException.class)
    public void contructorThrowsIllegalArgumentExceptionWhenServerIsNull() {
        new RemoteControlProxy(null, 5555, null, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void contructorThrowsIllegalArgumentExceptionWhenEnvironementIsNull() {
        new RemoteControlProxy("localhost", 1234, null, null);
    }

    @Test
    public void hostReturnsTheHostSpecifiedInConstructor() {
        assertEquals("a host", new RemoteControlProxy("a host", 0, "", null).host());
    }

    @Test
    public void portReturnsThePortSpecifiedInConstructor() {
        assertEquals(24, new RemoteControlProxy("", 24, "", null).port());
    }

    @Test
    public void environmentReturnsTheEnvironmentSpecifiedInConstructor() {
        assertEquals("an environment", new RemoteControlProxy("", 0, "an environment", null).environment());

    }

    @Test
    public void remoteControlURLTargetsASpecificSeleniumRC() {
        final RemoteControlProxy proxy = new RemoteControlProxy("localhost", 5555, "", null);
        assertEquals("http://localhost:5555/selenium-server/driver/", proxy.remoteControlURL());
    }

    @Test
    public void forwardReturnsTheResponseOfTheSeleniumRC() throws IOException {
        final RemoteControlProxy proxy;
        final Response expectedResponse;
        final HttpParameters parameters;
        final Mock client;

        expectedResponse = new Response(0, "");
        client = mock(HttpClient.class);
        parameters = new HttpParameters();
        client.expects("post").with(eq("http://foo:10/selenium-server/driver/"), eq(parameters)).will(returnValue(expectedResponse));
        proxy = new RemoteControlProxy("foo", 10, "", (HttpClient) client);
        assertEquals(expectedResponse, proxy.forward(parameters));

        verifyMocks();
    }

    @Test
    public void toStringMethodReturnsAHumanFriendlyDescriptionWithServerAndPortInformation() {
        assertEquals("[RemoteControlProxy grid.thoughtworks.org:4444]",
                     new RemoteControlProxy("grid.thoughtworks.org", 4444, "", null).toString());
    }

    @SuppressWarnings({"EqualsBetweenInconvertibleTypes"})
    @Test
    public void aRemoteControlsIsNotEqualToARandomObject() {
        assertFalse(new RemoteControlProxy("a.host.com", 24, "", new HttpClient())
                    .equals("a random object"));
    }

    @Test
    public void twoRemoteControlsAreEqualIfTheirHostAndPortMatch() {
        assertEquals(new RemoteControlProxy("a.host.com", 24, "", new HttpClient()),
                     new RemoteControlProxy("a.host.com", 24, "", new HttpClient()));
    }

    @Test
    public void twoRemoteControlsAreNotEqualIfTheirHostsDoNotMatch() {
        final RemoteControlProxy anotherRemoteControl;
        final RemoteControlProxy aRemoteControl;

        aRemoteControl = new RemoteControlProxy("a.host.com", 24, "", new HttpClient());
        anotherRemoteControl = new RemoteControlProxy("another.host.com", 24, "", new HttpClient());
        assertFalse(aRemoteControl.equals(anotherRemoteControl));
    }

    @Test
    public void twoRemoteControlsAreNotEqualIfTheirPortsDoNotMatch() {
        final RemoteControlProxy anotherRemoteControl;
        final RemoteControlProxy aRemoteControl;

        aRemoteControl = new RemoteControlProxy("a.host.com", 24, "", new HttpClient());
        anotherRemoteControl = new RemoteControlProxy("a.host.com", 64, "", new HttpClient());
        assertFalse(aRemoteControl.equals(anotherRemoteControl));
    }

    @Test
    public void twoRemoteControlsHaveTheSameHashcodeIfTheirHostAndPortMatch() {
        assertEquals(new RemoteControlProxy("a.host.com", 24, "", new HttpClient()).hashCode(),
                     new RemoteControlProxy("a.host.com", 24, "", new HttpClient()).hashCode());
    }

    @Test
    public void twoRemoteControlsDoNotHaveTheSameHashcodelIfTheirHostsDoNotMatch() {
        final RemoteControlProxy anotherRemoteControl;
        final RemoteControlProxy aRemoteControl;

        aRemoteControl = new RemoteControlProxy("a.host.com", 24, "", new HttpClient());
        anotherRemoteControl = new RemoteControlProxy("another.host.com", 24, "", new HttpClient());
        assertFalse(aRemoteControl.hashCode() == anotherRemoteControl.hashCode());
    }

    @Test
    public void twoRemoteControlsDoNotHaveTheSameHashcodelIfTheirPortsDoNotMatch() {
        final RemoteControlProxy anotherRemoteControl;
        final RemoteControlProxy aRemoteControl;

        aRemoteControl = new RemoteControlProxy("a.host.com", 24, "", new HttpClient());
        anotherRemoteControl = new RemoteControlProxy("a.host.com", 64, "", new HttpClient());
        assertFalse(aRemoteControl.hashCode() == anotherRemoteControl.hashCode());
    }

}
