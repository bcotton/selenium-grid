require File.expand_path(File.dirname(__FILE__) + "/../../../../../unit_test_helper")

include_class 'java.lang.IllegalArgumentException'
include_class 'com.thoughtworks.selenium.grid.hub.Environment'
include_class 'com.thoughtworks.selenium.grid.hub.remotecontrol.DynamicRemoteControlPool'

class EnvironmentTest < Test::Unit::TestCase

  # Only here for reference until I introduce a JRuby servlet

  test "to_string returns a human friendly string" do
    assert_equal "[Environment name='Firefox / Linux', browser='*chrome']",
                 Environment.new("Firefox / Linux", "*chrome", nil).to_string()
  end

end