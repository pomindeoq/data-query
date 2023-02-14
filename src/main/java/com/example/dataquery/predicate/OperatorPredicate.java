package com.example.dataquery.predicate;

import com.example.dataquery.model.PostDTO;
import com.example.dataquery.model.SearchFilter;

import java.util.function.Predicate;

public interface OperatorPredicate {
    Predicate<PostDTO> build(SearchFilter filter);

    boolean canHandle(String operator);
}
