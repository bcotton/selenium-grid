package com.thoughtworks.selenium.grid.hub.management.console;

import com.thoughtworks.selenium.grid.hub.HubRegistry;
import com.thoughtworks.selenium.grid.hub.Environment;
import com.thoughtworks.selenium.grid.hub.EnvironmentManager;
import com.thoughtworks.selenium.grid.hub.management.console.mvc.Page;
import com.thoughtworks.selenium.grid.hub.remotecontrol.DynamicRemoteControlPool;
import com.thoughtworks.selenium.grid.hub.remotecontrol.RemoteControlProxy;
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertSame;
import org.jbehave.classmock.UsingClassMock;
import org.jbehave.core.mock.Mock;
import org.junit.Test;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.List;

public class ConsoleControllerTest extends UsingClassMock {

    @Test
    public void listPageTemplateIsIndex() {
        final ConsoleController controller;
        final Mock registry;

        registry = mock(HubRegistry.class);
        registry.stubs("environmentManager").will(returnValue(mock(EnvironmentManager.class)));
        registry.stubs("remoteControlPool").will(returnValue(mock(DynamicRemoteControlPool.class)));

        controller = new ConsoleController((HubRegistry) registry);
        assertEquals("index.html", controller.list().template());
        verifyMocks();
    }

    @Test
    public void listSetAvailableRemoteControlAssignFromRegistry() {
        final List<RemoteControlProxy> expectedRemoteControls;
        final ConsoleController controller;
        final Mock remoteControlPool;
        final Mock registry;

        expectedRemoteControls = Arrays.asList(new RemoteControlProxy("", 0, "", 1, null));
        registry = mock(HubRegistry.class);
        remoteControlPool = mock(DynamicRemoteControlPool.class);
        registry.stubs("environmentManager").will(returnValue(mock(EnvironmentManager.class)));

        registry.stubs("remoteControlPool").will(returnValue(remoteControlPool));
        remoteControlPool.stubs("availableRemoteControls").will(returnValue(expectedRemoteControls));

        controller = new ConsoleController((HubRegistry) registry);
        assertEquals(expectedRemoteControls, controller.list().assigns().get("availableRemoteControls"));
        verifyMocks();
    }

    @Test
    public void listSetReservedRemoteControlAssignFromRegistry() {
        final List<RemoteControlProxy> expectedRemoteControls;
        final ConsoleController controller;
        final Mock remoteControlPool;
        final Mock registry;

        expectedRemoteControls = Arrays.asList(new RemoteControlProxy("", 0, "", 1, null));
        registry = mock(HubRegistry.class);
        remoteControlPool = mock(DynamicRemoteControlPool.class);
        registry.stubs("environmentManager").will(returnValue(mock(EnvironmentManager.class)));

        registry.stubs("remoteControlPool").will(returnValue(remoteControlPool));
        remoteControlPool.stubs("reservedRemoteControls").will(returnValue(expectedRemoteControls));

        controller = new ConsoleController((HubRegistry) registry);
        assertEquals(expectedRemoteControls, controller.list().assigns().get("reservedRemoteControls"));
        verifyMocks();
    }

    @Test
    public void listSetEnvironmentsAssignFromRegistry() {
        final List<Environment> expectedEnvironments;
        final ConsoleController controller;
        final Mock environmentManager;
        final Mock registry;

        expectedEnvironments = Arrays.asList(new Environment("", ""));
        registry = mock(HubRegistry.class);
        environmentManager = mock(EnvironmentManager.class);
        registry.stubs("remoteControlPool").will(returnValue(mock(DynamicRemoteControlPool.class)));

        registry.expects("environmentManager").will(returnValue(environmentManager));
        environmentManager.expects("environments").will(returnValue(expectedEnvironments));

        controller = new ConsoleController((HubRegistry) registry);
        assertEquals(expectedEnvironments, controller.list().assigns().get("environments"));
        verifyMocks();
    }

    @Test
    public void processRendersThePageReturnedByTheListAction() throws IOException, ServletException {
        final ConsoleController controller;
        final Page expectedPage;
        final Mock expectedResponse;
        final Mock registry;

        expectedPage = new Page("");
        expectedResponse = mock(HttpServletResponse.class);
        registry = mock(HubRegistry.class);
        registry.stubs("environmentManager").will(returnValue(mock(EnvironmentManager.class)));
        registry.stubs("remoteControlPool").will(returnValue(mock(DynamicRemoteControlPool.class)));

        controller = new ConsoleController((HubRegistry) registry) {

            public Page list() {
                return expectedPage;
            }

            public void render(Page page, HttpServletResponse response) throws IOException {
                assertEquals(expectedPage, page);
                assertSame(expectedResponse, response);
            }
        };

        controller.process((HttpServletResponse) expectedResponse);
        verifyMocks();
    }

    @Test
    public void rendersIndexPage() throws IOException {
        final RemoteControlProxy[] remoteControls;
        final ConsoleController controller;
        final Mock response;
        final Page page;

        response = mock(HttpServletResponse.class);
        response.stubs("getWriter").will(returnValue(mock(PrintWriter.class)));
        remoteControls = new RemoteControlProxy[]{new RemoteControlProxy("a host", 0, "an environment", 1, null)};
        page = new Page("index.html");
        page.set("environments", new Environment[] { new Environment("a environment", "a browser")});
        page.set("availableRemoteControls", remoteControls);
        page.set("reservedRemoteControls", remoteControls);

        controller = new ConsoleController(HubRegistry.registry());
        controller.render(page, (HttpServletResponse) response);
    }

}
