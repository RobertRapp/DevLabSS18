# Erläuterung

Der Dispatcher verteilt die Nachrichten die aus dem DStream ankommen
an die InteressenProfile. Mit der FilterQueue wird auf eingehende Nachrichten die Prädikatenlogik angewandt, um uninteressante Nachrichten zu filtern.

![Dispatcher](https://github.com/PeterThies/Event-Processing/blob/IngoTrautwein/diagrams/eventprocessing/agent/Agent_Dispatcher.JPG)

* Der Dispatcher ist einem Agenten zugeordnet.
* Der Dispatcher fügt jedes Interessenprofil des Agent einem IPFilterQPair hinzu.
* Mit den CollectionUtils prüft der Dispatcher, ob sich Prädikate im Interessenprofil befinden.
* Ein IPFilterQPair besteht aus einem Interessenprofil sowie einer FilterQueue
* Die Prädikate des Interessenprofils werden als Filterfunktionen der FilterQueue hinzugefügt.
* Der Dispatcher wird in der DStream-Verarbeitung aufgerufen, um die eingehende Nachricht durch die FilterQueue zu filtern.
