package com.example.url_shortener.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RequestBodyDto {

	@NotNull(message = "URL cannot be NULL")
	@NotBlank(message = "URL cannot be blank")
	private String url;

}
