# SampleJPOSClientAndServer
A simple program used to send ISO messages.

## Setup Log4j2 Config
Create a sample Log4J2 XML file. Sample file provided.

## Setup Packager
You will need to specify a packager. A sample packager is provided but you will need to adjust it.

## Setup Config File
Create a config file. This file will be passed as an argument to the jar file. A sample config file is provided.

## SQL Code
Setup the SQL database first. You can do this by running the scripts provided.

Example:
Run the script setupDatabase.sql against an MSSQL server.

## Running the Program
The jar file can be run with the following command:

```
java -jar JARFILE c:\temp\config.properties -Dlog4j.configurationFile=file:/C:/temp/log4j2.xml
```

