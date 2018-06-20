# Package-Struktur

## Agent

Alle relevanten Klassen für die Realisierung eines Event Processing Agents.
Dazu gehören die Interessenprofile für die Filterung sowie Aufbereitung eingehender Events.
Der Dispatcher verteilt die eingehenden Nachrichten an die Interessenprofile.

### InterestProfile

Die InteressenProfile dienen den Agenten für die Interesse an bestimmten Nachrichten.
Sie drücken ihr Interesse an Nachrichten durch die Prädikatenlogik aus.

### Dispatch

Der Dispatcher verteilt die Nachrichten an alle Interessenprofile. Aus den Predikaten der Interessenprofile wird eine Filterqueue erzeugt, die überprüft, ob eine Nachricht weitergeleitet werden soll oder nicht.

## Demo

Beinhaltet eine beispielhafte Nutzung des Frameworks.

## Events

Agenten können Ereignisse verarbeiten. Die empfangenen Nachrichten werden in Event-Objekte überführt, damit eine Verarbeitung möglich ist.

## Consume

Für die Verarbeitung von Nachrichten wird über den SparkStreamingContext ein StreamingProzess erstellt, um die Nachrichten von einer externen Quelle wie Kafka abrufen zu können. Hier befinden sich alle Klassen, die für das Abonnieren von Topics eines Kafka-clusters notwendig sind.

## Produce

Agenten können produzierte Events auf eine externe Quelle senden, die dann wieder von anderen Agenten abgerufen werden können.
Hier befinden sich die Klassen für den Verbindungsaufbau zu Kafka, um Nachrichten auf Topics zu veröffentlichen.

## Utils

Beinhaltet alle Klassen, die als Unterstützung dienen. Dazu gehören die Mapper-Klassen, Factories für die Erzeugung der Events und Agenten sowie Werkzeuge um beispielsweise die Handhabung der equals()-Methode zu erleichtern.

