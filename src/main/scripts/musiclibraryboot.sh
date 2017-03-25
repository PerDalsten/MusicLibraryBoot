#!/bin/bash

JAVA=$JAVA_HOME/bin/java

VERSION=1.0-SNAPSHOT
PROFILE=derby
LOGDIR=mylogs

$JAVA -jar -Dspring.profiles.active=$PROFILE -Dlogdir=$LOGDIR MusicLibraryBoot-$VERSION.jar