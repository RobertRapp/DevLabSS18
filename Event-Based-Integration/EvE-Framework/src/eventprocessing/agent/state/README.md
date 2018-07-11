Agenten lassen sich in verschiedene Zustände überführen, wodurch ein Agent nebenläufig zur DStream-Verarbeitung weitere Tätigkeiten ausführen kann.

![State](https://github.com/PeterThies/Event-Processing/blob/IngoTrautwein/diagrams/eventprocessing/agent/Agent_State.JPG)

* Jeder Zustand kann gestartet werden sowie gestoppt werden.
* Wird ein Zustand gestoppt, wird der Agent in den ReadyState überführt.
* Der Agent kann sich nur in einem Zustand befinden zur selben Zeit.
* Der Agent kann nur in einen neuen Zustand überführt werden, wenn er im ReadyState ist.
* Standardmäßig ist der Agent im NotInitializedState.
* Wenn der Agent die .init()-Methode ausführt, gelangt er in den ReadyState

Die nachfolgende Grafik zeigt die Zustände auf

![Statechart](https://github.com/PeterThies/Event-Processing/blob/IngoTrautwein/diagrams/eventprocessing/agent/Statechart_Agent.jpg)
