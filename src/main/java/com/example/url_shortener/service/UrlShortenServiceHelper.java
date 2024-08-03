package com.example.url_shortener.service;

import com.example.url_shortener.entities.ShortUrl;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;

@Component
public class UrlShortenServiceHelper {

	// Simple - add the ASCII charcter.
	// We can make it better to reduce collision
	public String getShortUrl(String url) {
		int hash = 0;
		for (int i = 0; i < url.length(); i++) {
			hash += url.charAt(i) - 'a';
		}
		return String.valueOf(hash);
	}

	public void redirectURL(ShortUrl shortUrl) {
		// Redirect the page
	}

	boolean isValidURL(String url) throws MalformedURLException {
		URI.create(url).toURL();
		return true;
	}

}
