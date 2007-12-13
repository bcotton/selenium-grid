require File.expand_path(File.dirname(__FILE__) + "/../../../../../unit_test_helper")

include_class 'com.thoughtworks.selenium.grid.hub.ServletParametersAdapter'
include_class 'com.thoughtworks.selenium.grid.hub.HttpCommandParser'


class HttpCommandParserTest < Test::Unit::TestCase

  test "parametersReturnsTheRequestParametersProvidedToTheConstructor" do
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
  
end