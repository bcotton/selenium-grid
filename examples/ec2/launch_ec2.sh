#!/bin/sh
# 
# ph7 - Launch EC2 image


EC2_HOME="/Local/Applications/Installed/ec2-api-tools-1.2-13740"
EC2_PRIVATE_KEY="/Volumes/Private Disk/New Stuff Mac Only/Amazon EC2/pk-5CCEI7ODBPYGRXYOAZWGPVCG5LJ6HLGE.pem"
EC2_CERT="/Volumes/Private Disk/New Stuff Mac Only/Amazon EC2/cert-5CCEI7ODBPYGRXYOAZWGPVCG5LJ6HLGE.pem"
export EC2_HOME
export EC2_PRIVATE_KEY
export EC2_CERT

#AMI=ami-7034d119
AMI=ami-763edb1f
ec2-run-instances  ${AMI} -k grid-keypair
#sleep 20
#ec2-describe-instances
#IP=`ec2-describe-instances | tail -1| sed -e 's/.*\(ec2-.*amazonaws.com\).*/\1/'`
#ec2-revoke default -p 22
#ec2-authorize default -p 22
#ssh -i id_rsa-grid-keypair root@${IP}
