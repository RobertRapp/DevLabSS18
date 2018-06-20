#! /bin/sh

# startet den Kafkaserver im Hintergrund.

echo "start kafka"
sudo $KAFKA_HOME/bin/kafka-server-start.sh -daemon $KAFKA_HOME/config/server.properties
echo "Done."