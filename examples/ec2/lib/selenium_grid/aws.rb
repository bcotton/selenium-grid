#
# 
#

require 'rubygems'
require 'ec2'
require "yaml"




module SeleniumGrid
  module AWS
  
    class Server
      attr_accessor :instance_id, :public_dns, :private_dns
    
      def initialize(instance_id)
        self.instance_id = instance_id
      end       

      def self.boot(image_id)
        response = ec2.run_instances(:image_id => image_id)
        Server.new response.instancesSet.item.first.instanceId
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
        response = self.class.ec2.describe_instances :instance_id => instance_id
        instance_info = response.reservationSet.item.first.instancesSet.item.first
        { :public_dns => instance_info.dnsName, 
          :private_dns => instance_info.privateDnsName,
          :status => instance_info.instanceState.name
        }
      end
      
      def self.ec2
        ACCESS_KEY_ID = ENV["AMAZON_ACCESS_KEY_ID"]
        SECRET_ACCESS_KEY = ENV["AMAZON_SECRET_ACCESS_KEY"]
        if ACCESS_KEY_ID.nil? 
          ACCESS_KEY_ID, SECRET_ACCESS_KEY = IO.read("/Volumes/Private Disk/New Stuff Mac Only/Amazon EC2/keys.txt").chop.split(/ /)
        end        
        @ec2 ||= EC2::Base.new(:access_key_id => ACCESS_KEY_ID, :secret_access_key => SECRET_ACCESS_KEY)
      end
      
    end

    class Cloud
      FILE = "cloud.yml"
      attr_accessor :hub
      
      def self.load
        begin
          YAML.load(File.read(FILE))
        rescue Errno::ENOENT
          new
        end
      end

      def self.update
        cloud = self.load
        yield cloud
      ensure
        cloud.write unless cloud.nil?
      end
      
      def write 
        File.open(FILE, "w") {|file| file.write(self.to_yaml)}
      end
            
    end

  end
end

