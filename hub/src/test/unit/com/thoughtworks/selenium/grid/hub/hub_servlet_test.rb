require File.expand_path(File.dirname(__FILE__) + "/../../../../../unit_test_helper")

include_class 'com.thoughtworks.selenium.grid.hub.HubServlet'

class HubServletTest < Test::Unit::TestCase

  xtest "jruby broken - Clint", "replay set content type as plain text" do
    remote_control_response = Response.new 123, ""
    servlet_response = HttpServletResponse.new
    servlet_response.expects("setContentType").with("text/plain")
    servlet_response.expects("getWriter").returns PrintWriter.new(StringWriter.new(100))

    HubServlet.new.reply(servlet_response, remote_control_response)
  end

  xtest "jruby broken - Clint", "reply set character encoding to UTF8" do
    remote_control_response = Response.new 123, ""
    servlet_response = HttpServletResponse.new
    servlet_response.expects("setCharacterEncoding").with("UTF-8")
    servlet_response.expects("getWriter").returns PrintWriter.new(StringWriter.new(100))

    HubServlet.new.reply(servlet_response, remote_control_response)
  end

  xtest "jruby broken - Clint", "reply set status from remote control on the servlet response" do
    remote_control_response = Response.new 123, ""
    servlet_response = HttpServletResponse.new
    servlet_response.expects("setStatus").with 123
    servlet_response.expects("getWriter").returns PrintWriter.new(StringWriter.new(100))
  end

  xtest "jruby broken - Clint", "reply write remote control response on servlet response as plain text" do
    writer = StringWriter.new 100
    remote_control_response = Response.new 0, "some response message"
    servlet_response = HttpServletResponse.new
    servlet_response.expects("getWriter").returns PrintWriter.new(writer)
    HubServlet.new.reply(servlet_response, remote_control_response)

    assert_equal "some response message", writer.get_buffer.to_string
  end

  xtest "jruby broken - Clint", "forward execute the selenese command on the appropriate remote control" do
    servlet = HubServlet.new
    request_parameters = ServletParametersAdapter.new
    request_parameters.put "cmd", "aSeleneseCommand"
    request_parameters.put "sessionId", "a session id"
    pool = DynamicRemoteControlPool.new
    remote_control = RemoteControlProxy.new
    environment_manager = EnvironmentManager.new
    response = Response.new 0, ""

    pool.expects("retrieve").with("a session id").returns remote_control
    remote_control.expects("forward").with(eq("cmd=aSeleneseCommand&sessionId=a+session+id")).returns response
    assert_equal response, servlet.forward(request_parameters, pool, environment_manager)
  end

  xtest "jruby broken - Clint", "forward returns an error message when a command parsing exception is thrown" do
    servlet = HubServlet.new
    request_parameters = ServletParamatersAdapter.new
    request_parameters.put "cmd", "aSeleneseCommand"
    request_parameters.put "sessionId", "a session id"
    pool = DynamicRemoteControlPool.new
    environment_manager = EnvironmentManager.new

    pool.expects("retrieve").with("a session id").throws CommandParsingException.new("an error message")
    response = servlet.forward(request_parameters, pool, environment_manager)
    assert_equal 200, response.status_code
    assert_equal "ERROR: an error message", response.body
  end

  xtest "jruby broken - Clint", "request parameters returns adapted request parameters" do
    parameter_map = new HashMap#<String, String[]>
    parameter_map.put "a name", ["a value"]
    request = HttpServletRequest.new
    request.expects("getParameterMap").returns parameter_map
    servlet = HubServlet.new
    parameters = servlet.request_parameters request
    assert_equal "a value", parameters.get("a name")
  end

end