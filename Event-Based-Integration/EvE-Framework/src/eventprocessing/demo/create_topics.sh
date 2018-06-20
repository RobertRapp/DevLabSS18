#! /bin/sh

# Das Bashskript kann über das Terminal auf dem Linuxsystem ausgeführt werden.
# $KAFKA_HOME stellt das Verzeichnis dar, indem Kafka installier ist. Die Umgebungsvariable muss entsprechend erst angelegt sein.

echo "Create topics..."
	for TOPIC in Sensor-1 Sensor-2 TrafficData Diagnosis Storage Logging
	do
		echo "Create topic $TOPIC."
		$KAFKA_HOME/bin/kafka-topics.sh --create --zookeeper localhost:2181 --replication-factor 1 --partitions 1 --topic $TOPIC
	done
echo "Done."
