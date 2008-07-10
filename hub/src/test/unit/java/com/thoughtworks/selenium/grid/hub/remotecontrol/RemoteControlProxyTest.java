package com.thoughtworks.selenium.grid.hub.remotecontrol;

import static com.thoughtworks.selenium.grid.AssertionHelper.assertDistinctHashCodes;
import static com.thoughtworks.selenium.grid.AssertionHelper.assertNotEquals;
import static com.thoughtworks.selenium.grid.AssertionHelper.assertSameHashCode;
import com.thoughtworks.selenium.grid.HttpClient;
import com.thoughtworks.selenium.grid.HttpParameters;
import com.thoughtworks.selenium.grid.Response;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static junit.framework.Assert.assertFalse;
import org.jbehave.classmock.UsingClassMock;
import org.jbehave.core.mock.Mock;
import org.junit.Test;

import java.io.IOException;


public class RemoteControlProxyTest extends UsingClassMock {

    @Test(expected = IllegalArgumentException.class)
    public void contructorThrowsIllegalArgumentExceptionWhenServerIsNull() {
        new RemoteControlProxy(null, 5555, null, 1, null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void contructorThrowsIllegalArgumentExceptionWhenEnvironementIsNull() {
        new RemoteControlProxy("localhost", 1234, null, 1, null);
    }

    @Test
    public void hostReturnsTheHostSpecifiedInConstructor() {
        assertEquals("a host", new RemoteControlProxy("a host", 0, "", 1, null).host());
    }

    @Test
    public void portReturnsThePortSpecifiedInConstructor() {
        assertEquals(24, new RemoteControlProxy("", 24, "", 1, null).port());
    }

    @Test
    public void environmentReturnsTheEnvironmentSpecifiedInConstructor() {
        assertEquals("an environment", new RemoteControlProxy("", 0, "an environment", 1, null).environment());

    }

    @Test
    public void concurrentSessionsMaxReturnsTheValueSpecifiedInConstructor() {
        assertEquals(24, new RemoteControlProxy("", 0, "an environment", 24, null).concurrentSessionsMax());

    }

    @Test
    public void concurrentSessionCountReturnsZeroByDefault() {
        assertEquals(0, new RemoteControlProxy("a host", 0, "", 1, null).concurrentSesssionCount());
    }

    @Test
    public void concurrentSessionCountReturnsOneAfterCallingRegisterNewSession() {
        final RemoteControlProxy remoteControl;

        remoteControl = new RemoteControlProxy("a host", 0, "", 1, null);
        remoteControl.registerNewSession();
        assertEquals(1, remoteControl.concurrentSesssionCount());
    }

    @Test
    public void concurrentSessionCountReturnsThreeAfterCallingRegisterNewSessionThreTimes() {
        final RemoteControlProxy remoteControl;

        remoteControl = new RemoteControlProxy("a host", 0, "", 3, null);
        remoteControl.registerNewSession();
        remoteControl.registerNewSession();
        remoteControl.registerNewSession();
        assertEquals(3, remoteControl.concurrentSesssionCount());
    }

    @Test(expected = IllegalStateException.class)
    public void registerNewSessionThrowsAnIllegalStateExpressionWhenRegisteringMoreSessionThanAllowedByConcurrentSessionMax() {
        final RemoteControlProxy remoteControl;

        remoteControl = new RemoteControlProxy("a host", 0, "", 1, null);
        remoteControl.registerNewSession();
        remoteControl.registerNewSession();
    }

    @Test
    public void unregisterSessionDecreasesConcurrentSessionCountByOne() {
        final RemoteControlProxy remoteControl;

        remoteControl = new RemoteControlProxy("a host", 0, "", 1, null);
        remoteControl.registerNewSession();
        remoteControl.unregisterSession();
        assertEquals(0, remoteControl.concurrentSesssionCount());
    }

    @Test(expected = IllegalStateException.class)
    public void unregisterSessionThrowsAnIllegalStateExceptionWhenConcurrentSessionCountIsZero() {
        final RemoteControlProxy remoteControl;

        remoteControl = new RemoteControlProxy("a host", 0, "", 1, null);
        remoteControl.unregisterSession();
    }

    @Test
    public void canHandleNewSessionReturnTrueWhenConcurrentSessinCountIsLowerThanConcurrentSessionMax() {
        final RemoteControlProxy remoteControl;

        remoteControl = new RemoteControlProxy("a host", 0, "", 2, null);
        remoteControl.registerNewSession();
        assertTrue(remoteControl.canHandleNewSession());
    }

    @Test
    public void canHandleNewSessionReturnFalseWhenConcurrentSessinCountIsEqualToConcurrentSessionMax() {
        final RemoteControlProxy remoteControl;

        remoteControl = new RemoteControlProxy("a host", 0, "", 1, null);
        remoteControl.registerNewSession();
        assertFalse(remoteControl.canHandleNewSession());
    }

    @Test
    public void remoteControlURLTargetsASpecificSeleniumRC() {
        final RemoteControlProxy proxy = new RemoteControlProxy("localhost", 5555, "", 1, null);
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
        proxy = new RemoteControlProxy("foo", 10, "", 1, (HttpClient) client);
        assertEquals(expectedResponse, proxy.forward(parameters));

        verifyMocks();
    }

    @Test
    public void toStringMethodReturnsAHumanFriendlyDescriptionWithServerAndPortInformation() {
        assertEquals("[RemoteControlProxy grid.thoughtworks.org:4444 0/24]",
                     new RemoteControlProxy("grid.thoughtworks.org", 4444, "", 24, null).toString());
    }

    @Test
    public void toStringIncludesconcurrentSessinCount() {
        final RemoteControlProxy remoteControl;

        remoteControl = new RemoteControlProxy("grid.thoughtworks.org", 4444, "", 15, null);
        remoteControl.registerNewSession();
        remoteControl.registerNewSession();
        assertEquals("[RemoteControlProxy grid.thoughtworks.org:4444 2/15]",
                     remoteControl.toString());
    }

    @SuppressWarnings({"EqualsBetweenInconvertibleTypes"})
    @Test
    public void aRemoteControlsIsNotEqualToARandomObject() {
        assertNotEquals(new RemoteControlProxy("a.host.com", 24, "", 1, new HttpClient()), "a random object");
    }


    @Test
    public void aRemoteControlsIsNotEqualToItself() {
        final RemoteControlProxy aRemoteControl;

        aRemoteControl = new RemoteControlProxy("a.host.com", 24, "", 1, new HttpClient());
        assertEquals(aRemoteControl, aRemoteControl);
    }

    @Test
    public void twoRemoteControlsAreEqualIfTheirHostAndPortMatch() {
        assertEquals(new RemoteControlProxy("a.host.com", 24, "", 1, new HttpClient()),
                     new RemoteControlProxy("a.host.com", 24, "", 1, new HttpClient()));
    }

    @Test
    public void twoRemoteControlsAreNotEqualIfTheirHostsDoNotMatch() {
        final RemoteControlProxy anotherRemoteControl;
        final RemoteControlProxy aRemoteControl;

        aRemoteControl = new RemoteControlProxy("a.host.com", 24, "", 1, new HttpClient());
        anotherRemoteControl = new RemoteControlProxy("another.host.com", 24, "", 1, new HttpClient());
        assertNotEquals(aRemoteControl, anotherRemoteControl);
    }

    @Test
    public void twoRemoteControlsAreNotEqualIfTheirPortsDoNotMatch() {
        final RemoteControlProxy anotherRemoteControl;
        final RemoteControlProxy aRemoteControl;

        aRemoteControl = new RemoteControlProxy("a.host.com", 24, "", 1, new HttpClient());
        anotherRemoteControl = new RemoteControlProxy("a.host.com", 64, "", 1, new HttpClient());
        assertNotEquals(aRemoteControl, anotherRemoteControl);
    }

    @Test
    public void twoRemoteControlsHaveTheSameHashcodeIfTheirHostAndPortMatch() {
        assertSameHashCode(new RemoteControlProxy("a.host.com", 24, "", 1, new HttpClient()),
                           new RemoteControlProxy("a.host.com", 24, "", 1, new HttpClient()));
    }

    @Test
    public void twoRemoteControlsDoNotHaveTheSameHashcodelIfTheirHostsDoNotMatch() {
        final RemoteControlProxy anotherRemoteControl;
        final RemoteControlProxy aRemoteControl;

        aRemoteControl = new RemoteControlProxy("a.host.com", 24, "", 1, new HttpClient());
        anotherRemoteControl = new RemoteControlProxy("another.host.com", 24, "", 1, new HttpClient());
        assertDistinctHashCodes(aRemoteControl, anotherRemoteControl);
    }

    @Test
    public void twoRemoteControlsDoNotHaveTheSameHashcodelIfTheirPortsDoNotMatch() {
        final RemoteControlProxy anotherRemoteControl;
        final RemoteControlProxy aRemoteControl;

        aRemoteControl = new RemoteControlProxy("a.host.com", 24, "", 1, new HttpClient());
        anotherRemoteControl = new RemoteControlProxy("a.host.com", 64, "", 1, new HttpClient());
        assertDistinctHashCodes(aRemoteControl, anotherRemoteControl);
    }

}
