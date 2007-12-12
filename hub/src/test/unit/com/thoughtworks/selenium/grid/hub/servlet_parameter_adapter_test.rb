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
    assert_equal "cmd=testComplete", parameters.queryString()
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

#  test "parameters are copied when a map is provided to constructor" do
#    servletParameterMap = HashMap.new #<String, String[]>();
#    servletParameterMap.put "a name", [ "a value" ]
#    assert_equals "a value", ServletParametersAdapter.new(servletParameterMap).get("a name")
#  end

  #  @Test
#  public void getReturnsOnlyTheFirstValueOfAParameter() {
#      final Map<String, String[]> servletParameterMap;
#
#      servletParameterMap = new HashMap<String, String[]>();
#      servletParameterMap.put("a name", new String[] { "first value", "second value" });
#      assertEquals("first value", new ServletParametersAdapter(servletParameterMap).get("a name"));
#  }
#
  test "get returns null when parameter does not exist" do
      assert_equal nil, ServletParametersAdapter.new.get("unknown parameter")
  end

  test "get returns nil when parameter value is null" do
    parameters = ServletParametersAdapter.new
    parameters.put "a name", nil
    assert_equal nil, parameters.get("a name")
  end

  #
#  @Test
#  public void getReturnsNullWhenParameterValueIsAnEmptyArray() {
#      final Map<String, String[]> servletParameterMap;
#
#      servletParameterMap = new HashMap<String, String[]>();
#      servletParameterMap.put("a name", new String[] { });
#      assertEquals(null, new ServletParametersAdapter(servletParameterMap).get("a name"));
#  }
#
#  @Test
#  public void putCanChangeAValueEventIfMapProvidedToConstructorIsFrozen() {
#      final Map<String, String[]> servletParameterMap;
#      final ServletParametersAdapter parameters;
#
#      servletParameterMap = new HashMap<String, String[]>();
#      servletParameterMap.put("a name", new String[] { "original value" });
#      parameters = new ServletParametersAdapter(Collections.unmodifiableMap(servletParameterMap));
#      parameters.put("a name", "new value");
#      assertEquals("new value", parameters.get("a name"));
#  }
#

end