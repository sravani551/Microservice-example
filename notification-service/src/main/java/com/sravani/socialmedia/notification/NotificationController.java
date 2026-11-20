package com.sravani.socialmedia.notification;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @GetMapping("/{notificationId}")
    public Notification getNotification(@PathVariable String notificationId) {
        return new Notification(notificationId, "1", "Notification " + notificationId);
    }

    @GetMapping("/user/{userId}")
    public List<Notification> getNotificationsForUser(@PathVariable String userId) {
        return List.of(new Notification("n-" + userId + "-1", userId, "Welcome, user " + userId + "!"));
    }
}
