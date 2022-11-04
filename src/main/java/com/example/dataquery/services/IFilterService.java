package com.example.dataquery.services;

import com.example.dataquery.models.PostDTO;
import com.example.dataquery.models.SearchCriteria;

import java.util.List;

public interface IFilterService {
    List<SearchCriteria> buildParams(String query);
    List<PostDTO> searchPosts(List<SearchCriteria> params);
    void post(PostDTO post);
}
