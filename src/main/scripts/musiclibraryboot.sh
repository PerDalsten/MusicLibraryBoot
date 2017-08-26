#!/bin/bash

JAVA=$JAVA_HOME/bin/java

VERSION=1.2.1
DATABASE=derby
LOGDIR=logs
PORT=8080

$JAVA -jar -Dspring.profiles.active=$DATABASE -Dlogdir=$LOGDIR -Dserver.port=$PORT MusicLibraryBoot-$VERSION.jar