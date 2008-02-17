module SeleniumGrid
  module AWS
    module Ec2Client
      extend Forwardable
      
      DEFAULT_AUTHORIZE_OPTIONS = {:ip_protocol => "tcp", :group_name => "default" }

      # def_delegator :driver, :describe_instances, :describe_instances

      def describe_instances(options)
        driver.describe_instances options
      end
            
      def run_instance(options)
        response = driver.run_instances(:image_id => options[:image_id])
        response.instancesSet.item.first.instanceId        
      end
            
      def authorize(options)
        actual_options = DEFAULT_AUTHORIZE_OPTIONS.merge(options)
        actual_options[:from_port] = actual_options[:to_port] = options[:port]
        puts "Opening port #{options[:port]} for #{actual_options[:group_name]} group..."        
        driver.authorize_security_group_ingress(actual_options)
      end
      
      def driver
        return @ec2_driver unless @ec2_driver.nil?
        
        access_key_id = ENV["AMAZON_ACCESS_KEY_ID"]
        secret_access_key = ENV["AMAZON_SECRET_ACCESS_KEY"]
        if access_key_id.nil? 
          access_key_id, secret_access_key = IO.read("/Volumes/Private Disk/New Stuff Mac Only/Amazon EC2/keys.txt").chop.split(/ /)
        end        
        @ec2_driver = EC2::Base.new(:access_key_id => access_key_id, :secret_access_key => secret_access_key)
      end
      
    end
  end
end
