set JAVA=%JAVA_HOME%/bin/java

set VERSION=1.2.1
set DATABASE=derby
set LOGDIR=logs
set PORT=8080

%JAVA% -jar -Dspring.profiles.active=%DATABASE% -Dlogdir=%LOGDIR% -Dserver.port=%PORT% MusicLibraryBoot-%VERSION%.jar