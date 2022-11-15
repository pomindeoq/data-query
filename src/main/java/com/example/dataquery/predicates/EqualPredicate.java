package com.example.dataquery.predicates;

import com.example.dataquery.models.PostDTO;
import com.example.dataquery.models.SearchFilter;
import org.springframework.stereotype.Component;

import java.util.function.Predicate;

@Component
public class EqualPredicate implements OperatorPredicate {
    @Override
    public Predicate<PostDTO> build(SearchFilter filter) {
        return p -> p.filterBy(filter.getKey()).equals(filter.getValue());
    }

    @Override
    public boolean canHandle(String operator) {
        return operator.equals("EQUAL");
    }
}
