require File.expand_path(File.dirname(__FILE__) + "/spec_helper")

describe "Good Rubyist" do
  include BookExample
  
  it "embraces Ruby" do
    as_described_on_amazon \
      :title => "Programming Ruby: The Pragmatic Programmers' Guide, Second Edition",
      :anchor => "Programming Ruby: The Pragmatic Programmers' Guide, Second Edition",
      :keywords => "Ruby",
      :isbn => "0974514055"
      
  end
  
  it "has style" do
    as_described_on_amazon \
      :title => "The Ruby Way, Second Edition: Solutions and Techniques in Ruby Programming",
      :anchor => "The Ruby Way, Second Edition: Solutions and Techniques in Ruby Programming (2nd Edition) (Addison-Wesley Professional Ruby Series)",
      :keywords => "Ruby",
      :isbn => "0672328844"
  end
  
end
