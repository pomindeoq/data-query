package com.example.dataquery.predicate;

import com.example.dataquery.exception.constants.ErrorCodes;

import com.example.dataquery.model.Operators;
import com.example.dataquery.model.PostDTO;
import com.example.dataquery.model.SearchFilter;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;

import java.util.function.Predicate;

import static com.example.dataquery.model.Operators.GREATER_THAN;
import static com.example.dataquery.model.Operators.LESS_THAN;

@Component
public class GreaterLessThanPredicate implements OperatorPredicate {
    @Override
    public Predicate<PostDTO> build(SearchFilter filter) {
        if (!NumberUtils.isParsable(filter.value())) {
            throw ErrorCodes.NOT_VALID_OPERATOR_FOR_KEY.getException();
        }
        int value = Integer.parseInt(filter.value());
        switch (Operators.valueOf(filter.operator())) {
            case GREATER_THAN:
                return p -> Integer.parseInt(String.valueOf(p.filterBy(filter.key()))) > value;
            case LESS_THAN:
                return p -> Integer.parseInt(String.valueOf(p.filterBy(filter.key()))) < value;
            default:
                throw ErrorCodes.NOT_VALID_OPERATOR_FOR_KEY.getException();
        }
    }

    @Override
    public boolean canHandle(String operator) {
        return operator.equals(GREATER_THAN.toString()) || operator.equals(LESS_THAN.toString());
    }
}
