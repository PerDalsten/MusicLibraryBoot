#!/bin/bash

JAVA=$JAVA_HOME/bin/java

VERSION=1.2.2
PROFILE=current
LOGDIR=logs
PORT=8080

if [ ! $1 == "" ]; then
	PROFILE=$1
fi


$JAVA -jar -Dspring.profiles.active=$PROFILE -Dlogdir=$LOGDIR -Dserver.port=$PORT MusicLibraryBoot-$VERSION.jar