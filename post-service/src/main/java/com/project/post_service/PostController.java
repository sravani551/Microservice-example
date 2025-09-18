package com.project.post_service;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/post")
public class PostController {
    @GetMapping(value = "/{postId}")
    public PostS getPostS(@PathVariable("postId") String postId){
        PostS postOne = new PostS(postId, "Post describtion for" + postId);
        return postOne;

    }
}
