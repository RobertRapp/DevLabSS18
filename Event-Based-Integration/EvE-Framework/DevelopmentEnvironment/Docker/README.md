# Testumgebung in Docker

Statt einer vollwertigen virtuellen Maschine können auch Container eingesetzt werden.
Dies hat insbesondere den Vorteil, dass sich diese Umgebung mit einem einzelnen Befehl starten lässt.

## Spark

Lokale Installation sollte die Entwicklung vereinfachen.


## Kafka

### Requirements

* Docker
* Docker-Compose

### Umgebung starten

Die Umgebung wurde mithilfe von docker-compose definiert.
Mit dem folgenden Befehl wird sowohl ein Container für `Zookeeper` und ein Container für `Kafka` gestartet.

```sh
docker-compose up --no-recreate

# Container vollstaendig loeschen
docker-compose rm -f
```

#### Mapping der Ports

Für `Zookeeper` wird der Port `2181` direkt auf denselben Port des Hostsytems gemapped.
Für `Kafka` wird der Port `9092` direkt auf denselben Port des Hostsytems gemapped.

### Zugriff auf Kafka

Der Zugriff auf Kafka kann über das Hostsystem erfolgen.
So kann dazu das Kafka Paket heruntergeladen und genutzt werden, wie es in der [offiziellen Quickstart Anleitung](https://kafka.apache.org/quickstart) erläutert wird.
Für `macOS` kann alternativ auf Kafka via `Homebrew` installiert werden: `brew install kafka`.


#### Windows

Wenn der Container läuft, kann durch die Eingabe von `docker ps` in der Kommandozeile unter anderem der Status und die Container ID ausgelesen werden.

Nun wird mit dem Befehl `docker exec -ti xxx bash` auf den Container connected und dort eine bash Eingabe ermöglicht. `xxx` steht für die ersten drei Zeichen der Container ID. Hier bitte die Container ID vom kafka Container eingeben.

```
# List Topics

kafka-topics.sh --zookeeper zookeeper:2181 --list

# Create Topic
kafka-topics.sh --create --zookeeper zookeeper:2181 --replication-factor 1 --partitions 1 --topic test

# Publish Message
kafka-console-producer.sh --broker-list 10.142.0.2:9092 --topic test

# Subscribe to Topic
kafka-console-consumer.sh --bootstrap-server 10.142.0.2:9092 --topic test --from-beginning
```

#### Unix

```
# List Topics

kafka-topics --zookeeper 10.142.0.2:2181 --list

# Create Topic
kafka-topics --create --zookeeper 10.142.0.2:2181 --replication-factor 1 --partitions 1 --topic test

# Publish Message
kafka-console-producer --broker-list 10.142.0.2:9092 --topic test

# Subscribe to Topic
kafka-console-consumer --bootstrap-server 10.142.0.2:9092 --topic test --from-beginning
```
