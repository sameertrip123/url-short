package com.example.url_shortener.utils;

import com.example.url_shortener.exception.RateLimitException;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@Aspect
@Component
public class RateLimitAspect {

	public static final String ERROR_MESSAGE = "To many request at endpoint %s from IP %s! Please try again after %d milliseconds!";

	private final ConcurrentHashMap<String, List<Long>> requestCounts = new ConcurrentHashMap<>();

	private static final int rateLimit = 10;

	private static final long rateDuration = 60000;

	@Before("@annotation(com.example.url_shortener.utils.WithRateLimitProtection)")
	public void rateLimit() {
		ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder
			.currentRequestAttributes();

		String ipAddress = requestAttributes.getRequest().getRemoteAddr();
		long currentTime = System.currentTimeMillis();

		requestCounts.putIfAbsent(ipAddress, new ArrayList<>());
		requestCounts.get(ipAddress).add(currentTime);
		cleanUpRequestCounts(currentTime);
		if (requestCounts.get(ipAddress).size() > rateLimit) {
			throw new RateLimitException(String.format(ERROR_MESSAGE, requestAttributes.getRequest().getRequestURI(),
					ipAddress, rateDuration));
		}
	}

	private void cleanUpRequestCounts(final long currentTime) {
		requestCounts.values().forEach(l -> {
			l.removeIf(t -> timeIsTooOld(currentTime, t));
		});
	}

	private boolean timeIsTooOld(long currentTime, long timeToCheck) {
		return currentTime - timeToCheck > rateDuration;
	}

}
