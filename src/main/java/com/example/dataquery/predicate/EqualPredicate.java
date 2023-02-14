package com.example.dataquery.predicate;

import com.example.dataquery.model.PostDTO;
import com.example.dataquery.model.SearchFilter;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;

import java.util.function.Predicate;

import static com.example.dataquery.model.Operators.EQUAL;

@Component
public class EqualPredicate implements OperatorPredicate {
    @Override
    public Predicate<PostDTO> build(SearchFilter filter) {
        String value = filter.value();
        Object parsedValue = NumberUtils.isParsable(value) ? Integer.parseInt(value) : value;
        return p -> p.filterBy(filter.key()).equals(parsedValue);
    }

    @Override
    public boolean canHandle(String operator) {
        return operator.equals(EQUAL.toString());
    }
}
