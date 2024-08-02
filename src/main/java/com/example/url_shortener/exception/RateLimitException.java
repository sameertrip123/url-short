package com.example.url_shortener.exception;

public class RateLimitException extends RuntimeException {

    public RateLimitException(final String message) {
        super(message);
    }
}
