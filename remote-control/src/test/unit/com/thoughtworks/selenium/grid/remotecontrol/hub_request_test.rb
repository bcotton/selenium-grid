require File.expand_path(File.dirname(__FILE__) + "/../../../../../unit_test_helper")

include_class 'com.thoughtworks.selenium.grid.remotecontrol.HubRequest'
include_class 'org.apache.commons.httpclient.HttpClient'

class HubRequestTest < Test::Unit::TestCase

  test "Post host as provided in constructor" do
    assert_equal "The Host", HubRequest.new("", "The Host", "", "").post_method.get_parameter("host").value
  end

  test "Post port as provided in constructor" do
    assert_equal "The Port", HubRequest.new("", "", "The Port", "").post_method.get_parameter("port").value
  end

  test "Post environment as provided in constructor" do
    assert_equal "The Env", HubRequest.new("", "", "", "The Env").post_method.get_parameter("environment").value
  end

  test "Post to Hub using URL provided in constructor" do
    request = HubRequest.new("http://hub.url:4444/action", "", "", "")
    assert_equal "http://hub.url:4444/action", request.post_method.get_uri.to_string
  end

  test "environment returns environment as provided in constructor" do
    assert_equal "The Env", HubRequest.new("", "", "", "The Env").environment
  end

  test "target_url  returns target URL as provided in constructor" do
    assert_equal "http://target-url", HubRequest.new("http://target-url", "", "", "").target_url
  end
  
end