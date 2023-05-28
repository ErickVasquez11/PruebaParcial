package com.erickvasquez.documentos.services;

import com.erickvasquez.documentos.models.entities.PlayList;
import com.erickvasquez.documentos.models.entities.Song;

public interface SongXPlaylistService {
	void save(Song song, PlayList playlist) throws Exception;
}
