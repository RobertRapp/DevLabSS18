# Installationsanleitung

In der Entwicklung wurde Eclipse Oxygen verwendet:  
https://www.eclipse.org/downloads/download.php?file=/oomph/epp/oxygen/R/eclipse-inst-win64.exe  
Daher werden die Arbeitsschritte für die Einbindung des Projekts an dieser Entwicklungsumgebung ausgerichtet.

## Voraussetzung an die Entwicklungsumgebung

Damit nach dem Klonvorgang die Anwendung ausgeführt werden kann, müssen folgende Voraussetzungen erfüllt sein:

1. Maven Integration
   Das Projekt wurde mit Maven entwickelt.  
   Für Eclipse: https://projects.eclipse.org/projects/technology.m2e

1. Java Version
   Version 8 von Java.  
   https://java.com/de/download/manual.jsp
   
1. Git
   Für die Versionskontrolle wird das Plugin Egit verwendet.  
   http://www.eclipse.org/egit/download/

## Einbindung des Projekts in die Entwicklungsumgebung

Für die Einbindung muss das Projekt aus dem Github-Repository geklont, als Projekt eingebunden und zu einem Mavenprojekt konvertiert werden.

1. Das Repository klonen: https://github.com/PeterThies/Event-Processing.git

1. Ein neues Javaprojekt anlegen: "File" -> "New" -> "Java Project" 
   1. Als "execution environment" muss "JavaSE 1.8" ausgewählt sein
   1. Das Projektverzeichnis auf das geklonte Gitrepository ändern.
      ($USER/git/Event-Processing/)
      
1. Für die Konvertierung in ein Maven Projekt ein Rechtsklick auf das Projekt -> "Configure" -> "Convert to Maven Project"
   
   Es kann ein paar Minuten dauern, bis das Projekt erfolgreich konvertiert wurde und die externen Bibliotheken aus der
   pom.xml nachgeladen sind.
   
1. Nachdem Das Projekt zu einem Maven Projekt konvertiert wurde, müssen die "Java Build Path" und "Java Compiler" Einstellungen überprüft werden. 
   1. Rechtsklick auf das Projekt -> "Properties"
   1. Auf den Tab "Java Build Path" wechseln
   1. Wenn Library "JRE System Library [J2SE-1.5]" eingebunden ist, dann:
      1. Die Library "JRE System Library [J2SE-1.5]" entfernen über den Button "Remove"
      1. Wenn der Eintrag entfernt wurde, dann auf den Button "Add Library..." klicken
      1. In dem neuen Tab "Add Library" die Option "JRE System Library" auswählen und mit "Next >" bestätigen.
      1. Sollte der Workspace default nicht auf "jre 1.8.x" oder "jdk 1.8.x" stehen, dann über "Alternate JRE:" den Pfad angeben (setzt voraus, dass Java mit der Version 8 installiert wurde)
      1. Anschließend auf "Finish" klicken
      1. Im "Java Build Path" erscheint nun die "JRE System Library [jre 1.8.x]" bzw. "JRE System Library [jdk 1.8.x]"
      1. Den Button "Apply" drücken.
   1. Auf den Tab "Java Compiler" wechseln
   1. Die Option "Enable projekt specific settings" muss ausgewählt sein.
   1. Den "Compiler compliance level:" auf "1.8" wechseln (Achtung: Java 8 wird benötigt), sollte dies nicht bereits auf "1.8" stehen
   1. Anschließend auf "Apply" klicken.
   1. Es erscheint die Meldung "Compiler Settings Changed". Hier mit "Yes" bestätigen
   1. Abschließend über "Apply and Close" die Einstellungen verlassen.

## Exportieren in eine .jar-Datei

Damit die Anwendung unter der Sparkumgebung lauffähig wird, muss das Projekt auf folgende Weise exportiert werden:

1. Rechtsklick auf das Projekt
1. auf "Export..." navigieren
1. als Export Wizzard wird "Java/Runnable JAR file" ausgewählt
1. "Launch configuration" benötigt ein Argument. Sollte bisher keine "run configuration" angelegt worden sein:
   1. In Eclipse im Menü auf "Run" und anschließend auf "Run Configurations..." klicken.
   1. In dem linken Fenster "Java Application" suchen und mit Rechtsklick "New" eine neue Konfiguration anlegen.
   1. Der "Name" kann frei gewählt werden.
   1. als "Project" muss euer Projekt ausgewählt sein (hier: "Event Processing")
   1. In "Main class" wird die Klasse eingetragen, in der sich die main-Methode befindet.
   1. eingetragene "Programm arguments" im Tab "Arguments" werden nicht exportiert!
   1. Abschließend auf den Button "Apply" um die Konfiguration zu speichern.
   1. Die erstelle "run configuration" kann nun der "Launch configuration" übergeben werden.
1. Unter dem Punkt "Library handling" muss die Auswahl "Extract required libraries into generated JAR" ausgewählt sein
1. "Export destination" gibt den Pfad an, in der die .jar-Datei gespeichert wird.

## Ausführen der .jar-Datei in der Sparkumgebung

Über das Terminal kann mit dem Befehl:  
*$SPARK_HOME/bin/spark-submit /path/to/the/application.jar*  
die exportierte .jar-Datei aufgerufen werden.

**Beispiel:**

*$SPARK_HOME/bin/spark-submit /path/to/the/application.jar*

## Bereitstellung von Kafka und Spark

[Bereitstellung von Kafka und Spark](./DevelopmentEnvironment/README.md).
