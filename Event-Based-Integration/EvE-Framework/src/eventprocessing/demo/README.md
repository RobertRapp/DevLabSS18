# Anleitung

Für die Fallstudie dient der Bereich der Verkehrskontrolle und -steuerung.
Verkehrskontrollsysteme basieren meist auf Sensoriken, die über eine Strecke verteilt platziert sind.
Die Betrachtung beschränkt sich hier auf den Straßenverkehr. 
Grundlage für Informationssysteme bilden aktuelle Informationen der Verkehrsstrecke. 
In dieser Demo wird konkret eine Straße betrachtet, die mit zwei Sensoren ausgestattet sind.
Diese beiden Sensoren werden in dem Moment ausgelöst, wenn ein Fahrzeug den Sensor passiert hat.

Um Erkenntnisse über das Verkehrsgeschehen sowie eine Prognose über die Entwicklung liefern zu können,
müssen charakteristische Kennzahlen erhoben werden, dazu gehören beispielsweise die Verkehrsstärke, -dichte udn die Durchschnittsgeschwindigkeit.
Die Demo beschränkt sich auf die Analyse der Durchschnittsgeschwindigkeit eines Fahrzeuges zwischen zwei Sensoren.

Anhand dieser erhobenen Kennzahl muss eine qualitative Entschluss gezogen werden, ob eine Überschreitung der Höchstgeschwindigkeit vorliegt oder nicht.

Plastische Darstellung der Kommunikation:

![Image of demo](https://github.com/PeterThies/Event-Processing/blob/IngoTrautwein/src/eventprocessing/demo/Demo.JPG)

## Voraussetzung

Damit dies Demo lauffähig ist, müssen folgende Schritte geprüft werden:

  1. Setzen der korrekten IPv4-Adresse für die Agenten zum Abrufen der Nachrichten von Topics. 
      1. [ConsumerSettings-DiagnosisAgent](https://github.com/PeterThies/Event-Processing/blob/Test-StreamRestart_2/src/eventprocessing/demo/agents/diagnosis/ConsumerSettingsDiagnosis.java)
      1. [ConsumerSettings-TrafficAnalysis](https://github.com/PeterThies/Event-Processing/blob/Test-StreamRestart_2/src/eventprocessing/demo/agents/TrafficAnalysis/ConsumerSettingsTrafficAnalysis.java)
      1. [ConsumerSettings-SensorProcessing](https://github.com/PeterThies/Event-Processing/blob/Test-StreamRestart_2/src/eventprocessing/demo/agents/SensorProcessing/ConsumerSettingsSensorProcessing.java)
      1. [ConsumerSettings-KafkaRunner](https://github.com/PeterThies/Event-Processing/blob/Test-StreamRestart_2/src/eventprocessing/consume/kafka/runner/KafkaConsumerProperties.java)
1. Setzen der korrekten IPv4-Adresse für das Versenden der Nachrichten an Topics. 
      1. [ProducerSettings-DiagnosisAgent](https://github.com/PeterThies/Event-Processing/blob/Test-StreamRestart_2/src/eventprocessing/demo/agents/diagnosis/ProducerSettingsDiagnosis.java)
      1. [ProducerSettings-TrafficAnalysis](https://github.com/PeterThies/Event-Processing/blob/Test-StreamRestart_2/src/eventprocessing/demo/agents/TrafficAnalysis/ProducerSettingsTrafficAnalysis.java)
      1. [ProducerSettings-SensorAgent](https://github.com/PeterThies/Event-Processing/blob/Test-StreamRestart_2/src/eventprocessing/demo/agents/SensorProcessing/ProducerSettingsSensorProcessing.java)

  1. Alle nachfolgenden Topics müssen bereits auf dem Kafkaserver angelegt sein. Ein entsprechendes Skript für die Erzeugung der Topics gibt es [hier](https://github.com/PeterThies/Event-Processing/blob/IngoTrautwein/src/eventprocessing/demo):
      1. Sensor-1
      1. Sensor-2
      1. TrafficData
      1. Diagnosis
      1. Storage
      1. Logging

  1. Der Kafka-Server muss gestartet sein und über das Netzwerk erreichbar sein. [Installation Kafka](https://github.com/PeterThies/Event-Processing/blob/IngoTrautwein/DevelopmentEnvironment/VirtualMachine/InstallationGuide_Ubuntu_Kafka.md)
  1. Anlegen einer Run Configuration in Eclipse.
      1. Java-Application
      1. Project: ```Event-Processing```
      1. Main class: ```eventprocessing.demo.Showcase```
  
