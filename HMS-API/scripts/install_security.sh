#!/bin/bash

if [[ $DEPLOYMENT_GROUP_NAME = *"-Dev"* || $DEPLOYMENT_GROUP_NAME = *"-Stg"* ]]; then
    echo "Skip Install Security Apps..."
    exit 0
fi

# reject os updates
sudo apt-mark hold linux-image-generic linux-headers-generic

# setup filebeat
echo "Setup Filebeat..."
if [[ ! -f "/etc/filebeat/filebeat.yml" || $(pgrep filebeat) == "" ]]; then
    curl -L -O https://artifacts.elastic.co/downloads/beats/filebeat/filebeat-6.3.0-amd64.deb
    sudo dpkg -i filebeat-6.3.0-amd64.deb
    sudo mv /opt/codedeploy-agent/deployment-root/$DEPLOYMENT_GROUP_ID/$DEPLOYMENT_ID/deployment-archive/filebeat.yml /etc/filebeat/filebeat.yml
    sudo chown -R root:root /etc/filebeat/*
    sudo service filebeat start
else
    if [[ $(cmp -s /opt/codedeploy-agent/deployment-root/$DEPLOYMENT_GROUP_ID/$DEPLOYMENT_ID/deployment-archive/filebeat.yml /etc/filebeat/filebeat.yml || echo 'diff') == "diff" ]]; then
        sudo mv /opt/codedeploy-agent/deployment-root/$DEPLOYMENT_GROUP_ID/$DEPLOYMENT_ID/deployment-archive/filebeat.yml /etc/filebeat/filebeat.yml
        sudo chown root:root /etc/filebeat/filebeat.yml
        sudo service filebeat restart
    fi
fi

# setup auditbeat
echo "Setup Auditbeat..."
if [[ ! -f "/etc/auditbeat/auditbeat.yml" || $(pgrep auditbeat) == "" ]]; then
    curl -L -O https://artifacts.elastic.co/downloads/beats/auditbeat/auditbeat-6.3.0-amd64.deb
    sudo dpkg -i auditbeat-6.3.0-amd64.deb
    sudo mv /opt/codedeploy-agent/deployment-root/$DEPLOYMENT_GROUP_ID/$DEPLOYMENT_ID/deployment-archive/auditbeat.yml /etc/auditbeat/auditbeat.yml
    sudo chown -R root:root /etc/auditbeat/*
    sudo service auditbeat start
else
    if [[ $(cmp -s /opt/codedeploy-agent/deployment-root/$DEPLOYMENT_GROUP_ID/$DEPLOYMENT_ID/deployment-archive/auditbeat.yml /etc/filebeat/auditbeat.yml || echo 'diff') == "diff" ]]; then
        sudo mv /opt/codedeploy-agent/deployment-root/$DEPLOYMENT_GROUP_ID/$DEPLOYMENT_ID/deployment-archive/auditbeat.yml /etc/auditbeat/auditbeat.yml
        sudo chown root:root /etc/auditbeat/auditbeat.yml
        sudo service filebeat restart
    fi
fi

# setup packetbeat
echo "Setup Packetbeat..."
if [[ ! -f "/etc/packetbeat/packetbeat.yml" || $(pgrep packetbeat) == "" ]]; then
    sudo apt-get install libpcap0.8
    curl -L -O https://artifacts.elastic.co/downloads/beats/packetbeat/packetbeat-6.3.0-amd64.deb
    sudo dpkg -i packetbeat-6.3.0-amd64.deb
    sudo mv /opt/codedeploy-agent/deployment-root/$DEPLOYMENT_GROUP_ID/$DEPLOYMENT_ID/deployment-archive/packetbeat.yml /etc/packetbeat/packetbeat.yml
    sudo chown -R root:root /etc/packetbeat/*
    sudo service packetbeat start
else
    if [[ $(cmp -s /opt/codedeploy-agent/deployment-root/$DEPLOYMENT_GROUP_ID/$DEPLOYMENT_ID/deployment-archive/packetbeat.yml /etc/filebeat/packetbeat.yml || echo 'diff') == "diff" ]]; then
        sudo mv /opt/codedeploy-agent/deployment-root/$DEPLOYMENT_GROUP_ID/$DEPLOYMENT_ID/deployment-archive/packetbeat.yml /etc/packetbeat/packetbeat.yml
        sudo chown root:root /etc/packetbeat/packetbeat.yml
        sudo service auditbeat restart
    fi
fi

# setup ds agent
echo "Setup DS agent..."
ISTYPE=$(curl http://169.254.169.254/latest/meta-data/instance-type)

if [[ $ISTYPE == 't2.micro' || $ISTYPE == 't2.small' ]]; then
    echo "Instance Type is too small..."
    exit 0
fi

# This script detects platform and architecture, then downloads and installs the matching Deep Security Agent package
 if [[ $(/usr/bin/id -u) -ne 0 ]]; then echo You are not running as the root user.  Please try again with root privileges.;
    logger -t You are not running as the root user.  Please try again with root privileges.;
    exit 1;
 fi;
 if type curl >/dev/null 2>&1; then
  SOURCEURL='https://10.49.41.70:4119'
  curl $SOURCEURL/software/deploymentscript/platform/linux/ -o /tmp/DownloadInstallAgentPackage --insecure --silent --tlsv1.2

  if [ -s /tmp/DownloadInstallAgentPackage ]; then
      . /tmp/DownloadInstallAgentPackage
      Download_Install_Agent
  else
     echo "Failed to download the agent installation script."
     logger -t Failed to download the Deep Security Agent installation script
     false
  fi
 else 
  echo "Please install CURL before running this script."
  logger -t Please install CURL before running this script
  false
 fi
sleep 15
/opt/ds_agent/dsa_control -r
/opt/ds_agent/dsa_control -a dsm://10.49.41.70:4120/ "policyid:10"