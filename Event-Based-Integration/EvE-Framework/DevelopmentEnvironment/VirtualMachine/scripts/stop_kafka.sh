#! /bin/sh

# Stoppt den Zookeeperserver.

echo "stop zookeeper"
sudo $KAFKA_HOME/bin/zookeeper-server-stop.sh
echo "Done."