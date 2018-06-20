#! /bin/sh

# Stoppt den Kafkaserver.

echo "stop kafka"
sudo $KAFKA_HOME/bin/kafka-server-stop.sh
echo "Done."