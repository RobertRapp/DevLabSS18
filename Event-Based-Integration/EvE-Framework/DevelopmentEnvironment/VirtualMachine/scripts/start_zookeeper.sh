#! /bin/sh

# startet den Zookeeperserver im Hintergrund.

echo "start zookeeper"
sudo $KAFKA_HOME/bin/zookeeper-server-start.sh -daemon $KAFKA_HOME/config/zookeeper.properties
echo "Done."