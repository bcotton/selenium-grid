require File.expand_path(File.dirname(__FILE__) + "/lib/view_test_helper")

describe "Martin Fowler's Refactoring Book" do
  
  it "should be easily added to shopping cart" do
    @selenium.open '/'
    @selenium.type "twotabsearchtextbox", "refactoring"
    @selenium.click_and_wait "navGoButtonPanel"
    @selenium.click_and_wait "//img[@alt='Refactoring: Improving the Design of Existing Code (The Addison-Wesley Object Technology Series)']"
    @selenium.field("name=quantity").should == "1"
  end
  
end

