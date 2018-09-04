# Default
JAVA_OPTS="-Dspring.profiles.active=#param# -server -Djava.awt.headless=true -Djava.net.preferIPv4Stack=true "
# Memory Size
ISTYPE=$(curl http://169.254.169.254/latest/meta-data/instance-type)
if [ $ISTYPE = 't2.micro' ]; then
    JAVA_OPTS="${JAVA_OPTS} -Xms256M -Xmx512M -XX:NewSize=64m -XX:MaxNewSize=128m -XX:MetaspaceSize=64m -XX:MaxMetaspaceSize=128m"
else
    JAVA_OPTS="${JAVA_OPTS} -Xms1024M -Xmx2048M -XX:NewSize=512m -XX:MaxNewSize=512m -XX:MetaspaceSize=256m -XX:MaxMetaspaceSize=512m"
fi
# GC option
JAVA_OPTS="${JAVA_OPTS} -XX:+DisableExplicitGC -verbose:gc -XX:-PrintGC -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -Xloggc:/var/log/tomcat8/gc.log -XX:ParallelGCThreads=2 -XX:-UseConcMarkSweepGC"
# pinpoint
JAVA_OPTS="${JAVA_OPTS} -javaagent:/home/ubuntu/pinpoint-agent/pinpoint-bootstrap-1.7.3.jar -Dpinpoint.agentId=#isid# -Dpinpoint.applicationName=HMS-API"