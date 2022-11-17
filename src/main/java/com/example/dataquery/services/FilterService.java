package com.example.dataquery.services;

import com.example.dataquery.models.PostDTO;
import com.example.dataquery.models.SearchFilter;
import com.example.dataquery.predicates.OperatorPredicate;
import lombok.AllArgsConstructor;
import one.util.streamex.StreamEx;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.example.dataquery.models.Operators.NOT;
import static com.example.dataquery.models.Operators.OR;

@Service
@AllArgsConstructor
public class FilterService {
    private final List<OperatorPredicate> predicates;

    public List<PostDTO> applyFilter(List<SearchFilter> filters, Map<String, PostDTO> posts) {
        var predicate = filters.stream()
                .map(this::buildPredicate)
                .findFirst()
                .orElse(it -> false);

        Optional<String> notPrefix = Optional.ofNullable(filters.get(0).getPrefix())
                .filter(NOT.toString()::equals);

        return posts.values().stream()
                .filter(notPrefix.isPresent() ? predicate.negate() : predicate)
                .sorted(Comparator.comparingInt(PostDTO::getTimestamp))
                .toList();
    }

    private Predicate<PostDTO> buildPredicate(SearchFilter filter) {
        Optional<String> orPrefix = Optional.ofNullable(filter.getPrefix())
                .filter(OR.toString()::equals);

        return predicates.stream()
                .filter(predicate -> predicate.canHandle(filter.getOperator()))
                .map(predicate -> predicate.build(filter))
                .map(predicate -> combinePredicates(orPrefix, predicate))
                .findFirst()
                .orElse(null);
    }

    private Predicate<PostDTO> combinePredicates(Optional<String> orPrefix, Predicate<PostDTO> postDTOPredicate) {
        Predicate<PostDTO> predicatesOR = StreamEx.of(postDTOPredicate).reduce(x -> false, Predicate::or);
        Predicate<PostDTO> predicatesAND = StreamEx.of(postDTOPredicate).reduce(x -> true, Predicate::and);

        return orPrefix.isPresent() ? predicatesOR : predicatesAND;
    }
}
