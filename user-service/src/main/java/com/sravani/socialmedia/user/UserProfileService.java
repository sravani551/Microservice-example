package com.sravani.socialmedia.user;

import java.util.List;

import org.springframework.stereotype.Service;

/**
 * Builds a user profile from two downstream services. Each dependency has its own circuit
 * breaker, so one failing service degrades only its section instead of the whole response.
 */
@Service
public class UserProfileService {

    private final PostClient postClient;
    private final NotificationClient notificationClient;

    public UserProfileService(PostClient postClient, NotificationClient notificationClient) {
        this.postClient = postClient;
        this.notificationClient = notificationClient;
    }

    public UserProfile getProfile(String userId) {
        User user = new User(userId, "User " + userId, "555-010" + userId);
        List<Post> posts = postClient.postsForUser(userId);
        List<Notification> notifications = notificationClient.notificationsForUser(userId);
        boolean degraded = posts == null || notifications == null;
        return new UserProfile(
                user,
                posts == null ? List.of() : posts,
                notifications == null ? List.of() : notifications,
                degraded);
    }
}
