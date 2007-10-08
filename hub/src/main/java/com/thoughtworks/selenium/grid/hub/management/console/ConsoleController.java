package com.thoughtworks.selenium.grid.hub.management.console;

import com.thoughtworks.selenium.grid.hub.ApplicationRegistry;
import com.thoughtworks.selenium.grid.hub.management.console.mvc.Controller;
import com.thoughtworks.selenium.grid.hub.management.console.mvc.Page;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class ConsoleController extends Controller {

    public ConsoleController(ApplicationRegistry registry) {
        super(registry);
    }

    public void process(HttpServletResponse response) throws ServletException, IOException {
        final Page page = list();
        render(page, response);
    }

    public Page list() {
        final Page page;

        page = new Page("index.html");
        page.set("environments", registry().environmentManager().environments());
        page.set("availableRemoteControls", registry().remoteControlPool().availableRemoteControls());
        page.set("reservedRemoteControls", registry().remoteControlPool().reservedRemoteControls());

        return page;
    }

}
