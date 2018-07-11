# Erläuterung

Hier befinden sich alle Funktionen, die für die Verarbeitung des DStreams verwendet werden.

![Event](https://github.com/PeterThies/Event-Processing/blob/IngoTrautwein/diagrams/eventprocessing/consume/Consume_Spark_Streaming_Function.JPG)

* Die StreamingExecution greift auf die Funktionen zurück, um eingehende Nachrichten in einem DStream zu transformieren.
* IsMessageOfInterest übergibt die eingehende Nachricht an den Dispatcher, der für jedes Interessenprofil der Agenten individuell anhand der Prädikaten prüft, ob das Interessenprofil an der Nachricht interessiert ist. Ist das Interessenprofil interessiert, übergibt der Dispatcher die Nachricht an das Interessenprofil, ansonsten wird die Nachricht verworfen.
* ExtractMessage trennt aus dem eingehenden Datensatz die Nachricht von den Metadaten.
* IsJson überprüft ob die Nachrichten ein valides Json-Format aufweisen, dabei wird auf die Json-Utils zurückgegriffen  
