module BookExample
  
  def as_described_on_amazon(options)
    @selenium.open "/"
    @selenium.select "url", "Books"
    @selenium.type "twotabsearchtextbox", options[:keywords]
    @selenium.click_and_wait "navGoButtonPanel"
    # @selenium.is_text_present(options[:title]).should be_true
    @selenium.click "//img[@alt=\"#{options[:anchor]}\"]"
    @selenium.wait_for_page_to_load(60000);
    @selenium.field("name=quantity").should == "1"
    @selenium.is_text_present("ISBN-10: #{options[:isbn]}").should be_true
    @selenium.click_and_wait "link=Explore similar items"
    @selenium.wait_for_page_to_load 60000
    @selenium.go_back
    @selenium.wait_for_page_to_load 60000
    @selenium.field("quantity").should == "1"
    @selenium.select "quantity", "label=5"
    @selenium.click_and_wait "submit.add-to-cart"
    @selenium.is_text_present("Added to your\nShopping Cart:").should be_true
    @selenium.is_text_present(options[:title]).should be_true
    @selenium.is_text_present("quantity: 5").should be_true    
  end
  
end