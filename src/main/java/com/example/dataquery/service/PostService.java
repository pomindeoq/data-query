package com.example.dataquery.service;

import com.example.dataquery.model.PostDTO;
import com.example.dataquery.model.SearchFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PostService {
    private final FilterService filterService;
    private final ParserService parserService;
    Map<String, PostDTO> posts = PostDTO.getPosts();

    public List<PostDTO> searchPosts(String query) {
        List<SearchFilter> filters = parserService.parseParameters(query);
        if (filters.isEmpty()) {
            return new LinkedList<>(posts.values());
        } else {
            return filterService.applyFilter(filters, posts);
        }
    }

    public void post(final PostDTO post) {
        posts.put(post.id(), post);
    }
}
