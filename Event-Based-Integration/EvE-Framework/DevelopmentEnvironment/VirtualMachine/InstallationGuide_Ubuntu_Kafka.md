# Installation Kafka

## Step 1 - Installation Java:

Öffnen des Terminal und überprüfung der Java-Version:
```
java -version
```
Wenn kein Java installiert ist, mit dem Befehl:
```
sudo apt-get install openjdk-8-jre
```
Java installieren.

## Step 2 - Installation Kafka:

Mit:
```
wget apache.mirror.digionline.de/kafka/1.0.0/kafka_2.11-1.0.0.tgz
```
Kann Kafka von der Homepage heruntergeladen werden.

Im Anschluss mit:
```
sudo mkdir /opt/Kafka
```
Das Verzeichnis für die Kafkainstallation anlegen.
Jetzt kann das heruntergeladene Archiv mit:
```
sudo tar -xvf kafka_2.11-1.0.0.tgz -C /opt/Kafka/
```
in das zuvor angelegte Verzeichnis entpackt werden.

## Step 3 - Setzen der Pathvariable

Im Homeverzeichnis des Benutzers kann über
```
cat >> .profile
```
die Umgebungsvariable:
```
export KAFKA_HOME=/opt/Kafka/kafka_2.11-1.0.0
```
hinzugefügt werden. 
Der Bearbeitungsmodus kann mit ```STRG+C``` wieder verlassen werden.
Die Pfadveriable ist nach einem Neustart des Servers verfügbar:
```
reboot
```

## Step 4 - Starten der Server

Kafka nutzt ZooKeeper, also muss als erstes ein ZooKeeper Server gestartet werden.
Der -daemon Parameter bewirkt, dass der Dienst im Hintergrund gestartet wird.
```
sudo $KAFKA_HOME/bin/zookeeper-server-start.sh -daemon $KAFKA_HOME/config/zookeeper.properties
```
Im Anschluss kann mit dem Befehl:
```
netstat -ant | grep :2181
```
geprüft werden, ob der Dienst gestartet wurde. Die Ausgabe sieht wie folgt aus:
![Status](https://github.com/PeterThies/Event-Processing/blob/master/DevelopmentEnvironment/VirtualMachine/images/Installation_check_zookeeper.JPG)
Anschließend kann der Kafka Server gestartet werden:
```
sudo $KAFKA_HOME/bin/kafka-server-start.sh -daemon $KAFKA_HOME/config/server.properties
```
Im Anschluss kann mit dem Befehl:
```
netstat -ant | grep :2181
```
geprüft werden, ob der Dienst gestartet wurde. Die Ausgabe sieht wie folgt aus:
![Status](https://github.com/PeterThies/Event-Processing/blob/master/DevelopmentEnvironment/VirtualMachine/images/Installation_check_kafka.JPG)
## Step 5 - häufige Befehle (optional)

```
# Listet alle Topics auf
$KAFKA_HOME/bin/kafka-topics.sh --zookeeper zookeeper:2181 --list

# Erzeugt ein Topic: Name: Test, Replication: 1 und Partition: 1
$KAFKA_HOME/bin/kafka-topics.sh --create --zookeeper zookeeper:2181 --replication-factor 1 --partitions 1 --topic test

# Auf das Topic test können über die Konsole Nachrichten geschrieben werden.
$KAFKA_HOME/bin/kafka-console-producer.sh --broker-list 10.142.0.2:9092 --topic test

# Alle Nachrichten des Topic "test" werden abgerufen.
$KAFKA_HOME/bin/kafka-console-consumer.sh --bootstrap-server 10.142.0.2:9092 --topic test --from-beginning
```
