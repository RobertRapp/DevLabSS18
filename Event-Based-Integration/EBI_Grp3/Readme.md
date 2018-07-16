# Events
<<<<<<< HEAD
Dieses Dokument zeigt eine Übersicht aller verwendeten Events innerhalb der Projektgruppe-Event-basierte Integration. 


##  
- [ApplicationEvent](#applicationevent)
- [CalendarEvent](#calendarevent)
- [ContactEvent](#contactevent)
- [DocProposalEvent](#docproposalevent)
- [DocumentEvent](#documentevent)
- [FeedbackEvent](#feedbackevent)
- [GuiEvent](#guievent)
- [JsonDocEvent](#guievent)
- [NoKeywordEvent](#nokeywordevent)
- [ProjectEvent](#projectevent)
- [ProtocolEvent](#protocolevent)
- [RequestEvent](#reqeustevent)
- [SafeProtocolEvent](#safeprotocolevent)
- [SentenceEvent](#sentenceevent)
- [SessionContextEvent](#sessioncontextevent)
- [SessionEndEvent](#sessionendevent)
- [SessionStartEvent](#sessionstartevent)
- [SeveralKeywordsevent](#severalkeywordsevent)
- [SingleKeyword](#singlekeyword)
- [UncertainEvent](#uncertainevent)
- [UserInteractionEvent](#userinteractionevent)
- [WatsonEvent](#watsonevent)



## ApplicationEvent
| Kurzbeschreibung | Properties |  Properties Datentyp |
| :---- | :---- | :---- |
| `löakjfalksödfjöalskjföslakjfölksdjf` | `XXXX` | `XXXX`

## CalendarEvent
| Kurzbeschreibung | Properties |  Properties Datentyp |
| :---- | :---- | :---- |
| `XXXXX` | `XXXX` | `XXXX`

## ContactEvent
| Kurzbeschreibung | Properties |  Properties Datentyp |
| :---- | :---- | :---- |
| `XXXXX` | `XXXX` | `XXXX`
## DocProposalEvent
| Kurzbeschreibung | Properties |  Properties Datentyp |
| :---- | :---- | :---- |
| `Bei Empfang des Events von Topic DocProposal wird dieses in dem DocProposalInterestProfile in den JSON Format umgewandelt.` | `XXXX` | `XXXX`
## DocumentEvent
| Kurzbeschreibung | Properties |  Properties Datentyp |
| :---- | :---- | :---- |
| `XXXXX` | `XXXX` | `XXXX`
## FeedbackEvent
| Kurzbeschreibung | Properties |  Properties Datentyp |
| :---- | :---- | :---- |
| `XXXXX` | `XXXX` | `XXXX`
## GuiEvent
| Kurzbeschreibung | Properties |  Properties Datentyp |
| :---- | :---- | :---- |
| `Noch nicht definiert, fliegt vielleicht wieder raus` | `XXXX` | `XXXX`
## JsonDocEvent
| Kurzbeschreibung | Properties |  Properties Datentyp |
| :---- | :---- | :---- |
| `Dieses Event wird nach der Umwandlung in den JSON Format (zuvor DocProposalEvent) an das Topic Gui gesendet` | `XXXX` | `XXXX`
## NoKeywordEvent
| Kurzbeschreibung | Properties |  Properties Datentyp |
| :---- | :---- | :---- |
| `XXXXX` | `XXXX` | `XXXX`
## ProjectEvent
| Kurzbeschreibung | Properties |  Properties Datentyp |
| :---- | :---- | :---- |
| `XXXXX` | `XXXX` | `XXXX`
## ProtocolEvent
| Kurzbeschreibung | Properties |  Properties Datentyp |
| :---- | :---- | :---- |
| `XXXXX` | `XXXX` | `XXXX`
## RequestEvent
| Kurzbeschreibung | Properties |  Properties Datentyp |
| :---- | :---- | :---- |
| `XXXXX` | `XXXX` | `XXXX`
## SafeProtocolEvent
| Kurzbeschreibung | Properties |  Properties Datentyp |
| :---- | :---- | :---- |
| `XXXXX` | `XXXX` | `XXXX`

## SentenceEvent
| Kurzbeschreibung | Properties |  Properties Datentyp |
| :---- | :---- | :---- |
| `XXXXX` | `XXXX` | `XXXX`
## SessionContextEvent
| Kurzbeschreibung | Properties |  Properties Datentyp |
| :---- | :---- | :---- |
| `XXXXX` | `XXXX` | `XXXX`
## SessionEndEvent
| Kurzbeschreibung | Properties |  Properties Datentyp |
| :---- | :---- | :---- |
| `Wird ausgelöst sobald eine Session beendet wird und an das Topic SessionState gesendet.` | `XXXX` | `XXXX`
## SessionStartEvent
| Kurzbeschreibung | Properties |  Properties Datentyp |
| :---- | :---- | :---- |
| `Wird ausgelöst sobald eine Session gestartet wird und an das Topic SessionState gesendet` | `XXXX` | `XXXX`
## SeveralKeywordsevent
| Kurzbeschreibung | Properties |  Properties Datentyp |
| :---- | :---- | :---- |
| `XXXXX` | `XXXX` | `XXXX`
## SingleKeyword
| Kurzbeschreibung | Properties |  Properties Datentyp |
| :---- | :---- | :---- |
| `XXXXX` | `XXXX` | `XXXX`
## UncertainEvent
| Kurzbeschreibung | Properties |  Properties Datentyp |
| :---- | :---- | :---- |
| `XXXXX` | `XXXX` | `XXXX
## UserInteractionEvent
| Kurzbeschreibung | Properties |  Properties Datentyp |
| :---- | :---- | :---- |
| `Wird ausgelöst sobald ein Dokument angeklickt wird und an das Topic UserInteraction gesendet` | `XXXX` | `XXXX`
## WatsonEvent
| Kurzbeschreibung | Properties |  Properties Datentyp |
| :---- | :---- | :---- |
| `XXXXX` | `XXXX` | `XXXX`

`

## TOPICS
Das ist eine Übersicht aller Topics innerhalb des Projekts.
=======
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


# Agents
Dieser Abschnitt zeigt eine Übersicht aller verwendeten Agents innerhalb der Projektgruppe "Eventbasierte Integration". 
##  
- [SessionContextAgent](#SessionContextAgent)
- [RequestAgent](#RequestAgent)
- [ProtocolAgent](#ProtocolAgent)
- [ActivityAgent](#ActivityAgent)

## SessionContextAgent
Beobachtet das Gespräch und identifiziert ob sich der Gesprächskontext innerhalb eines Gesprächs während der Laufzeit ändert. Jede Gesprächskontext-Änderung erzeugt ein Event auf das SessionContextUpdate Topic. 
## RequestAgent
Erzeugt Dokumentenanfragen mit Hilfe des aktuellen Sessionkontexts und semantisch angereicherten Chunks. 
## ProtocolAgent
Der ProtocolAgent sammelt Gesprächsinformationen und erstellt bei Session-Ende ein Protocol-Event, das auf das Protocol-Topic gepusht wird. Dieses löst die Abspeicherung des Protokolls durch DokumentenRepräsentation aus.
## ActivityAgent
Erstellt ein Application-Event das in der der GUI einen Applikationsaufruf auslöst.


# Topics
Dieser Abschnitt zeigt eine Übersicht aller verwendeten Topics innerhalb der Projektgruppe "Eventbasierte Integration". 
##  
- Protocol
- TokenGeneration
- SessionContext
- DocRequest
- SessionState
- DocProposal

#
Dieser Abschnitt zeigt ist eine Übersicht aller Topics innerhalb des Projekts.
>>>>>>> 3299b29c173e39619cff723bc73a73280c3f9dd8



| Topic | Events | Publisher | Subscriber |
| :---- | :---- | :---- |:---- |
<<<<<<< HEAD
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
=======
|  ChunkGeneration|WatsonEvent, SentenceEvent|GuiAgent, SentenceAgent| SentenceAgent, SemanticAgent |
| SemanticChunks |FeedbackEvent|SemanticAgent  | TokenizeAgent |
| Keywords | SingleKeywordEvent, SeveralKeywordAgent, NoKeywordEvent | TokenizeAgent |SingleKeywordAgent, SeveralKeywordAgent, NoKeywordAgent, SessionContextAgent, ActivityAgent
| TokenGeneration |DocumentEvent, ContactEvent, ProjectEvent, UncertainEvent | SeveralKeywordAgent, SingleKeywordAgent, NoKeywordAgent | ProtocolAgent, RequestAgent, SessionContextAgent, ActivityAgent |
| SessionContext | SessionContextEvent | SessionContextAgent | RequestAgent |
| DocProposal | ApplicationEvent, DocProposalEvent | ActivityAgent, DocProposalAgent | DocumentProposalAgent, DocProposalAgent |
| DocRequest | RequestEvent | RequestAgent | DocProposalAgent |
| Protocol | ProtocolEvent | ProtocolAgent | SaveDocumentAgent |
| SessionState | SessionStartEvent, SessionEndEvent  | GuiAgent | ProtocolAgent, SessionContextAgent |
| UserInteraction | UserInteractionEvent| GuiAgent |  

>>>>>>> 3299b29c173e39619cff723bc73a73280c3f9dd8
