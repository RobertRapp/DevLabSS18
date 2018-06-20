# Erläuterung

Der KafkaConsumerRunner baut einen separaten Kanal zu Kafka auf.
Über diesen Kanal können unabhänging von der DStream-Verarbeitung Events empfangen werden.
Diese Events können Befehle für die Agenten beinhalten, um diese beispielsweise in einen
Zustand zu versetzen, um alle empfangenen Events zu zählen.