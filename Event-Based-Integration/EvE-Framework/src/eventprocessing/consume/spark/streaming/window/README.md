# Erläuterung

Neben den transformations die von der Spark Core API gestellt werden, 
bietet Apache Spark Streaming die Funktionalität von Window, 
mit der Transformationen über ein gleitendes Datenfenster angewendet werden können.

![Window](https://github.com/PeterThies/Event-Processing/blob/IngoTrautwein/diagrams/eventprocessing/consume/Consume_Spark_Window.JPG)

* Das AbstractWindow stellt die Basis Member aller Windows bereit. Dazu gehört die Länge sowie das Intervall für ein Window.
* Jeder Agent hält ein eigenes Window vor. Wird kein Window angegeben, orientiert sich die Länge sowie das Intervall an der batch duration des SparkContext.
* Die SparkExecution ruft für jeden Agenten das Window ab und setzt das Window auf den DStream.
