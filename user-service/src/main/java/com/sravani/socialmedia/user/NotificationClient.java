package com.sravani.socialmedia.user;

import java.util.Arrays;
import java.util.List;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
public class NotificationClient {

    private static final Logger log = LoggerFactory.getLogger(NotificationClient.class);

    private final RestTemplate restTemplate;

    public NotificationClient(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @CircuitBreaker(name = "notification-service", fallbackMethod = "notificationsUnavailable")
    public List<Notification> notificationsForUser(String userId) {
        Notification[] items = restTemplate.getForObject(
                "http://notification-service/notifications/user/{userId}", Notification[].class, userId);
        return items == null ? List.of() : Arrays.asList(items);
    }

    List<Notification> notificationsUnavailable(String userId, Throwable cause) {
        log.warn("notification-service unavailable for user {}: {}", userId, cause.toString());
        return null;
    }
}
