package com.example.url_shortener.service;

import com.example.url_shortener.entities.ShortUrl;
import org.springframework.stereotype.Component;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

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
        String originalURl = shortUrl.getOriginalUrl();

        // Redirect the page
    }

    boolean isValidURL(String url) throws MalformedURLException, URISyntaxException {
        try {
            new URL(url).toURI();
            return true;
        } catch (MalformedURLException | URISyntaxException e) {
            return false;
        }
    }
}
