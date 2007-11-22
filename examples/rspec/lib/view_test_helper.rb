$:.unshift
require 'rubygems'
require 'spec'
require 'spec/ui'
require 'spec/ui/selenium/driver_ext'
require File.expand_path(File.dirname(__FILE__) + "/selenium")
require File.expand_path(File.dirname(__FILE__) + "/selenium_driver_extensions")

Spec::Runner.configure do |config|

  config.before(:all) do
    remote_control_server = ENV['SELENIUM_REMOTE_CONTROL'] || "localhost"
    port = ENV['SELENIUM_PORT'] || 4444
    browser = ENV['SELENIUM_BROWSER'] || "*chrome"
    application_host = ENV['SELENIUM_APPLICATION_HOST'] || "amazon.com"
    application_port = ENV['SELENIUM_APPLICATION_PORT'] || "80"
    timeout = 60000    
    puts "Contacting Selenium RC on #{remote_control_server}:#{port} -> http://#{application_host}:#{application_port}"
    @selenium = Selenium::SeleniumDriver.new(remote_control_server, port, browser, "http://#{application_host}:#{application_port}", timeout)
    @selenium.extend SeleniumDriverExtensions
    @selenium.start
  end

  config.after(:each) do
    formatter = Spec::Ui::ScreenshotFormatter.instance
    formatter.take_screenshot_of(@selenium) unless formatter.nil?
  end

  config.after(:all) do
    @selenium.stop
  end
  
  
end

