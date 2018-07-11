# Erläuterung

Die Agenten drücken durch die Interessenprofile das Interesse an Nachrichten aus.
Jedes Interessenprofil kann mehrere Predicates beinhalten, welches die Kriterien
vorhalten, welche Nachrichten Interessant sind und somit weiterverarbeitet werden.

![InterestProfile](https://github.com/PeterThies/Event-Processing/blob/IngoTrautwein/diagrams/eventprocessing/agent/InterestProfile.JPG)

* Ein Interessenprofil ist genau einem Agenten zugeordnet.
* Das Interessenprofil wird dem Dispatcher des Agenten zugewiesen.
* Das Interesse an Nachrichten wird durch Prädikate ausgedrückt.
