# Installation Apache Spark

## Step 1 - Installation Java:

Öffnen des Terminal und überprüfung der Java-Version:
```
java -version
```
Wenn kein Java installiert ist, mit dem Befehl:
```
sudo apt-get install openjdk-8-jre
```
Java installieren.

## Step 2 - Installation Scala:

Im nächsten Schritt wird Scala installiert:

```
sudo apt-get install scala
```

Um die Installation von Scala zu testen, folgendes in das Terminal eingeben:
```
scala
```
Nun startet sich die Scala-Konsole, die wie folgt aussieht:
![Check_Scala_1](https://github.com/PeterThies/Event-Processing/blob/master/DevelopmentEnvironment/VirtualMachine/images/Installation_check_scala_1.JPG)
```
println("Hello World")
```

Bei erfolgreicher Installation wird "Hello World" ausgegeben:

![Check_Scala_2](https://github.com/PeterThies/Event-Processing/blob/master/DevelopmentEnvironment/VirtualMachine/images/Installation_check_scala_2.JPG)

um Scala zu beenden, folgenden Befehl eingeben:

```
:q
```

## Step 3 - Installation Apache Spark
Mit:
```
wget http://apache.mirror.digionline.de/spark/spark-2.3.0/spark-2.3.0-bin-hadoop2.7.tgz
```
Kann Spark von der Homepage heruntergeladen werden.
Im Anschluss mit:
```
sudo mkdir /opt/Spark
```
Das Verzeichnis für die Sparkinstallation anlegen.
Jetzt kann das heruntergeladene Archiv mit:

Das Verzeichnis entpacken:
```
sudo tar -xvf spark-2.3.0-bin-hadoop2.7.tgz -C /opt/Spark/
```

## Step 4 - Setzen der Pathvariable
Im Homeverzeichnis des Benutzers kann über
```
cat >> .profile
```
die Umgebungsvariable:
```
export SPARK_HOME=/opt/Spark/spark-2.3.0-bin-hadoop2.7/bin
```
hinzugefügt werden.
Der Bearbeitungsmodus kann mit ```STRG+C``` wieder verlassen werden.
Die Pfadveriable ist nach einem Neustart des Servers verfügbar:
```
reboot
```

## Step 5 - Apache Spark Server testen
Die Spark-Shell bietet eine interaktive Konsole für die direkt Eingabe von Befehlen:
```
$SPARK_HOME/spark-shell
```
Folgende Ausgabe erscheint auf dem Bildschirm:
![Check_Spark_1](https://github.com/PeterThies/Event-Processing/blob/master/DevelopmentEnvironment/VirtualMachine/images/Installation_check_spark_1.JPG)

Nach dem Start wird Scala aufgerufen und über:
```
println("Spark is running")
```
kann geprüft werden, ob die Installation erfolgreich war:
![Check_Spark_2](https://github.com/PeterThies/Event-Processing/blob/master/DevelopmentEnvironment/VirtualMachine/images/Installation_check_spark_2.JPG)

mit der Eingabe:
```
:q
```
Kann die Sparkumgebung beendet werden.

## Step 5 - Apache Spark Server starten

Mit dem Befehl:
```
$SPARK_HOME/bin/spark-submit /path/to/the/application.jar
```
kann eine Anwendung im Sparkkontext ausgeführt werden.
