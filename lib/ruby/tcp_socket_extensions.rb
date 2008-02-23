require 'timeout'
require 'socket'

class TCPSocket
  
  def self.wait_for_service(options)
    Timeout::timeout(options[:timeout] || 20) do
      loop do
        begin
          socket = TCPSocket.new(options[:host], options[:port])
          return
        rescue Errno::ECONNREFUSED
          puts ".\n"
          sleep 2
        end
      end
    end
  end
  
end