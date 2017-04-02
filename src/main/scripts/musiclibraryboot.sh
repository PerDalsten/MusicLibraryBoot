#!/bin/bash

JAVA=$JAVA_HOME/bin/java

VERSION=1.2
DATABASE=derby
LOGDIR=logs

$JAVA -jar -Dspring.profiles.active=$DATABASE -Dlogdir=$LOGDIR MusicLibraryBoot-$VERSION.jar