require File.expand_path(File.dirname(__FILE__) + "/../../../../../unit_test_helper")

include_class 'java.util.HashMap'
include_class 'com.thoughtworks.selenium.grid.hub.ServletParametersAdapter'

class ServletParameterAdapterTest < Test::Unit::TestCase

  test "query string is empty when request has no parameter" do
    assert_equal "", ServletParametersAdapter.new.queryString
  end

  test "build query string contructs the query string from request parameters" do
    parameters = ServletParametersAdapter.new
    parameters.put "cmd", "testComplete"
    assert_equal "cmd=testComplete", parameters.queryString
  end

  test "build query string is escaped" do
    parameters = ServletParametersAdapter.new
    parameters.put "param", "A value with space / slash"
    assert_equal "param=A+value+with+space+%2F+slash", parameters.queryString
  end

  test "build query string seperates parameters with ampersands" do
    parameters = ServletParametersAdapter.new
    parameters.put "cmd", "testComplete"
    parameters.put "sessionId", "1234"
    assert_equal "cmd=testComplete&sessionId=1234", parameters.queryString
  end

  xtest "jruby broken - Clint", "parameters are copied when a map is provided to constructor" do
    servletParameterMap = HashMap.new #<String, String[]>
    servletParameterMap.put "a name", [ "a value" ]
    assert_equals "a value", ServletParametersAdapter.new(servletParameterMap).get("a name")
  end

  xtest "jruby broken - Clint", "get returns only the first value of a parameter" do
    servlet_parameter_map = Map #<String, String[]>
    # TODO - Clint - ph7: two servlet_param_map locals, wtf? Weird java syntax?
    servlet_parameter_map = HashMap.new #<String, String[]>
    servlet_parameter_map.put "a name", ["first value", "second value"]
    assert_equal "first value", ServletParametersAdapter.new(servlet_parameter_map).get("a name")
  end

  test "get returns null when parameter does not exist" do
      assert_equal nil, ServletParametersAdapter.new.get("unknown parameter")
  end

  test "get returns nil when parameter value is null" do
    parameters = ServletParametersAdapter.new
    parameters.put "a name", nil
    assert_equal nil, parameters.get("a name")
  end

  xtest "jruby broken - Clint", "get returns nil when parameter value is an empty array" do
    servlet_parameter_map = Map.new #<String, String[]>
    servlet_parameter_map = HashMap.new #<String, String[]>
    servlet_parameter_map.put "a name", []
    assert_nil ServletParametersAdapter.new(servlet_parameter_map).get("a name")
  end

  xtest "jruby broken - Clint", "put can change a value event if map provided to constructor is frozen" do
    servlet_parameter_map = Map.new #<String, String[]>
    parameters = ServletParametersAdapter.new

    servlet_parameter_map = HashMap.new #<String, String[]>
    servlet_parameter_map.put "a name", ["original value"]
    parameters = ServletParametersAdapter.new(Collections.unmodifiable_map(servlet_parameter_map))
    parameters.put "a name", "new value"
    assert_equal "new value", parameters.get("a name")
  end

end