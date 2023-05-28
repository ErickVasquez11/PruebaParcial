package com.erickvasquez.documentos.controllers;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.erickvasquez.documentos.models.dtos.playlists.SavePlayListDTO;
import com.erickvasquez.documentos.models.dtos.playlists.UpdatePlayListDTO;
import com.erickvasquez.documentos.models.dtos.response.MessageDTO;
import com.erickvasquez.documentos.models.dtos.response.PlayListSongsDTO;
import com.erickvasquez.documentos.models.dtos.songxplaylist.SaveSongXPlaylistDTO;
import com.erickvasquez.documentos.models.entities.PlayList;
import com.erickvasquez.documentos.models.entities.Song;
import com.erickvasquez.documentos.models.entities.SongXPlaylist;
import com.erickvasquez.documentos.models.entities.User;
import com.erickvasquez.documentos.services.PlaylistService;
import com.erickvasquez.documentos.services.SongService;
import com.erickvasquez.documentos.services.SongXPlaylistService;
import com.erickvasquez.documentos.services.UserService;
import com.erickvasquez.documentos.utils.RequestErrorHandler;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/play-lists")
@CrossOrigin("*")
public class PlayListController {
	

	@Autowired
	private PlaylistService playlistService;
	@Autowired
	private SongService songService;
	@Autowired
	private SongXPlaylistService songxPlaylistService;
	@Autowired
	private UserService userService;
	@Autowired
	private RequestErrorHandler errorHandler;
	
	@GetMapping("")
	public ResponseEntity<?> getPlayLists() {
		List<PlayList> playlist = playlistService.findAll();
		return new ResponseEntity<>(playlist, HttpStatus.OK);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<?> getPlayList(@PathVariable String id) {
		PlayList playlist = playlistService.findOneById(id);
		
		if (playlist == null)
			return new ResponseEntity<>(new MessageDTO("playlist not found"), HttpStatus.NOT_FOUND);
		
		return new ResponseEntity<>(playlist, HttpStatus.OK);
	}
	
	@GetMapping("/{id}/songs")
	public ResponseEntity<?> getPlayListSongs(@PathVariable String id) {
		PlayList playlist = playlistService.findOneById(id);
		
		if (playlist == null)
			return new ResponseEntity<>(new MessageDTO("playlist not found"), HttpStatus.NOT_FOUND);
		
		// Get relations
		List<SongXPlaylist> relations = playlist.getSongxplaylist();
		
		// Create songs list
		List<Song> songs = relations.stream().map(relation -> {
			return relation.getSong();
		}).collect(Collectors.toList());
		
		PlayListSongsDTO response = new PlayListSongsDTO(playlist, songs);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@PostMapping("")
	public ResponseEntity<?> createPlayList(
			@ModelAttribute @Valid SavePlayListDTO data, BindingResult validations) {
		if (validations.hasErrors()) {
			return new ResponseEntity<>(
					errorHandler.mapErrors(validations.getFieldErrors()), HttpStatus.BAD_REQUEST);
		}
		
		User user = userService.findOneById(data.getUserCode());
		if (user == null)
			return new ResponseEntity<>(new MessageDTO("user not found"), HttpStatus.NOT_FOUND);
		
		try {
			playlistService.save(data, user);
			return new ResponseEntity<>(new MessageDTO("playlist created"), HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping("add-song")
	public ResponseEntity<?> addSongToPlaylist(
			@ModelAttribute @Valid SaveSongXPlaylistDTO data, BindingResult validations) {
		if (validations.hasErrors()) {
			return new ResponseEntity<>(
					errorHandler.mapErrors(validations.getFieldErrors()), HttpStatus.BAD_REQUEST);
		}
		
		Song song = songService.findOneById(data.getSongCode());
		if (song == null)
			return new ResponseEntity<>(new MessageDTO("song not found"), HttpStatus.NOT_FOUND);
		
		PlayList playlist = playlistService.findOneById(data.getPlaylistCode());
		if (playlist == null)
			return new ResponseEntity<>(new MessageDTO("playlist not found"), HttpStatus.NOT_FOUND);
			
		try {
			songxPlaylistService.save(song, playlist);
			return new ResponseEntity<>(new MessageDTO("song added to playlist"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("")
	public ResponseEntity<?> updatePlayList(
			@ModelAttribute @Valid UpdatePlayListDTO data, BindingResult validations) {
		if (validations.hasErrors()) {
			return new ResponseEntity<>(
					errorHandler.mapErrors(validations.getFieldErrors()), HttpStatus.BAD_REQUEST);
		}
		
		try {
			playlistService.update(data);
			return new ResponseEntity<>(new MessageDTO("playlist updated"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deletePlayList(@PathVariable String id) {
		try {
			playlistService.deleteOneById(id);
			return new ResponseEntity<>(new MessageDTO("playlist deleted"), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
