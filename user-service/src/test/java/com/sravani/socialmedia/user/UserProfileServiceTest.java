package com.sravani.socialmedia.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;

class UserProfileServiceTest {

    private final PostClient posts = mock(PostClient.class);
    private final NotificationClient notifications = mock(NotificationClient.class);
    private final UserProfileService service = new UserProfileService(posts, notifications);

    @Test
    void aggregatesBothServices() {
        when(posts.postsForUser("1")).thenReturn(List.of(new Post("p1", "1", "Hello")));
        when(notifications.notificationsForUser("1")).thenReturn(List.of(new Notification("n1", "1", "Hi")));

        UserProfile profile = service.getProfile("1");

        assertThat(profile.user().id()).isEqualTo("1");
        assertThat(profile.posts()).hasSize(1);
        assertThat(profile.notifications()).hasSize(1);
        assertThat(profile.degraded()).isFalse();
    }

    @Test
    void oneFailingServiceDegradesOnlyItsSection() {
        when(posts.postsForUser("1")).thenReturn(null); // circuit open -> fallback
        when(notifications.notificationsForUser("1")).thenReturn(List.of(new Notification("n1", "1", "Hi")));

        UserProfile profile = service.getProfile("1");

        assertThat(profile.posts()).isEmpty();
        assertThat(profile.notifications()).hasSize(1);
        assertThat(profile.degraded()).isTrue();
    }

    @Test
    void userWithNoPostsIsNotDegraded() {
        when(posts.postsForUser("2")).thenReturn(List.of());
        when(notifications.notificationsForUser("2")).thenReturn(List.of());

        assertThat(service.getProfile("2").degraded()).isFalse();
    }
}
