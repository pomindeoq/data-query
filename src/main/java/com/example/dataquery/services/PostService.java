package com.example.dataquery.services;

import com.example.dataquery.models.PostDTO;
import com.example.dataquery.models.SearchFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
            return new ArrayList<>(posts.values());
        } else {
            return filterService.applyFilter(filters, posts);
        }
    }

    public void post(final PostDTO post) {
        posts.put(post.getId(), post);
    }
}
