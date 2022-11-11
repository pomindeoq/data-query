package com.example.dataquery.controllers;

import com.example.dataquery.models.EmptyJsonResponse;
import com.example.dataquery.models.PostDTO;
import com.example.dataquery.models.SearchFilter;
import com.example.dataquery.services.FilterService;
import com.example.dataquery.services.ParserService;
import com.example.dataquery.services.PostService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/store")
    public ResponseEntity<List<PostDTO>> search(@RequestParam(name = "query", required = false) String query) {
        return ResponseEntity.ok(postService.searchPosts(query));
    }

    @PostMapping("/store")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<EmptyJsonResponse> post(@RequestBody PostDTO post) {
        postService.post(post);
        return new ResponseEntity<>(new EmptyJsonResponse(), HttpStatus.OK);
    }
}
