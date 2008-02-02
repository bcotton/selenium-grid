require File.expand_path(File.dirname(__FILE__) + "/../../../../../unit_test_helper")

include_class 'com.thoughtworks.selenium.grid.remotecontrol.RegistrationRequest'

class RegistrationRequestTest < Test::Unit::TestCase

  test "Post to Hub using URL provided in constructor for registration request" do
    request = RegistrationRequest.new "http://thehub.url:4444", "", "", ""
    assert_equal "http://thehub.url:4444/registration-manager/register", request.post_method.get_uri.to_string
  end

end