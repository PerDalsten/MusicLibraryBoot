var albums;

function setAlbums(a){
	albums = a;
}

function getAlbum(id){
	
	var album;
	
	for(i=0;i<albums.length;i++){
		if(albums[i].id == id){
			album = albums[i];
			break;
		}		
	}	
	return album;
}

function getAlbums(){
	return albums;
}


function showAlbum(album) {
	document.getElementById("album").innerHTML = album.title;
}

function displayAlbumList() {

	var albumList = document.getElementById("albumList");

	for (i = 0; i < albums.length; i++) {
		var album = document.createElement("li");
		album.setAttribute("id", albums[i].id);

		var description = albums[i].title;
		description += " - (";
		description += albums[i].artist.name;
		description += ")";
		description += " [";
		description += albums[i].year;
		description += "]";
	
		var albumDescription = document.createTextNode(description);

		album.appendChild(albumDescription);

		album.onclick = function() {

			var songList = document.getElementById("songs" + this.id);

			if (!songList) {
				var songList = document.createElement("ol");
				songList.setAttribute("id", "songs" + this.id);
				songList.className = "showSongs";

				var songs = getAlbum(this.id).songs;

				for (i = 0; i < songs.length; i++) {
					var song = document.createElement("li");
					song.appendChild(document.createTextNode(songs[i].title));
					songList.appendChild(song);
				}

				document.getElementById(this.id).appendChild(songList);
			} else {
				if (songList.className == 'showSongs')
					songList.className = "hideSongs";
				else
					songList.className = "showSongs";
			}
		}

		albumList.appendChild(album);
	}
}

function enableFilter(){
	 document.getElementById("artist").oninput=applyFilter;
	 document.getElementById("title").oninput=applyFilter;
	 //TODO Check for valid year
	 document.getElementById("year").oninput=applyFilter;
}

function applyFilter(){
	console.log("Applying filter: "+document.getElementById("artist").value);
	
	var artist=document.getElementById("artist").value;
	var artistExpr;
	
	var title=document.getElementById("title").value;
	var titleExpr;
	
	var year=document.getElementById("year").value;
	var yearExpr;
	
	if(artist){
		artistExpr=new RegExp(artist,'i');
	}
	
	if(title){
		titleExpr=new RegExp(title,'i');
	}
	
	if(year){
		yearExpr=new RegExp(year,'i');
	}
	
	
	for (i = 0; i < albums.length; i++) {
		var doShow = true;
		
		if(artistExpr){
			doShow = (albums[i].artist.name.search(artistExpr)>-1);
		}
		
		if(doShow && titleExpr){
			doShow = (albums[i].title.search(titleExpr)>-1);
		}
		
		if(doShow && yearExpr){
			doShow = (albums[i].year.toString().search(yearExpr)>-1);
		}
		
	    var album=document.getElementById(albums[i].id);
		if(doShow){
			album.style.display = '';
		}else{
			album.style.display = 'none';
		}
	}
}

function init() {

	var xhttp = new XMLHttpRequest();
	xhttp.onreadystatechange = function() {
		if (this.readyState == 4 && this.status == 200) {
			setAlbums(JSON.parse(this.response));
			displayAlbumList();
			enableFilter();
		}		
	};
	xhttp.open("GET", "rest/albums", true);
	xhttp.send();
}

window.onload = init;