# Code from Spec::UI by Aslak Hellesøy
# Changed to have it run with RSpec 1.1.3

require 'stringio'
require 'rubygems'
require 'spec'
require 'spec/runner/formatter/html_formatter'
require File.dirname(__FILE__) + '/screenshot_saver'

module Spec
  class ScreenshotFormatter < Spec::Runner::Formatter::HtmlFormatter
    
    class << self
      attr_accessor :instance
    end

    include ScreenshotSaver

    def initialize(where, out=where)
      super(where, out)
      if out.is_a?(String)
        @root = File.dirname(out)
      else
        raise "#{self.class} must write to a file, so that we know where to store screenshots"
      end
      raise "Only one instance of #{self.class} is allowed" unless self.class.instance.nil?
      ScreenshotFormatter.instance = self
    end
    
    # Writes the HTML from +browser+ to disk
    # This method *must* be called in an after(:each) block.
    def save_html_snapshot(selenium_driver)
      dir = File.dirname(absolute_html_path)
      FileUtils.mkdir_p(dir) unless File.directory?(dir)
      File.open(absolute_html_path, "w") do |io| 
        io.write(selenium_driver.html)
      end
    end
    
    # Takes a screenshot of the current window and saves it to disk. 
    # Use this method when you don't have a browser object.
    def save_screenshot(selenium_driver)
      save_screenshot_to absolute_png_path
    end
        
    def absolute_png_path
      File.join(@root, relative_png_path)
    end

    def relative_png_path
      "images/#{current_example_number}.png"
    end

    def absolute_html_path
      File.join(@root, relative_html_path)
    end

    def relative_html_path
      "html/#{current_example_number}.html"
    end

    def global_scripts
      super + <<-EOF
function showImage(e) {
w = window.open();
w.location = e.childNodes[0].src
}

// Lifted from Ruby RDoc
function toggleSource( id ) {
var elem
var link

if( document.getElementById )
{
  elem = document.getElementById( id )
  link = document.getElementById( "l_" + id )
}
else if ( document.all )
{
  elem = eval( "document.all." + id )
  link = eval( "document.all.l_" + id )
}
else
  return false;

if( elem.style.display == "block" )
{
  elem.style.display = "none"
  link.innerHTML = "show snapshot"
}
else
{
  elem.style.display = "block"
  link.innerHTML = "hide snapshot"
}
}
EOF
    end

    def global_styles
      super + <<-EOF
div.rspec-report textarea {
width: 100%;
}

div.rspec-report div.dyn-source {
background:#FFFFEE none repeat scroll 0%;
border:1px dotted black;
color:#000000;
display:none;
margin:0.5em 2em;
padding:0.5em;
}
EOF
    end

    def extra_failure_content(failure)
      result = super(failure)
      if File.exist?(absolute_png_path)
        result += img_div 
      end
      if File.exist?(absolute_html_path)
        source_id = "#{current_example_number}_source"
        result += "        <div>[<a id=\"l_#{source_id}\" href=\"javascript:toggleSource('#{source_id}')\">show snapshot</a>]</div>\n"
        result += "        <div id=\"#{source_id}\" class=\"dyn-source\"><iframe src=\"#{relative_html_path}\" width=\"100%\" height=\"300px\"></iframe></div>\n"
      end
      result
    end

    def img_div
      "        <div><a href=\"#{relative_png_path}\"><img width=\"25%\" height=\"25%\" src=\"#{relative_png_path}\" /></a></div>\n"
    end
  end
  
  # This formatter produces the same HTML as ScreenshotFormatter, except that
  # it doesn't save screenshot PNGs and browser snapshot HTML source to disk. 
  # It is meant to be used from a Spec::Distributed master
  class MasterScreenshotFormatter < ScreenshotFormatter
    def screenshot
    end
    
    def save_html_snapshot(browser)
    end
  end
  
  # This formatter writes PNG and browser snapshot HTML to disk, just like its superclass,
  # but it doesn't write the HTML report itself.
  # It is meant to be used from Spec::Distributed slaves
  class SlaveScreenshotFormatter < ScreenshotFormatter
    def initialize(where)
      super(where, StringIO.new)
    end
  end
end
