package com.example.dataquery.predicates;

import com.example.dataquery.exceptions.constants.ErrorCodes;

import com.example.dataquery.models.PostDTO;
import com.example.dataquery.models.SearchFilter;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Component;

import java.util.function.Predicate;

@Component
public class GreaterThanPredicate implements OperatorPredicate {
    @Override
    public Predicate<PostDTO> build(SearchFilter filter) {
        if (!NumberUtils.isParsable(filter.getValue())) {
            throw ErrorCodes.NOT_VALID_OPERATOR_FOR_KEY.getException();
        }
        try {
            return p -> Integer.parseInt((String) p.filterBy(filter.getKey())) > Integer.parseInt(filter.getValue());
        } catch (NumberFormatException ex) {
            System.out.println(ex);
            throw ex;
        }
    }

    @Override
    public boolean canHandle(String operator) {
        return operator.equals("GREATER_THAN");
    }
}
