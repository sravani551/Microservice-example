package com.sravani.socialmedia.user;

/** Client-side view of a post; user-service owns its own copy of the contract. */
public record Post(String id, String userId, String title) {
}
