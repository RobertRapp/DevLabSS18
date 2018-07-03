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
