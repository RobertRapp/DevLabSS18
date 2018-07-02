# Events
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
