package com.example.url_shortener.service;

import com.example.url_shortener.entities.ShortUrl;
import com.example.url_shortener.exception.InvalidUrlException;
import com.example.url_shortener.exception.ResourceNotFoundException;
import com.example.url_shortener.repository.UrlShortenRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.time.Instant;
import java.util.Optional;

@Service
@Slf4j
public class UrlShortenService {

	private final UrlShortenServiceHelper urlShortenServiceHelper;

	private final UrlShortenRepository urlShortenRepository;

	public UrlShortenService(UrlShortenServiceHelper urlShortenServiceHelper,
			UrlShortenRepository urlShortenRepository) {
		this.urlShortenServiceHelper = urlShortenServiceHelper;
		this.urlShortenRepository = urlShortenRepository;
	}

	public ResponseEntity<?> shortenUrl(String url) throws MalformedURLException, URISyntaxException {
		// Check if the URL is valid
		if (!this.urlShortenServiceHelper.isValidURL(url)) {
			log.error("Invalid URL");
			throw new InvalidUrlException("Invalid URl");
		}

		// Get the short URL from the long url
		String shortenedUrl = this.urlShortenServiceHelper.getShortUrl(url);

		// Create the entity
		ShortUrl shortUrl = ShortUrl.builder()
			.originalUrl(url)
			.shortCode(shortenedUrl)
			.accessCount(0L)
			.createdAt(Instant.now())
			.build();

		// Save the entity in the DB
		this.urlShortenRepository.save(shortUrl);

		return new ResponseEntity<>(shortenedUrl, HttpStatus.OK);
	}

	public ResponseEntity<?> getStatistics(String shortCode) {
		Optional<ShortUrl> shortUrl = this.urlShortenRepository.findByShortCode(shortCode);
		if (shortUrl.isEmpty()) {
			log.error("Short URL does not exists");
			throw new ResourceNotFoundException("Short URL does not exists");
		}

		Long accessCount = shortUrl.get().getAccessCount();

		return new ResponseEntity<>(accessCount, HttpStatus.OK);
	}

	public void redirect(String shortCode) {
		Optional<ShortUrl> shortUrl = this.urlShortenRepository.findByShortCode(shortCode);
		if (shortUrl.isEmpty()) {
			log.error("Short URL does not exists");
			throw new ResourceNotFoundException("Short URL does not exists");
		}

		Long accessCount = shortUrl.get().getAccessCount();

		shortUrl.get().setAccessCount(accessCount + 1);

		this.urlShortenRepository.save(shortUrl.get());

		this.urlShortenServiceHelper.redirectURL(shortUrl.get());
	}

}
