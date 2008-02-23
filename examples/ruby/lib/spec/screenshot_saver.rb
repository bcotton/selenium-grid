module Spec
  module ScreenshotSaver
    
    def save_screenshot_to(png_path)
      dir = File.dirname(png_path)
      FileUtils.mkdir_p(dir) unless File.directory?(dir)
      if PLATFORM['darwin']
        sh "screencapture '#{png_path}'"
      elsif image_magick_support?
         sh "import -window root '#{png_path}'"        
      end
    end
    
    def image_magick_support?
      @image_magick_support ||= `import --version`.grep /"ImageMagick"/
    end
    
    def sh(command)
      system command
      STDERR.puts "Warning: Could not capture screenshot with '#{command}'" unless $? == 0
    end
    
  end
  
end
