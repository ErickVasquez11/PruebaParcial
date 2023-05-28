package com.erickvasquez.documentos.services.implementations;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.erickvasquez.documentos.models.entities.PlayList;
import com.erickvasquez.documentos.models.entities.Song;
import com.erickvasquez.documentos.models.entities.SongXPlaylist;
import com.erickvasquez.documentos.repositories.SongxPlayListRepository;
import com.erickvasquez.documentos.services.SongXPlaylistService;

@Service
public class SongXPlaylistServiceImplement implements SongXPlaylistService {
	
	@Autowired
	private SongxPlayListRepository repository;

	@Override
	public void save(Song song, PlayList playlist) throws Exception {
		SongXPlaylist relation = new SongXPlaylist(song, playlist);
		repository.save(relation);
	}
}
