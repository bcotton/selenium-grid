require File.expand_path(File.dirname(__FILE__) + '/array_extension')
Array.send :include, ArrayExtension

class MultiProcessSpecRunner

  def initialize(max_concurrent_processes = 10)
    @max_concurrent_processes = max_concurrent_processes
  end
  
  def run(spec_files)
    concurrent_processes = [ @max_concurrent_processes, spec_files.size ].min
    spec_files_by_process = spec_files / concurrent_processes
    concurrent_processes.times do |i|
      cmd  = "spec #{options(i)} #{spec_files_by_process[i].join(' ')}"
      puts "Launching #{cmd}"
      exec(cmd) if fork == nil
    end
    success = true
    concurrent_processes.times do |i|
      pid, status = Process.wait2
      puts "Test process ##{i} with pid #{pid} completed with #{status}"
      success &&= status.exitstatus.zero?
    end
    
    script = File.expand_path(File.dirname(__FILE__) + "/aggregate_reports.rb")
    reports = Dir[screenshot_dir + "/Selenium-Build-Report-*.html"].collect {|report| %{"#{report}"} }.join(' ')
    command = %{ruby "#{script}" #{reports} > "#{screenshot_dir}/Aggregated-Selenium-Report.html"}
    sh command
    
    raise "Build failed" unless success
  end

  protected
 
  def options(process_number)
    "--require 'spec/ui' --format='Spec::Ui::ScreenshotFormatter:#{screenshot_dir}/Selenium-Build-Report-#{process_number}.html'"
  end
  
  def screenshot_dir
    ENV['CC_BUILD_ARTIFACTS'] || File.expand_path(File.dirname(__FILE__) + "/../reports")
  end
    
end
