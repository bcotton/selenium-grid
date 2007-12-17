require File.expand_path(File.dirname(__FILE__) + "/../../../../../unit_test_helper")

include_class 'com.thoughtworks.selenium.grid.hub.ServletParametersAdapter'
include_class 'com.thoughtworks.selenium.grid.hub.HttpCommandParser'
include_class 'com.thoughtworks.selenium.grid.hub.Environment'
include_class 'com.thoughtworks.selenium.grid.hub.EnvironmentManager'
include_class 'com.thoughtworks.selenium.grid.hub.remotecontrol.commands.NewBrowserSessionCommand'
include_class 'com.thoughtworks.selenium.grid.hub.remotecontrol.commands.TestCompleteCommand'


class HttpCommandParserTest < Test::Unit::TestCase

  test "parameters returns the request parameters provided to the constructor" do
    parameters = ServletParametersAdapter.new
    assert_equal parameters, HttpCommandParser.new(parameters).send_non_public(:parameters)
  end

  test "returns a remote control command with http request query string for a generic request" do
    parameters = ServletParametersAdapter.new
    parameters.put "cmd", "generic"
    parameters.put "sessionId", "1234"

    command = HttpCommandParser.new(parameters).parse nil
    assert_equal "cmd=generic&sessionId=1234", command.query_string
    assert_equal "1234", command.session_id
  end
  
  xtest "jruby broken - Clint", "returns NewBrowserSessionCommand for new session requests" do
    parameters = ServletParametersAdapter.new
    parameters.put("cmd", "getNewBrowserSession")
    parameters.put("1", "an environment name")
    parameters.put("2", "http://openqa.org")
    expected_environment = Environment.new("", "aBrowser", nil)
    environment_manager = EnvironmentManager.new
    p environment_manager
    environment_manager.stubs(:environment).with("an environment name").returns(expected_environment)

    assert_equal expected_environment, environment_manager.environment("an environment name")
    command = HttpCommandParser.new(parameters).parse(environment_manager)
    assert_equal true, command.kind_of?(NewBrowserSessionCommand)
      assert browserSessionCommand.queryString =~ /cmd=getNewBrowserSession/
      assertTrue(browserSessionCommand.queryString().contains("1=aBrowser"));
      assertTrue(browserSessionCommand.queryString().contains("2=http%3A%2F%2Fopenqa.org"));
      assertEquals(expectedEnvironment, browserSessionCommand.environment());
  end

  test "returns test complete command for test complete requests" do
    parameters = ServletParametersAdapter.new
    parameters.put "cmd", "testComplete"
    parameters.put "sessionId", "1234"

    command = HttpCommandParser.new(parameters).parse nil
    assert_true command.is_a?(TestCompleteCommand)
    assert_equal "cmd=testComplete&sessionId=1234", command.query_string
    assert_equal "1234", command.sessionId
  end

#  public void returnsTestCompleteCommandForTestCompleteRequests() {
#      final ServletParametersAdapter parameters;
#      final SeleneseCommand command;
#
#      parameters = new ServletParametersAdapter();
#      parameters.put("cmd", "testComplete");
#      parameters.put("sessionId", "1234");
#
#      command = new HttpCommandParser(parameters).parse(null);
#      assertEquals(true, command instanceof TestCompleteCommand);
#      assertEquals("cmd=testComplete&sessionId=1234", command.queryString());
#      assertEquals("1234", command.sessionId());
#  }


end