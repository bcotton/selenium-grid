require File.expand_path(File.dirname(__FILE__) + "/../../../../../unit_test_helper")

include_class 'com.thoughtworks.selenium.grid.remotecontrol.UnregistrationRequest'

class UnregistrationRequestTest < Test::Unit::TestCase

  test "Post to Hub using URL provided in constructor for unregistration request" do
    request = UnregistrationRequest.new "http://thehub.url:4444", "", "", ""
    assert_equal "http://thehub.url:4444/registration-manager/unregister", request.post_method.get_uri.to_string
  end

end