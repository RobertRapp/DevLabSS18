# Erläuterung

Die Klasse StreamingExecution stellt den Startpunkt der Anwendung dar.
Über den StreamingContext werden die benötigten Informationen
abgerufen, die für den Aufbau des DStreams erforderlich sind. 
Mit Hilfe des JobListener ist es möglich, den DStream zu loggen.

![Event](https://github.com/PeterThies/Event-Processing/blob/IngoTrautwein/diagrams/eventprocessing/consume/Consume_Spark_Streaming_1.JPG)

* Die StreamingExecution ist für die Ausführung der DStream-Verarbeitung verantwortlich
* Von jeden Agenten wird das Window ausgelesen und in der Transformation des DStreams implementiert.
* Für die Filterung der eingehenden Nachrichten werden mit Funktionen die eingehenden Nachrichten gefiltert.

![Event](https://github.com/PeterThies/Event-Processing/blob/IngoTrautwein/diagrams/eventprocessing/consume/Consume_Spark_Streaming_2.JPG)

* Die StreamingExecution beinhaltet einen StreamingContext mit dem die DStream-Verarbeitung ermöglicht.
* Der StreamingContext beinhaltet alle relevanten Informationen um einen Spark-/Streaming Context zu erzeugen.
* Die StreamingContextValues beinhalten alle Parameter, für die Erzeugung eines StreamingContext.
* Der JobListener kann dem DStream hinzugefügt werden, um Logging-Informationen über den Streaming-Prozess zu erhalten.
* In der AgentRegistry sind alle Agenten registiert, für die ein DStream aufgebaut wird.
