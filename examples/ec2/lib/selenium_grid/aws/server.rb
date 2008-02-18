module SeleniumGrid
  module AWS
  
    class Server
      extend Ec2Client
      
      attr_accessor :instance_id, :public_dns, :private_dns
    
      def initialize(instance_id)
        self.instance_id = instance_id
      end       

      def self.boot(ami, options = {})
        new launch(ami, options)
      end

      def self.boot_and_acquire_dns(ami, options)
        server = boot(ami, options)
        server.wait_for_dns
      end

      def wait_for_dns
        puts "Fetching DNS Info..."
        until dns_allocated?
          sleep 2
          putc "."
          refresh_status
        end
        puts
        self
      end
      
      def dns_allocated?
        public_dns != nil && public_dns != "" &&
        private_dns != nil && private_dns != ""
      end
      
      def refresh_status
        info = self.class.describe instance_id
        @public_dns = info[:public_dns]
        @private_dns = info[:private_dns]
      end
      
      def run(command, options)
        actual_command = "ssh -i '#{options[:keypair]}' root@#{public_dns} '#{command}'"
        puts actual_command
        system actual_command
        raise "Error with #{command}" if 0 != $?
      end         
    end

  end
end

