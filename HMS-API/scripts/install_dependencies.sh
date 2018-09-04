#!/bin/bash

set -e

#sudo apt-get update
#sudo apt-get -y install default-jdk
sudo apt-get -y install tomcat8

# Mornitoring Services
if [[ $DEPLOYMENT_GROUP_NAME = *"Live"* || $DEPLOYMENT_GROUP_NAME = *"Pre"* ]]; then
    sudo service filebeat restart || true
    sudo service packetbeat restart || true
    sudo service auditbeat restart || true
    sudo service ds_agent restart || true
else
    sudo service filebeat stop || true
    sudo service packetbeat stop || true
    sudo service auditbeat stop || true
    sudo service ds_agent stop || true
fi

# Instance metric collection
if [[ $(sudo service --status-all | grep -E "collectd") == "" ]]; then
  sudo apt-get -y install collectd
fi
rm -rf ./collectd-cloudwatch
git clone https://github.com/awslabs/collectd-cloudwatch
cd collectd-cloudwatch/src
sudo ./setup.py --non_interactive -m recommended

if [ -f "/var/lib/tomcat8/webapps/ROOT.war" ]; then
    sudo rm -f /var/lib/tomcat8/webapps/ROOT.war
fi

if [ -d "/var/lib/tomcat8/webapps/ROOT" ]; then
    sudo rm -rf /var/lib/tomcat8/webapps/ROOT
fi

rm -f /usr/share/tomcat8/bin/setenv.sh

/opt/codedeploy-agent/deployment-root/$DEPLOYMENT_GROUP_ID/$DEPLOYMENT_ID/deployment-archive/env_judge.sh

# Install pinpoint agent
rm -f /home/ubuntu/pinpoint-agent*.tar.gz
rm -rf /home/ubuntu/pinpoint-agent
cd /home/ubuntu/
mkdir -p pinpoint-agent
wget https://github.com/naver/pinpoint/releases/download/1.7.3/pinpoint-agent-1.7.3.tar.gz
tar xvfz pinpoint-agent*.tar.gz -C pinpoint-agent
rm -f /home/ubuntu/pinpoint-agent/pinpoint.config

# awslogs
sudo cp /var/awslogs/etc/awslogs.conf ./awslogs.conf
sudo chown ubuntu:ubuntu ./awslogs.conf
if [[ $(grep -i 'hms' ./awslogs.conf) == "" ]]; then
    cat <<EOF >>./awslogs.conf

[hms catalina log]
file = /var/log/tomcat8/catalina.out
buffer_duration = 5000
log_stream_name = {hostname} - {instance_id} - {ip_address} - hms catalina
initial_position = start_of_file
log_group_name = hms-api
EOF
    sudo cp ./awslogs.conf /var/awslogs/etc/awslogs.conf
fi
rm ./awslogs.conf

# hostname
sudo apt-get install -y awscli
ISID=$(curl 169.254.169.254/latest/meta-data/instance-id)
HOSTNAME=$(aws ec2 describe-tags --region ap-northeast-2 --filters "Name=resource-id,Values=$ISID" "Name=key,Values=Name" | python -c "import sys,json; print json.load(sys.stdin)['Tags'][0]['Value']")
echo "host name ... "$HOSTNAME

sudo hostnamectl set-hostname $HOSTNAME
cat <<EOF >./hosts
127.0.0.1 localhost
127.0.0.1 $HOSTNAME
EOF
sudo cp ./hosts /etc/hosts
rm ./hosts
sudo service networking restart
sudo service awslogs restart
sudo service codedeploy-agent restart