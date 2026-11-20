package com.sravani.socialmedia.post;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/posts")
public class PostController {

    @GetMapping("/{postId}")
    public Post getPost(@PathVariable String postId) {
        return new Post(postId, "1", "Post " + postId);
    }

    @GetMapping("/user/{userId}")
    public List<Post> getPostsByUser(@PathVariable String userId) {
        return List.of(
                new Post("p-" + userId + "-1", userId, "First post by user " + userId),
                new Post("p-" + userId + "-2", userId, "Second post by user " + userId));
    }
}
