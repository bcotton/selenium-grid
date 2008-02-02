require File.expand_path(File.dirname(__FILE__) + "/../../../../../unit_test_helper")

include_class 'com.thoughtworks.selenium.grid.remotecontrol.SelfRegisteringSeleniumServer'

class HubRequestTest < Test::Unit::TestCase

  test "hubURL is the one provided in the constructor" do
    assert_equal "The URL", SelfRegisteringSeleniumServer.new("The URL", "", "", "").hub_url
  end

  test "environment is the one provided in the constructor" do
    assert_equal "The Environment", SelfRegisteringSeleniumServer.new("", "The Environment", "", "").environment
  end

  test "host is the one provided in the constructor" do
    assert_equal "The Host", SelfRegisteringSeleniumServer.new("", "", "The Host", "").host
  end

  test "port is the one provided in the constructor" do
    assert_equal "The Port", SelfRegisteringSeleniumServer.new("", "", "", "The Port").port
  end
  
end