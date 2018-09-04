#!/bin/bash

# ENV 환경 변수 지정
if [[ $DEPLOYMENT_GROUP_NAME = *"Live"* ]]; then
    ENV="prod"
    PINPOINT_DEPLOY=""
elif [[ $DEPLOYMENT_GROUP_NAME = *"Pre"* ]]; then
    ENV="stg"
    PINPOINT_DEPLOY=".pre"
elif [[ $DEPLOYMENT_GROUP_NAME = *"Stage"* ]]; then
    ENV="stg"
    PINPOINT_DEPLOY=""
else
    ENV="dev"
    PINPOINT_DEPLOY=".dev"
fi

ISID=$(curl http://169.254.169.254/latest/meta-data/instance-id)

#echo $ENV
if [[ -f '/opt/codedeploy-agent/deployment-root/'$DEPLOYMENT_GROUP_ID'/'$DEPLOYMENT_ID'/deployment-archive/setenv.sh' ]]; then
    perl -pi -e 's/#param#/'"$ENV"'/g' /opt/codedeploy-agent/deployment-root/$DEPLOYMENT_GROUP_ID/$DEPLOYMENT_ID/deployment-archive/setenv.sh
    perl -pi -e "s/#isid#/$ISID/g" /opt/codedeploy-agent/deployment-root/$DEPLOYMENT_GROUP_ID/$DEPLOYMENT_ID/deployment-archive/setenv.sh
    perl -pi -e "s/#pinpoint-deploy#/$PINPOINT_DEPLOY/g" /opt/codedeploy-agent/deployment-root/$DEPLOYMENT_GROUP_ID/$DEPLOYMENT_ID/deployment-archive/pinpoint.config

fi

