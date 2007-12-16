require 'java'
require 'erb'
require 'console/controller'
module Java
  include_class com.thoughtworks.selenium.grid.ClasspathHelper
  include_class com.thoughtworks.selenium.grid.hub.ApplicationRegistry
end

Proc.new do |request, response|
  Console::Controller.new(response).index
end
