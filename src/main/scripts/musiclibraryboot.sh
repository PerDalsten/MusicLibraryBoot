#!/bin/bash

JAVA=$JAVA_HOME/bin/java

$JAVA -jar -Dspring.profiles.active=mysql MusicLibraryBoot-0.0.1-SNAPSHOT.jar