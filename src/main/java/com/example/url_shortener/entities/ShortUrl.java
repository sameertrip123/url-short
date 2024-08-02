package com.example.url_shortener.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Entity
@Table(name = "short_url")
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShortUrl {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String originalUrl;

	private String shortCode;

	private Instant createdAt;

	private Long accessCount;

}
