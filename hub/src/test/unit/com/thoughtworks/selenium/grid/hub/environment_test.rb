require File.expand_path(File.dirname(__FILE__) + "/../../../../../unit_test_helper")

include_class 'java.lang.IllegalArgumentException'
include_class 'com.thoughtworks.selenium.grid.hub.Environment'
include_class 'com.thoughtworks.selenium.grid.hub.remotecontrol.DynamicRemoteControlPool'

class EnvironmentTest < Test::Unit::TestCase

  test "Throws an IllegalArgumentException when name is null" do
    assert_throws("java.lang.IllegalArgumentException", "name cannot be null") do
      a_pool = Class.new { include DynamicRemoteControlPool }.new
      Environment.new(nil, "a browser", a_pool)  
    end
  end
  
  test "Name is the name provided to constructor" do
    assert_equal "an environment", Environment.new("an environment", "a browser", nil).name
  end

  test "Browser is the browser provided to constructor" do
    assert_equal "some browser", Environment.new("an environment", "some browser", nil).browser
  end

  test "Pool is the pool provided to constructor" do
    a_pool = Class.new { include DynamicRemoteControlPool }.new
    assert_equal a_pool, Environment.new("an environment", "some browser", a_pool).pool
  end

  test "An environment is not equal to a random object" do
    assert_not_equal Environment.new("an env", "a browser", nil), "random"
  end

  test "2 environments with the same name are equals" do
    assert_equal Environment.new("an environment", "*firefox", nil), Environment.new("an environment", "*ie", nil)  
  end

  test "2 environments with different names are not equals" do
    assert_not_equal Environment.new("an environment", "*browser", nil),
                     Environment.new("another environment", "*browser", nil)

  end

  test "2 environments with the same name have the same hashcode" do
    assert_equal Environment.new("an environment", "*browser", nil).hash_code,
                 Environment.new("an environment", "*browser", nil).hash_code  
  end

  test "2 environments with different names do not have the same hashcode" do
    assert_not_equal Environment.new("an environment", "*browser", nil).hash_code,
                     Environment.new("another environment", "*browser", nil).hash_code
  end

  test "to_string returns a human friendly string" do
    assert_equal "[Environment name='Firefox / Linux', browser='*chrome']",
                 Environment.new("Firefox / Linux", "*chrome", nil).to_string() 
  end

end