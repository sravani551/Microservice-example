package com.sravani.socialmedia.notification;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(NotificationController.class)
class NotificationControllerTest {

    @Autowired
    private MockMvc mvc;

    @Test
    void fieldsAreMappedCorrectly() throws Exception {
        // Regression test: the original model swapped id and description in its constructor.
        mvc.perform(get("/notifications/5"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("5"))
                .andExpect(jsonPath("$.message").value("Notification 5"));
    }

    @Test
    void returnsNotificationsForUser() throws Exception {
        mvc.perform(get("/notifications/user/3"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userId").value("3"));
    }
}
