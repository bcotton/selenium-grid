require File.expand_path(File.dirname(__FILE__) + "/spec_helper")

describe "Good Developer" do
  include BookExample
  
  it "refactors aggressively" do
    as_described_on_amazon \
      :title => "Refactoring: Improving the Design of Existing Code",
      :anchor => "Refactoring: Improving the Design of Existing Code (The Addison-Wesley Object Technology Series)",
      :keywords => "Refactoring",
      :isbn => "0201485672"
      
  end

  it "drive its design with tests" do
    as_described_on_amazon \
      :title => "Test Driven Development: By Example",
      :anchor => "Test Driven Development: By Example (The Addison-Wesley Signature Series)",
      :keywords => "Test Driven Development",
      :isbn => "0321146530"
      
  end
  
end