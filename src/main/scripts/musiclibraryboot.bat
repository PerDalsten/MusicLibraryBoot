set JAVA=%JAVA_HOME%/bin/java

set VERSION=1.2.6
set PROFILE=current
set LOGDIR=logs
set PORT=8080

if not "%1"=="" set PROFILE=%1  

%JAVA% -jar -Dspring.profiles.active=%PROFILE% -Dlogdir=%LOGDIR% -Dserver.port=%PORT% MusicLibraryBoot-%VERSION%.jar