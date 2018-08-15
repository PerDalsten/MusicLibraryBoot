RUNNING
=======

Copy jar and src/main/scripts/musiclibraryboot.* to a directory and run by calling appropriate start script, e.g.

./musiclibraryboot.sh mysql (or derby or hsql)

If using MusicLibraryConfigServer no argument is necessary as 'current' is default (assuming MusicLibraryBoot-current.yml 
is deployed to config server).


Rest URL's
==========

All: http://localhost:8080/MusicLibraryBoot/rest/albums
By ID: http://localhost:8080/MusicLibraryBoot/rest/albums/42
Search: http://localhost:8080/MusicLibraryBoot/rest/albums?artist=Thin Lizzy&title=&year=
By artist: http://localhost:8080/MusicLibraryBoot/rest/artists/83/albums


Read only UI
============

http://localhost:8080/MusicLibraryBoot/