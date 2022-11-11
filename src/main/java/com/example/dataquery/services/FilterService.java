package com.example.dataquery.services;

import com.example.dataquery.models.PostDTO;
import com.example.dataquery.models.SearchFilter;
import com.example.dataquery.predicates.OperatorPredicate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Predicate;

@Service
@RequiredArgsConstructor
public class FilterService {
    List<OperatorPredicate<PostDTO, SearchFilter>> predicates;

    public List<PostDTO> applyFilter(List<SearchFilter> filters, Map<String, PostDTO> posts) {
        var predicate = filters.stream()
                .map(this::buildPredicate)
                .findFirst()
                .orElse(it -> false);

        return posts.values().stream()
                .filter(predicate)
                .toList();
    }

    private Predicate<PostDTO> buildPredicate(SearchFilter filter) {
        return predicates.stream()
                .map(predicate -> predicate.build(filter))
                .reduce(Predicate::or)
                .orElse(null);
    }
}
