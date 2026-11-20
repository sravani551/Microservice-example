package com.sravani.socialmedia.user;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockitoBean
    private UserProfileService profileService;

    @Test
    void returnsAggregatedProfile() throws Exception {
        when(profileService.getProfile("9")).thenReturn(new UserProfile(
                new User("9", "User 9", "555"), List.of(new Post("p", "9", "t")), List.of(), false));

        mvc.perform(get("/users/9"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.user.id").value("9"))
                .andExpect(jsonPath("$.posts[0].title").value("t"))
                .andExpect(jsonPath("$.degraded").value(false));
    }
}
