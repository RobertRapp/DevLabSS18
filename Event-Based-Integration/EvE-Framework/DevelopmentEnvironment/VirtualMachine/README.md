# Installation der Virtuellen Maschinen

Um Virtuelle Maschinen zu betreiben bedarf es einer Virtuallisierungssoftware, welche die Maschinen verwaltet.

Virtual Box ist eine Open Source Lösung, auf die sich auch die Konfiguration im folgenden bezieht.

Homepage: https://www.virtualbox.org/

Gastsystem: Ubuntu Server x64 (v. 16.04.4 LTS) - http://releases.ubuntu.com/xenial/ubuntu-16.04.4-server-amd64.iso

## Installation unter Windows 10

Wenn beim Starten der Virtuellen Maschine folgender Fehler auftreten sollte:
```
VT-x is not available (VERR_VMX_NO_VMX).
```

Dann folgende Optionen überprüfen:

  1. VT-x is not enabled in the BIOS
  1. The CPU doesn't support VT-x
  1. Hyper-V virtualization is enabled in Windows
    
Das Hyper-V Feature lässt sich über die Kommandozeile (CMD) mit Administratorrechten deaktivieren:
```
dism.exe /Online /Disable-Feature:Microsoft-Hyper-V
```

Im Anschluss muss das Host-System neugestartet werden, damit die Änderungen wirksam werden.

## Konfiguration Virtuelle Maschine

Bei der Installationsanleitung wird als Netzwerkadapter die "Netzwerkbrücke" genutzt. https://www.virtualbox.org/manual/ch06.html#network_bridged

## Installation Ubuntu Server

Setting | Value
------------ | -------------
Language | English
Install | Ubuntu Server
Installation language | English
Location | Germany
Locale Settings | en_US.UTF-8
Hostname | kafka-spark
Account / User name | hdm
Password | hdm
Encrypt home directory | no
Timezone | Europe/Berlin
Partition method | use entire disk
HTTP proxy information | none
manage upgrades	| Install security updates automatically
Software selection | standard system utilities
Install GRUB boot loader to the master boot record | yes

## Netzwerkkonfiguration - statische IPv4-Adresse vergeben

Achtung: Es wird von einem klassischen C-Netzwerk ausgegangen (255.255.255.0 bzw. /24)

Über den Befehl:
```
ifconfig
```
kann in der Konsole die aktiven Netzwerkadapter sowie die Konfiguration angezeigt werden. Auf dem Screenshot ist der Adaptername in der linken Spalte abgebildet. Relevant ist der Adapter "enp0s3". Durch das hinzufügen der Netzwerkbrücke, ist dieser Adapter aktiv und hat durch den DHCP-Server eine IPv4-Konfiguration erhalten.

![output_ifconfig](https://github.com/PeterThies/Event-Processing/blob/master/DevelopmentEnvironment/VirtualMachine/images/Installation_VirtualBox_LinuxServer_static_ipv4.JPG)

IPv4-Konfiguration:

Setting | Value
------------ | -------------
inet addr | 192.168.0.70
Bcast | 192.168.0.255
Mask | 255.255.255.0

Die Werte für die Parameter kann sich von Netzwerk zu Netzwerk unterscheiden.

Standardmäßig ist der DHCP-Server aktiviert. Sollte dies nicht gewünscht sein, können die Adaptereinstellungen in einem Editor
mit Administratorrechten angepasst werden. Alternativ zu vi kann jeder beliebige andere Editor verwendet werden:
```
sudo vi /etc/network/interfaces
```
Die Ausgabe sieht dann ungefähr wie folgt aus (Nach wie vor können sich die Werte unterscheiden):
![output_interfaces](https://github.com/PeterThies/Event-Processing/blob/master/DevelopmentEnvironment/VirtualMachine/images/Installation_VirtualBox_LinuxServer_network_interfaces_dhcp.JPG)

Es muss die Konfiguration für den Adapter "enp0s3" von DHCP in Static geändert werden:

![output_interfaces](https://github.com/PeterThies/Event-Processing/blob/master/DevelopmentEnvironment/VirtualMachine/images/Installation_VirtualBox_LinuxServer_network_interfaces_static.JPG)

Anschließend die Änderungen speichern.
Die Adaptereinstellungen werden nach einem Neustart übernommen.
Entweder über
```
sudo /etc/init.d/networking restart
```
Die Adapter neustarten oder den Server neustarten mit:
```
reboot
```
Nachdem die Netzwerkeinstellungen gesetzt wurden, kann mit dem Befehl:
```
sudo apt-get update
```
liest die Paketliste neu ein.


## Installation von Kafka und Spark

[Installationsanleitung für Spark](InstallationGuide_Ubuntu_Spark.md)

[Installationsanleitung für Kafka](InstallationGuide_Ubuntu_Kafka.md)
