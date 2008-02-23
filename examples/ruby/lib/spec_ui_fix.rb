# Workaround uninitialized constant Spec::Runner::Formatter
require 'rubygems'
require 'spec'
require 'spec/runner/formatter/html_formatter'
require 'spec/ui' 
require File.dirname(__FILE__) + '/spec/screenshot_saver'
require File.dirname(__FILE__) + '/spec/screenshot_formatter'
