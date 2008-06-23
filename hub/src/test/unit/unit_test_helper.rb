require 'java'

### Having FUN with JRuby classpath/loadpath so Java classes can be included seamlessly ###

module Java
  include_class java.lang.System
end

begin
  project_basedir = File.expand_path(File.dirname(__FILE__) + '/../../../..');
  [ "vendor/servlet-api-2.5-6.1.5.jar",
    "vendor/jetty-6.0.2.jar",
    "vendor/jetty-util-6.0.2.jar",
    "vendor/commons-codec-1.3.jar",
    "vendor/commons-logging-1.0.4.jar",
    "vendor/commons-httpclient-3.0.1.jar",
    "vendor/objenesis-1.0.jar",
    "vendor/cglib-nodep-2.1_3.jar",
    "vendor/junit-4.1.jar",
    "vendor/jmock-junit4-2.2.0.jar",
    "vendor/jmock-legacy-2.2.0.jar",
    "vendor/proxytoys-0.2.1.jar",
    "vendor/jmock-2.2.0.jar",
    "vendor/jbehave-1.0.1.jar",
    "hub/lib/freemarker-2.3.10.jar",
    "vendor/jyaml-1.3.jar",
    "hub/src/main/java",
    "hub/src/test/unit/java",
    "target/classes/production/Core/",
    "target/classes/production/Hub/",
    "hub/target/classes/",          
  ].each {|path| $CLASSPATH << (project_basedir + "/" + path) }
end

### Test::Unit Goodness ###

require 'test/unit'
$: << File.expand_path(File.dirname(__FILE__) + "/../../../../vendor/gems/mocha-0.5.5/lib")
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
