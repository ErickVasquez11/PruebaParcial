package com.erickvasquez.documentos.models.dtos.response;

import java.util.List;

import com.erickvasquez.documentos.models.entities.PlayList;
import com.erickvasquez.documentos.models.entities.Song;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class PlayListSongsDTO {

	private PlayList playlist;
	private List<Song>songs;
}
