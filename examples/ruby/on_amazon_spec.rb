require File.expand_path(File.dirname(__FILE__) + "/lib/view_test_helper")

describe "Martin Fowler's Refactoring Book" do
  
  it "should be found on Amazon" do
    @selenium.open '/'
    @selenium.type "twotabsearchtextbox", "refactoring"
    @selenium.click_and_wait "navGoButtonPanel"
    @selenium.location.should match(%r{^http://www.xxamazon.com/s/ref=})
    @selenium.click_and_wait "//img[@alt='Refactoring: Improving the Design of Existing Code (The Addison-Wesley Object Technology Series)']"
  end

end

