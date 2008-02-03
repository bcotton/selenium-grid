require File.expand_path(File.dirname(__FILE__) + "/../../../../../unit_test_helper")

include_class 'java.util.HashMap'
include_class 'com.thoughtworks.selenium.grid.hub.HttpParameters'

class ServletParameterAdapterTest < Test::Unit::TestCase

  test "query string is empty when request has no parameter" do
    assert_equal "", HttpParameters.new.queryString
  end

  test "build query string contructs the query string from request parameters" do
    parameters = HttpParameters.new
    parameters.put "cmd", "testComplete"
    assert_equal "cmd=testComplete", parameters.queryString
  end

  test "build query string is escaped" do
    parameters = HttpParameters.new
    parameters.put "param", "A value with space / slash"
    assert_equal "param=A+value+with+space+%2F+slash", parameters.queryString
  end

  test "build query string seperates parameters with ampersands" do
    parameters = HttpParameters.new
    parameters.put "cmd", "testComplete"
    parameters.put "sessionId", "1234"
    assert_equal "cmd=testComplete&sessionId=1234", parameters.queryString
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