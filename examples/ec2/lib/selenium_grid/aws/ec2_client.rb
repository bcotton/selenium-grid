module SeleniumGrid
  module AWS
    module Ec2Client
      
      def describe(instance_id)
        output = ec2_shell "ec2-describe-instances #{instance_id}"
        output =~ /INSTANCE\s+(i-.*)$/
        fields = $1.split(/\s+/)
        if output =~ /running/
          {:instance_id => fields[0],
           :ami => fields[1],
           :public_dns => fields[2],
           :private_dns => fields[3],
           :status => fields[4] }
        else 
          {:instance_id => fields[0],
           :ami => fields[1],
           :status => fields[2] }
        end
      end
            
      def launch(ami, options ={})
        output = ec2_shell "ec2-run-instances #{ami} -k #{options[:keypair]}"
        output =~ /INSTANCE\s+(i-\S+)\s+ami-/
        $1
      end
            
      def authorize_port(port)
        puts "Opening port #{port}..."        
        ec2_shell "ec2-authorize default -p #{port}"
      end
            
      def ec2_shell(command)
        puts 
        puts command
        output = `${EC2_HOME}/bin/#{command}`
        # raise "Error running command '#{command}" unless 0 == $?
        puts ">>>> got"
        puts output
        puts ">>>>>"
        output
      end
      
    end
  end
end
