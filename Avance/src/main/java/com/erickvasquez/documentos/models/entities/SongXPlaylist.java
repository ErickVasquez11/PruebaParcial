package com.erickvasquez.documentos.models.entities;

import java.sql.Timestamp;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "songxplaylits")
public class SongXPlaylist {
	@Id
	@Column(name = "code")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private UUID code;
	
	@Column(name = "date_added", nullable = true)
	//Sql
	private Timestamp date_added;
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "song_code",nullable = true)
	private Song song;
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "playlist_code",nullable = true)
	private PlayList playlist;
	
	public SongXPlaylist(Song song, PlayList playlist) {
		super();
		this.song = song;
		this.playlist = playlist;
	}
}
