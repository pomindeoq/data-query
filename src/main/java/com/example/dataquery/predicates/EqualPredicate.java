package com.example.dataquery.predicates;

import com.example.dataquery.models.PostDTO;
import com.example.dataquery.models.SearchFilter;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;

import java.util.function.Predicate;

import static com.example.dataquery.models.Operators.EQUAL;

@Component
public class EqualPredicate implements OperatorPredicate {
    @Override
    public Predicate<PostDTO> build(SearchFilter filter) {
        String value = filter.getValue();
        Object parsedValue = NumberUtils.isParsable(value) ? Integer.parseInt(value) : value;
        return p -> p.filterBy(filter.getKey()).equals(parsedValue);
    }

    @Override
    public boolean canHandle(String operator) {
        return operator.equals(EQUAL.toString());
    }
}
