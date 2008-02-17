module SeleniumGrid
  module AWS
  
    class Server
      extend Ec2Client
      
      attr_accessor :instance_id, :public_dns, :private_dns
    
      def initialize(instance_id)
        self.instance_id = instance_id
      end       

      def self.boot(image_id)
        new(run_instance(:image_id => image_id))
      end

      def self.boot_and_acquire_dns(image_id)
        server = boot image_id
        puts "Fetching DNS Info..."
        until server.dns_allocated?
          sleep 2
          putc "."
          server.refresh_instance_info
        end
        puts
      end

      def dns_allocated?
        public_dns != nil && public_dns != "" &&
        private_dns != nil && private_dns != ""
      end
      
      def refresh_instance_info
        instance_info = fetch_instance_info
        @public_dns = instance_info[:public_dns]
        @private_dns = instance_info[:private_dns]
      end
      
      def fetch_instance_info
        response = self.class.driver.describe_instances :instance_id => instance_id
        instance_info = response.reservationSet.item.first.instancesSet.item.first
        { :public_dns => instance_info.dnsName, 
          :private_dns => instance_info.privateDnsName,
          :status => instance_info.instanceState.name
        }
      end
            
    end

  end
end

