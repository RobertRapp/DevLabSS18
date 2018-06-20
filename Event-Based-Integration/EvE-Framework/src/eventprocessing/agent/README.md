# Erläuterung

Der Agent hält verschiedene InteressenProfile vor, durch die der eingehende DStream gefiltert wird.
Wenn eine Nachricht durch den Filter gekommen ist, wird daraus ein Event erzeugt, welches anschließend
verarbeitet werden kann.
Ist das Event erfolgreich bearbeitet, wird es von dem Agenten weitergeleitet, damit es zurück auf ein Topic
geschrieben werden kann.
Durch State kann der Agent in verschiedene Zustände überführt werden.
Beispielsweise "not initialized" oder "ready".
not initialized - Agent ist nicht initialisiert, er kann in keinen Zustand wechseln, außer in ready.
ready - der Agent ist initialisiert und bereit Befehle entgegenzunehmen.