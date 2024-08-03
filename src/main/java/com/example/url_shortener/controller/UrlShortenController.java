package com.example.url_shortener.controller;

import com.example.url_shortener.dto.RequestBodyDto;
import com.example.url_shortener.service.UrlShortenService;
import com.example.url_shortener.utils.WithRateLimitProtection;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;

@RestController
@Slf4j
public class UrlShortenController {

	private final UrlShortenService urlShortenService;

	public UrlShortenController(UrlShortenService urlShortenService) {
		this.urlShortenService = urlShortenService;
	}

	@WithRateLimitProtection
	@PostMapping("/api/shorten")
	public ResponseEntity<?> shortenUrl(@Valid @RequestBody RequestBodyDto requestBodyDto)
			throws MalformedURLException {
		log.info("Shorten URL called");
		return this.urlShortenService.shortenUrl(requestBodyDto.getUrl());
	}

	@WithRateLimitProtection
	@GetMapping("/{shortCode}")
	public void redirect(@PathVariable String shortCode) {
		log.info("Redirect URL Called");
		this.urlShortenService.redirect(shortCode);
	}

	@WithRateLimitProtection
	@GetMapping("/api/stats/{shortCode}")
	public ResponseEntity<?> getStatistics(@PathVariable String shortCode) {
		log.info("Get Stats URL Called");
		return this.urlShortenService.getStatistics(shortCode);
	}

}
