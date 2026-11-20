package com.sravani.socialmedia.user;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.Test;

/**
 * Resilience4j only finds a fallback whose parameters are the original method's plus a
 * trailing Throwable. A mismatch fails at runtime, only when the circuit opens; this test
 * catches it at build time.
 */
class FallbackSignatureTest {

    @Test
    void postFallbackMatchesProtectedMethod() {
        assertThatCode(() -> PostClient.class.getDeclaredMethod("postsUnavailable", String.class, Throwable.class))
                .doesNotThrowAnyException();
        assertThat(new PostClient(null).postsUnavailable("1", new RuntimeException("down"))).isNull();
    }

    @Test
    void notificationFallbackMatchesProtectedMethod() {
        assertThatCode(() -> NotificationClient.class.getDeclaredMethod(
                "notificationsUnavailable", String.class, Throwable.class)).doesNotThrowAnyException();
        assertThat(new NotificationClient(null).notificationsUnavailable("1", new RuntimeException("down"))).isNull();
    }
}
