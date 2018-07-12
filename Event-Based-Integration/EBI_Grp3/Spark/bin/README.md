# Winutils.exe

Bei der Nutzung von Apache Hadoop 2.2.0 (automatisch beinhaltet, sobald Spark 2.2.0 verwendet wird) unter Windows wird der Fehler:

```
ERROR util.Shell: Failed to locate the winutils binary in the hadoop binary path
java.io.IOException: Could not locate executable C:\hadoop\bin\winutils.exe in the Hadoop binaries.
```

## Warum?

Die Apache Hadoop Distribution 2.2.0 besitzen nicht alle nativen Komponenten, die es unter Windows benötigt.
Winutil ist Voraussetzung und nicht Optional, auch wenn die Anwendung trotzdem lauffähig zu sein scheint.

## Lösung

Im Projektverzeichnis 
```
.\bin\Spark
```

 befindet sich die 
 ```
 winutils.exe
 ```
 
 Diese wird automatisch geladen, wenn das Hostsystem ein Windows-System ist.
 In der StreamingContext.java befindet sich im Konstruktor die Funktion, um
 das Betriebssystem festzustellen und ob die winutils benötigt wird oder nicht:
 ```
 // Prüft ob es sich um Windows handelt und ob die winutils.exe geladen werden muss
if (SystemUtil.isWindows()) {
	SystemUtil.setProperty(SystemUtil.getHadoopHomeDirectory(), SystemUtil.getProjectPath() + "\\Spark");
}
```
