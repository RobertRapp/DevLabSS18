# Events
Dieser Abschnitt zeigt eine Übersicht aller verwendeten Events innerhalb der Projektgruppe "Eventbasierte Integration". 


##  
- [ApplicationEvent](#applicationevent)
- [ProtocolEvent](#protocolevent)
- [RequestEvent](#reqeustevent)
- [SessionContextEvent](#sessioncontextevent)



## ApplicationEvent
Enthält die Applikations-Anforderungen. Das Topic “DocProposal” wird von der GUI konsumiert. 
## ProtocolEvent
Enthält alle gesammelten Informationen zur aktuellen Session. Wird auf das Protocol-Topic gepusht. Dieses Topic wird von Dokumenten-Repräsentation abonniert. Erstellen daraus das Protokoll.
## RequestEvent
Enthält Dokumentenanfrage in Form von aktuellem Sessionkontext und semantisch angereicherten Chunks.
Properties: Projekt, Teilnehmer, Set von Schlüsselwörtern (zu Projekt, Person oder Dokument)
## SessionContextEvent
Wird mit Informationen über das Gespräch befüllt.


`

## TOPICS
Das ist eine Übersicht aller Topics innerhalb des Projekts.



| Topic | Events | Publisher | Subscriber |
| :---- | :---- | :---- |:---- |
|  ChunkGeneration|  |  | SentenceAgent |
| SessionInfo | |  | ProtocolAgent |
| TokenGeneration |  | SeveralKeywordsAgent, SingleKeywordAgent |TokenizeAgent, ProtocolAgent, RequestAgent, SessionContextAgent, ActivityAgent
| SessionContextUpdate |SessionContextEvent |  | SessionContextAgent, RequestAgent |
| UserInfo | | - | ProtocolAgent |
| Keywords | | SeveralKeywordsAgent, SingleKeywordAgent, NoKeywordAgent| TokenizeAgent |
| Gui | | GUIAgent | GUIAgent |
| SessionState |  |  | GUIAgent |
| DocProposal |  |  | GUIAgent, DocProposal |
| UserInteraction | | GUIAgent |  |
| DocRequest |  |  |  |
| SemanticChunks |  |  |  |
