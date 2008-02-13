require File.expand_path(File.dirname(__FILE__) + "/../../../../../unit_test_helper")

include_class 'java.util.HashMap'
include_class 'com.thoughtworks.selenium.grid.hub.HttpParameters'

class ServletParameterAdapterTest < Test::Unit::TestCase

  test "toString just have class name when request has no parameter" do
    assert_equal "", HttpParameters.new.to_s
  end

  test "toString includes request parameters when any" do
    parameters = HttpParameters.new
    parameters.put "cmd", "testComplete"
    assert_equal 'cmd => "testComplete"', parameters.to_s
  end

  test "toString seperates parameters with space" do
    parameters = HttpParameters.new
    parameters.put "cmd", "testComplete"
    parameters.put "sessionId", "1234"
    assert_equal 'cmd => "testComplete", sessionId => "1234"', parameters.to_s
  end

  test "get returns null when parameter does not exist" do
      assert_equal nil, HttpParameters.new.get("unknown parameter")
  end

  test "get returns nil when parameter value is null" do
    parameters = HttpParameters.new
    parameters.put "a name", nil
    assert_equal nil, parameters.get("a name")
  end

end