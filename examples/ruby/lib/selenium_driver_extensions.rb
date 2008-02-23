module SeleniumDriverExtensions

  def location
    get_location
  end
  
  def field(locator)
    get_value locator
  end
  
  def wait_for_element(field_name, time=60000)
    script = <<-EOS
    var element;
    try {
      element = selenium.browserbot.findElement('#{field_name}');
    } catch(e) {
      element = null;
    }
    element != null
    EOS
    
    wait_for_condition script, time
  end
  
  def wait_for_text(field_name, text, time=60000)
    script = <<-EOS
    var element;
    try {
      element = selenium.browserbot.findElement('#{field_name}');
    } catch(e) {
      element = null;
    }
    element != null && element.innerHTML == '#{text}'"
    EOS
    
    wait_for_condition script, time
  end
  
  def click_and_wait(locator, timeout=60000)
    click locator
    wait_for_page_to_load timeout
  end
    
end
