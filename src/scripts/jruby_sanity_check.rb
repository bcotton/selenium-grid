#
# Validates JRuby Installation
#

EXPECTED_JRUBY_VERSION = "1.1RC2"
FORUM_URL = "http://forums.openqa.org/forum.jspa?forumID=42"

unless defined? JRUBY_VERSION
  raise "Sorry you must run this script with JRuby"
end

puts "Detected JRuby #{JRUBY_VERSION}"
unless JRUBY_VERSION == EXPECTED_JRUBY_VERSION
  raise <<-EOS
    Selenium Grid has been tested with JRuby #{EXPECTED_JRUBY_VERSION}. You are using #{JRUBY_VERSION}.
    Download it at http://jruby.codehaus.org
  EOS
end

begin
  $: << File.expand_path(File.dirname(__FILE__) + '/../../vendor/gems/mocha-0.5.5/lib')
  require File.expand_path(File.dirname(__FILE__) + '/../../vendor/gems/mocha-0.5.5/lib/mocha')
  puts "Successfully loaded Mocha library."
rescue
  puts <<-EOS
    Sorry, we could not load the Mocha library, feel free to report your problem
    on Selenium Grid forums (#{FORUM_URL}).
    We will do our best to help you get started.
  EOS
  raise
end
