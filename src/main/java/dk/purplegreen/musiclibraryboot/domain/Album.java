package dk.purplegreen.musiclibraryboot.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;

@Entity
@Table(name = "album")

public class Album {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@ManyToOne
	@JoinColumn(name = "artist_id", nullable = false)
	private Artist artist;
	@Column(name = "album_title", nullable = false)
	private String title;
	@Column(name = "album_year", nullable = false)
	private Integer year;
	@OneToMany(cascade = CascadeType.ALL, mappedBy = "album", orphanRemoval = true, fetch = FetchType.EAGER)
	@OrderBy("disc, track")
	private List<Song> songs = new ArrayList<>();

	public Album() {
	}

	public Album(Integer id) {
		this.id = id;
	}

	public Album(Artist artist, String title, Integer year) {
		this.artist = artist;
		this.title = title;
		this.year = year;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Artist getArtist() {
		return artist;
	}

	public void setArtist(Artist artist) {
		this.artist = artist;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public List<Song> getSongs() {
		return songs;
	}

	public void addSong(Song song) {
		if (song.getAlbum() != this) {
			song.setAlbum(this);
		}
		getSongs().add(song);
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder("<");
		result.append(id);
		result.append("> ");
		result.append(title);
		return result.toString();
	}
}
