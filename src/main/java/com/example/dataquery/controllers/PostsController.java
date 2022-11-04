package com.example.dataquery.controllers;

import com.example.dataquery.exceptions.GenericException;
import com.example.dataquery.exceptions.constants.ErrorCodes;
import com.example.dataquery.models.EmptyJsonResponse;
import com.example.dataquery.models.PostDTO;
import com.example.dataquery.models.SearchCriteria;
import com.example.dataquery.services.FilterService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@RestController
@AllArgsConstructor
public class PostsController {

    private final FilterService service;

    @GetMapping("/store")
    public ResponseEntity<List<PostDTO>> search(@RequestParam(name = "query", required = false) String query) {
        List<SearchCriteria> params = service.buildParams(query);
        return ResponseEntity.ok(service.searchPosts(params));
    }

    @PostMapping("/store")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<EmptyJsonResponse> post(@RequestBody PostDTO post) {
        service.post(post);
        return new ResponseEntity<>(new EmptyJsonResponse(), HttpStatus.OK);
    }
}
