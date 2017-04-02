set JAVA=%JAVA_HOME%/bin/java

set VERSION=1.2
set DATABASE=derby
set LOGDIR=logs

%JAVA% -jar -Dspring.profiles.active=%DATABASE% -Dlogdir=%LOGDIR% MusicLibraryBoot-%VERSION%.jar