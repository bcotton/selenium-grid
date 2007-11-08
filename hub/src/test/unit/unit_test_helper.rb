require 'java'

### Having FUN with JRuby classpath/loadpath so Java classes can be included seamlessly ###

module Java
  include_class java.lang.System
end

begin
  project_basedir = File.expand_path(File.dirname(__FILE__) + '/../../../..');
  [ "lib/servlet-api-2.5-6.1.5.jar",
    "lib/jetty-6.0.2.jar",
    "lib/jetty-util-6.0.2.jar",
    "lib/commons-codec-1.3.jar",
    "lib/commons-logging-1.0.4.jar",
    "lib/commons-httpclient-3.0.1.jar",
    "lib/objenesis-1.0.jar",
    "lib/cglib-nodep-2.1_3.jar",
    "lib/junit-4.1.jar",
    "lib/jmock-junit4-2.2.0.jar",
    "lib/jmock-legacy-2.2.0.jar",
    "lib/proxytoys-0.2.1.jar",
    "lib/jmock-2.2.0.jar",
    "lib/jbehave-1.0.1.jar",
    "hub/lib/freemarker-2.3.10.jar",
    "lib/jyaml-1.3.jar",
    "hub/src/main/java",
    "hub/src/test/unit/java",
    "target/classes/production/Core/",
    "target/classes/production/Hub/",
  ].each {|path| $CLASSPATH << (project_basedir + "/" + path) }
end

### Test::Unit Goodness ###

require 'test/unit'
require 'rubygems'
require 'mocha'

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

