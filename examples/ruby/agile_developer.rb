require File.expand_path(File.dirname(__FILE__) + "/lib/view_test_helper")

describe "Agile Developer" do
  include BookExample
  
  it "refactors aggressively" do
    as_described_on_amazon \
      :title => "Refactoring: Improving the Design of Existing Code",
      :anchor => "Refactoring: Improving the Design of Existing Code (The Addison-Wesley Object Technology Series)",
      :keywords => "Refactoring",
      :isbn => "0201485672"
      
  end

end

