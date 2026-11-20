package com.sravani.socialmedia.user;

import java.util.List;

/**
 * Aggregated view returned by user-service.
 *
 * @param degraded true when at least one downstream service was unavailable and its
 *                 section was replaced by a fallback (empty list)
 */
public record UserProfile(User user, List<Post> posts, List<Notification> notifications, boolean degraded) {
}
