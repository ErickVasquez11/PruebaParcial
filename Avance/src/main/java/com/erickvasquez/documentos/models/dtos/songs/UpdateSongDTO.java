package com.erickvasquez.documentos.models.dtos.songs;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UpdateSongDTO {

	@NotBlank(message = "code is required")
	private String code;
	
	@NotBlank(message = "title is required")
	private String title;
	
	@Min(value = 1, message = "duration must be greater than 1")
	@Max(value = 99, message = "duration must be smaller than 99")
	private int duration;
}
