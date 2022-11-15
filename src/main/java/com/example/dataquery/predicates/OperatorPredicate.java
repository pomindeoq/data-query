package com.example.dataquery.predicates;

import com.example.dataquery.models.PostDTO;
import com.example.dataquery.models.SearchFilter;

import java.util.function.Predicate;

public interface OperatorPredicate {
    Predicate<PostDTO> build(SearchFilter filter);

    boolean canHandle(String operator);
}
