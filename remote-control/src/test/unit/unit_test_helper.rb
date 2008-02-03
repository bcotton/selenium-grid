require 'java'

### Having FUN with JRuby classpath/loadpath so Java classes can be included seamlessly ###

module Java
  include_class java.lang.System
end

begin
  project_basedir = File.expand_path(File.dirname(__FILE__) + '/../../../..');
  [

  "lib/commons-httpclient-3.0.1.jar",
  "lib/commons-codec-1.3.jar",
  "lib/commons-logging-1.0.4.jar",
  "remotecontrol/lib/selenium-server-*.jar",
  "remote-control/src/main/java",
  "remote-control/src/test/unit/java",
  "target/classes/production/Core/",
  "target/classes/production/Hub/",
  "target/classes/production/Remote Control/",
  "remote-control/target/classes/",
  ].each {|path| $CLASSPATH << (project_basedir + "/" + path) }
end

### Test::Unit Goodness ###

require 'test/unit'
$: << File.expand_path(File.dirname(__FILE__) + "/../../../../lib/gems/mocha-0.5.5/lib")
require "mocha"

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

  def assert_nil(actual, message=nil)
    assert_equal nil, actual, message
  end

  def assert_true(actual, message=nil)
    assert_equal true, actual, message
  end

  def self.xtest(skip_reason, test_name)
    test_name = :"test_#{test_name.gsub(/[^\w\d]/,'_')}"
    define_method test_name do
      print "(#{skip_reason})"
    end
  end

end

class Object

  # JRuby 1.0.2 cannot send to Java protected method.
  # This method is a workaround using Java reflection to invoke the method
  def send_non_public(method_name)
    method = self.java_class.declared_instance_methods.find {|m| m.name == method_name.to_s} 
    method.accessible = true unless method.accessible?
    method.invoke(self.java_object)
  end
  
end
