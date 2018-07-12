# Erläuterung

Darstellung der Events.
Das AbstractEvent stellt das allgemeine Event dar.
AtomicEvent stellt einzelnes Event dar, während das
ComplexEvent ein Erzeugnis ist, an dem mehrere Events beteiligt
sind (AtomicEvents sowie ComplexEvents).
Über die Properties können dem Event weitere Eigenschaften hinzugefügt werden,
die das Event detailierter beschreiben können.

![Event](https://github.com/PeterThies/Event-Processing/blob/IngoTrautwein/diagrams/eventprocessing/events/Event.jpg)

* Über den EventIdProvider erhält das Event eine eindeutige ID. Der EventIdProvider zählt intern die vergebene Nummer hoch.
* Eine NoValidIdException wird geworfen, wenn eine versucht wird eine id kleiner 0 dem Event zuzuweisen.
* Eine NoValidPropertyException wird geworfen, wenn versucht wird, ein null-Objekt als Property zuzuweisen.
* Properties beschreiben das Event. Ein Event kann aus keinem oder mehreren Properties bestehen.
* Ein Event muss serialisierbar sein, damit es über das Netzwerk versendet werden kann.
* Ein Event ist entweder ein AtomicEvent, welches ein Event darstellt, welches keine Beziehung zu anderen Events besitzt. Oder es ist ein Event vom Typ ComplexEvent und steht zu anderen Events in Relation.

![Event_to_Agent](https://github.com/PeterThies/Event-Processing/blob/IngoTrautwein/diagrams/eventprocessing/events/Event_to_Agent.JPG)

* Events werden von Agenten verarbeitet
* Der Agent drückt sein Interesse an Events durch InteressenProfile aus
* Ein Agent besitzt ein oder mehrere Interessenprofile
* Die Interessenprofile stellen die Filtereinheit für den Agenten dar.

![Event_to_Util](https://github.com/PeterThies/Event-Processing/blob/IngoTrautwein/diagrams/eventprocessing/events/Event_to_Util.JPG)

* Der MessageMapper konvertiert das Event zu einem String oder einen String zu einem Event.
* Über den DateTimeMapper ruft das Event die aktuelle Zeit ab.
* EventUtils beinhaltet alle Methoden um Eigenschaften zu finden oder Events untereinander zu vergleichen.
* Die EventFactory erzeugt Instanzen von Events. Es garantiert durch diese Erzeugung mit zuhilfenahme des EventIdProviders eine eindeutige id für das Event.
