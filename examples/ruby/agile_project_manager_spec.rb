require File.expand_path(File.dirname(__FILE__) + "/spec_helper")

describe "Agile Project Manager" do
  include BookExample
  
  it "regularly ships working software " do
    as_described_on_amazon \
      :title => "Ship it! A Practical Guide to Successful Software Projects",
      :anchor => "Ship it! A Practical Guide to Successful Software Projects",
      :keywords => "Ship It",
      :isbn => "0974514047"
      
  end
  
  it "improves the overall process" do
    as_described_on_amazon \
      :title => "Agile Retrospectives: Making Good Teams Great",
      :anchor => "Agile Retrospectives: Making Good Teams Great",
      :keywords => "Agile Retrospectives",
      :isbn => "0977616649"
  end
  
end
