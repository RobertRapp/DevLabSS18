# Erläuterung

Alle Prädikate, die auf eine Nachricht angewandt werden, 
um auf einen bestimmten Inhalt von Eigenschaften-Wert Paaren zu überprüfen.

![StatementPredicate](https://github.com/PeterThies/Event-Processing/blob/IngoTrautwein/diagrams/eventprocessing/agent/StatementPredicate.JPG)

* Prüft eingehende Nachrichten ob bestimmte Eigenschaften sowie Werte vorhanden sind.
* Das FilterAttribute beinhaltet die gesuchte Eigenschaft-Wertpaarung.
* IsEventType prüft, ob ein Event von einem bestimmten Typ ist. ("type":"value"
* IsTopic prüft, ob ein Event von einem bestimmten Topic kommt. ("source":"topic")
* GetEverything nimmt alles entgegen. ("")
* HasProperty sucht nach einer bestimmten Eigenschaft. ("Property":)
* HasPropertyContains sucht nach einer Paarung von Eigenschaft und dazugehörigen Wert. ("Property":"Value")
