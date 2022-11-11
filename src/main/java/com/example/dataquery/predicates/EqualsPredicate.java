package com.example.dataquery.predicates;

import com.example.dataquery.models.PostDTO;
import com.example.dataquery.models.SearchFilter;

import java.util.function.Predicate;

public class EqualsPredicate implements OperatorPredicate<PostDTO, SearchFilter> {
    @Override
    public Predicate<PostDTO> build(SearchFilter filter) {
        return p -> p.filterBy(filter.getKey()).equals(filter.getValue());
    }
}
