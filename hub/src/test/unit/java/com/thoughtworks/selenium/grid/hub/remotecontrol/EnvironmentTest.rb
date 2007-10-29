require 'java'

$CLASSPATH << "/Local/Users/Philippe/Projects/Selenium Grid/hub/target/dist/lib/selenium-grid-hub-standalone-0.9.2.jar"
include_class 'java.lang.IllegalArgumentException'
include_class 'com.thoughtworks.selenium.grid.hub.Environment'
include_class 'com.thoughtworks.selenium.grid.hub.remotecontrol.MonoEnvironmentPool'

require 'test/unit'

class Test::Unit::TestCase

  def self.test(description, &block)
    test_name = "test_#{description.gsub(/[\s]/, '_')}".to_sym
    raise "#{test_name} is already defined in #{self}" if self.instance_methods.include? test_name.to_s
    define_method test_name do
      instance_eval &block
    end
  end

  def assert_throws(java_exception_class, expected_message)
    begin
       yield
    rescue NativeException => e
      assert_equal "#{java_exception_class}: #{expected_message}", e.to_s
    end
  end
  
end

class EnvironmentTest < Test::Unit::TestCase

  test "Throws an IllegalArgumentException when name is null" do
    assert_throws("java.lang.IllegalArgumentException", "name cannot be null") do
      Environment.new(nil, "a browser", MonoEnvironmentPool.new(nil))  
    end
  end
  
  test "Name is the name provided to constructor" do
    assert_equal "an environment", Environment.new("an environment", "a browser", nil).name
  end

end