set JAVA=%JAVA_HOME%/bin/java

set VERSION=1.2-SNAPSHOT
set DATABASE=derby
set LOGDIR=logs

%JAVA% -jar -Dspring.profiles.active=%DATABASE% -Dlogdir=%LOGDIR% MusicLibraryBoot-%VERSION%.jar