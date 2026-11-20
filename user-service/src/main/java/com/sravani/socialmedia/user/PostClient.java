package com.sravani.socialmedia.user;

import java.util.Arrays;
import java.util.List;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class PostClient {

    private static final Logger log = LoggerFactory.getLogger(PostClient.class);

    private final RestTemplate restTemplate;

    public PostClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /** Returns null (not an empty list) from the fallback so callers can tell "none" from "unavailable". */
    @CircuitBreaker(name = "post-service", fallbackMethod = "postsUnavailable")
    public List<Post> postsForUser(String userId) {
        Post[] posts = restTemplate.getForObject("http://post-service/posts/user/{userId}", Post[].class, userId);
        return posts == null ? List.of() : Arrays.asList(posts);
    }

    List<Post> postsUnavailable(String userId, Throwable cause) {
        log.warn("post-service unavailable for user {}: {}", userId, cause.toString());
        return null;
    }
}
