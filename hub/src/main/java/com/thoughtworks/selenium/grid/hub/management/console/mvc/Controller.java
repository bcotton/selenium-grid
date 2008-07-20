package com.thoughtworks.selenium.grid.hub.management.console.mvc;

import com.thoughtworks.selenium.grid.hub.HubRegistry;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Web Controller processing HTTP requests.
 *
 * @author: Philippe Hanrigou
 */
public class Controller {

    private final HubRegistry registry;


    public Controller(HubRegistry registry) {
        this.registry = registry;
    }

    public void render(Page page, HttpServletResponse response) throws IOException {
        final Template template;
        final String content;

        template = templateResolver().get(page.template());
        content = template.render(page.assigns());
        response.setContentType("text/html");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().print(content);
    }

    protected HubRegistry registry() {
        return registry;
    }


    protected TemplateResolver templateResolver() {
      return new TemplateResolver(getClass());
    }

}
