# Hinweis

Neben der Entwicklungsumgebung des Quelltextes ist die Einbindung von Spark und Kafka erforderlich.
Es existieren mehrere Varianten um die beiden Systeme einzubinden. Wenn die Installation von Kafka und Spark
nicht auf der eigenen lokalen Maschine passieren soll, gibt es die Variante der Nutzung von Virtuellen Maschinen oder Docker.

## Hinweise Virtuelle Maschinen

eine Virtuelle Maschine ist ein vollständiges Rechnersystem. Damit ist ein Betriebssystem im vollen Umfang nötig.
Dies bedeutet in der Konsequenz, dass mehr Ressourcen für den Betrieb benötigt werden. Dies kann gerade bei Laptops
zu Problemen werden (abhänging der Ausstattung)

[Installation Virtuelle Maschinen](./VirtualMachine/README.md).

## Hinweise Docker

Docker ist Containerbasierend. Im Vergleich zu einer Virtuellen Maschine werden nicht alle Komponenten installiert,
wie beispielsweise das Gesamte Betriebssystem (z.B. Ubuntu) sondern nur die Komponenten die benötigt werden,
um die Anwendung (Kafka oder Spark) lauffähig zu machen.
Mit Docker spart man sich daher eine Menge an Ressourcen, im speziellen Arbeits-/Festplattenspeicher.

[Installation Docker](./Docker/README.md).
