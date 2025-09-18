package com.project.notification_service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/notifications")
public class NotificationsController {

    @GetMapping(value = "/{notificationId}")
    public Notifications getNotifications(@PathVariable("notificationId") String notificationId){
        Notifications notificationOne = new Notifications(notificationId, "notification details" + notificationId);

        return notificationOne;
    }
}
