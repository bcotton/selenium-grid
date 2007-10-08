package com.thoughtworks.selenium.grid.hub.management.console;

import org.jbehave.classmock.UsingClassMock;
import org.jbehave.core.mock.Mock;
import org.junit.Test;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletException;
import java.io.IOException;


public class ConsoleServletTest extends UsingClassMock {

    @Test
    public void doGetCallsListActionOnConsoleController() throws IOException, ServletException {
        final ConsoleServlet servlet;
        final Mock controller;
        final Mock response;

        response = mock(HttpServletResponse.class);
        controller = mock(ConsoleController.class);
        servlet = new ConsoleServlet() {
            protected ConsoleController controller() {
                return (ConsoleController) controller;
            }
        };

        controller.expects("process").with(response);

        servlet.doGet(null, (HttpServletResponse) response);
        verifyMocks();
    }

}
