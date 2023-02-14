package com.example.dataquery.controller;


import com.example.dataquery.model.PostDTO;
import com.example.dataquery.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;

    @GetMapping("/store/search")
    public ResponseEntity<List<PostDTO>> search(@RequestParam(name = "query", required = false) String query) {
        return ResponseEntity.ok(postService.searchPosts(query));
    }

    @PostMapping("/store")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> post(@RequestBody PostDTO post) {
        postService.post(post);
        return ResponseEntity.noContent().build();
    }
}
