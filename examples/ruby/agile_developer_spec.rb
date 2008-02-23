require File.expand_path(File.dirname(__FILE__) + "/lib/view_test_helper")

describe "Agile Developer" do
  include BookExample
  
  it "demonstrates integrity and discipline" do
    as_described_on_amazon \
      :title => "Extreme Programming Explained: Embrace Change",
      :anchor => "Extreme Programming Explained: Embrace Change (2nd Edition) (The XP Series)",
      :keywords => "Extreme Programming",
      :isbn => "0321278658"
      
  end
  
  it "is positive and pragmatic" do
    as_described_on_amazon \
      :title => "The Pragmatic Programmer: From Journeyman to Master",
      :anchor => "The Pragmatic Programmer: From Journeyman to Master",
      :keywords => "Pragmatic Programmer",
      :isbn => "020161622"
  end
  
end

