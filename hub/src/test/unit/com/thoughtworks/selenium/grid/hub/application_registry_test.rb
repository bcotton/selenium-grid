require File.expand_path(File.dirname(__FILE__) + "/../../../../../unit_test_helper")

include_class 'com.thoughtworks.selenium.grid.hub.ApplicationRegistry'

class EnvironmentTest < Test::Unit::TestCase

  test "registry returns a singleton instance" do
    assert_equal ApplicationRegistry.registry, ApplicationRegistry.registry
  end

end
