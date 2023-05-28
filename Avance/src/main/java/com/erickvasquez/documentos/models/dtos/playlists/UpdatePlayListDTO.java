package com.erickvasquez.documentos.models.dtos.playlists;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdatePlayListDTO {

	@NotBlank(message = "Playlist code is required")
	private String code;
	
	@NotBlank(message = "Title is required")
	private String title;
	
	@NotBlank(message = "Description is required")
	private String description;
}
