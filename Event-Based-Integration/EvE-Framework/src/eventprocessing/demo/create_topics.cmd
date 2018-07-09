@ECHO off

IF NOT EXIST %KAFKA_HOME%\kafka_2.11-1.0.0\bin\windows ECHO Could not find kafka.

CD %KAFKA_HOME%\kafka_2.11-1.0.0\bin\windows

SET topics=Sensor-1 Sensor-2 TrafficData Diagnosis Storage Logging

(FOR %%a IN (%topics%) DO (
	ECHO Create topic %%a.
	kafka-topics.bat --create --zookeeper 10.142.0.2:2181 --replication-factor 1 --partitions 1 --topic %%a
))
		
PAUSE
EXIT /b
