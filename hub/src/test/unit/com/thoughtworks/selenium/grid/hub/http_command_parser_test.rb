require File.expand_path(File.dirname(__FILE__) + "/../../../../../unit_test_helper")

include_class 'com.thoughtworks.selenium.grid.hub.HttpParameters'
include_class 'com.thoughtworks.selenium.grid.hub.HttpCommandParser'
include_class 'com.thoughtworks.selenium.grid.hub.Environment'
include_class 'com.thoughtworks.selenium.grid.hub.EnvironmentManager'
include_class 'com.thoughtworks.selenium.grid.hub.remotecontrol.commands.NewBrowserSessionCommand'
include_class 'com.thoughtworks.selenium.grid.hub.remotecontrol.commands.TestCompleteCommand'


class HttpCommandParserTest < Test::Unit::TestCase

  test "parameters returns the request parameters provided to the constructor" do
    parameters = HttpParameters.new
    assert_equal parameters, HttpCommandParser.new(parameters).parameters
  end

  test "returns a remote control command with http request query string for a generic request" do
    parameters = HttpParameters.new
    parameters.put "cmd", "generic"
    parameters.put "sessionId", "1234"

    command = HttpCommandParser.new(parameters).parse nil
    assert_equal "cmd=generic&sessionId=1234", command.query_string
    assert_equal "1234", command.session_id
  end

  test "returns a remote control command with matching http parameters for a generic request" do
    parameters = HttpParameters.new
    parameters.put "cmd", "generic"
    parameters.put "sessionId", "1234"

    command = HttpCommandParser.new(parameters).parse nil
    assert_equal "generic", command.parameters.get("cmd")
    assert_equal "1234", command.parameters.get("sessionId")
  end

  test "returns a remote control command with matching session id for a generic request" do
    parameters = HttpParameters.new
    parameters.put "cmd", "generic"
    parameters.put "sessionId", "1234"

    command = HttpCommandParser.new(parameters).parse nil
    assert_equal "1234", command.session_id
  end

  test "returns test complete command for test complete requests" do
    parameters = HttpParameters.new
    parameters.put "cmd", "testComplete"
    parameters.put "sessionId", "1234"

    command = HttpCommandParser.new(parameters).parse nil
    assert_true command.is_a?(TestCompleteCommand)
    assert_equal "cmd=testComplete&sessionId=1234", command.query_string
    assert_equal "1234", command.sessionId
  end

end