package com.example.dataquery.service;

import com.example.dataquery.model.PostDTO;
import com.example.dataquery.model.SearchFilter;
import com.example.dataquery.predicate.OperatorPredicate;
import lombok.AllArgsConstructor;
import one.util.streamex.StreamEx;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Stream;

import static com.example.dataquery.model.Operators.NOT;
import static com.example.dataquery.model.Operators.OR;

@Service
@AllArgsConstructor
public class FilterService {
    private final List<OperatorPredicate> predicates;

    public List<PostDTO> applyFilter(List<SearchFilter> filters, Map<String, PostDTO> posts) {
        var predicate = filters.stream()
                .map(this::buildPredicate)
                .findFirst()
                .orElse(it -> false);

        Optional<String> notPrefix = Optional.ofNullable(filters.get(0).prefix())
                .filter(NOT.toString()::equals);

        return posts.values().stream()
                .filter(notPrefix.isPresent() ? predicate.negate() : predicate)
                .sorted(Comparator.comparingInt(PostDTO::timestamp))
                .toList();
    }

    private Predicate<PostDTO> buildPredicate(SearchFilter filter) {
        Optional<String> orPrefix = Optional.ofNullable(filter.prefix())
                .filter(OR.toString()::equals);

        return predicates.stream()
                .filter(predicate -> predicate.canHandle(filter.operator()))
                .map(predicate -> predicate.build(filter))
                .map(predicate -> combinePredicates(orPrefix, predicate))
                .findFirst()
                .orElse(null);
    }

    private Predicate<PostDTO> combinePredicates(Optional<String> orPrefix, Predicate<PostDTO> postDTOPredicate) {
        Predicate<PostDTO> predicatesOR = Stream.of(postDTOPredicate).reduce(x -> false, Predicate::or);
        Predicate<PostDTO> predicatesAND = Stream.of(postDTOPredicate).reduce(x -> true, Predicate::and);

        return orPrefix.isPresent() ? predicatesOR : predicatesAND;
    }
}
