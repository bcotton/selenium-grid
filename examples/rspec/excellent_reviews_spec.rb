require File.expand_path(File.dirname(__FILE__) + "/lib/view_test_helper")

describe "Martin Fowler's Refactoring Book" do
  
  it "should get excellent reviews" do
    @selenium.open '/'
    @selenium.type "twotabsearchtextbox", "refactoring"
    @selenium.click_and_wait "navGoButtonPanel"
    @selenium.click_and_wait "//img[@alt='Refactoring: Improving the Design of Existing Code (The Addison-Wesley Object Technology Series)']"
    @selenium.is_text_present("excellent").should be_true
  end

end

