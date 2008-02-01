package com.thoughtworks.selenium.grid.hub;

import static com.thoughtworks.selenium.grid.ClasspathHelper.readFromClasspath;
import static com.thoughtworks.selenium.grid.IOHelper.close;
import org.jruby.Ruby;
import org.jruby.RubyProc;
import org.jruby.ast.Node;
import org.jruby.javasupport.JavaUtil;
import org.jruby.runtime.DynamicScope;
import org.jruby.runtime.builtin.IRubyObject;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

public class JRubyServlet extends javax.servlet.http.HttpServlet {
    public static Ruby jrubyRuntime = Ruby.getDefaultInstance();


    public void init() throws ServletException {
    }


    @Override
    public void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final RubyProc rubyProc;

        rubyProc = parseRubyScript();  // Cool for development
        rubyProc.call(new IRubyObject[]{wrap(request), wrap(response)});
    }

    protected RubyProc parseRubyScript() throws IOException {
        final java.net.URL rubyFile;
        final String rubyCode;
        final DynamicScope scope;
        InputStream stream = null;
        try {

            rubyFile = this.getClass().getResource("/dispatcher.rb");
            jrubyRuntime.getLoadService().require("java");
            rubyCode = readFromClasspath("dispatcher.rb");
            scope = jrubyRuntime.getCurrentContext().getCurrentScope();
            Node script = jrubyRuntime.parse(rubyCode, rubyFile.toExternalForm(), scope, 0);
            return (org.jruby.RubyProc) jrubyRuntime.eval(script);
        } finally {
            close(stream);
        }
    }

    protected IRubyObject wrap(Object object) {
        IRubyObject result = JavaUtil.convertJavaToRuby(jrubyRuntime, object);
        return jrubyRuntime.getModule("JavaUtilities").callMethod(jrubyRuntime.getCurrentContext(), "wrap", result);
    }


}
