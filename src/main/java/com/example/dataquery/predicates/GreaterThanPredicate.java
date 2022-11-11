package com.example.dataquery.predicates;

import com.example.dataquery.exceptions.constants.ErrorCodes;

import com.example.dataquery.models.PostDTO;
import com.example.dataquery.models.SearchFilter;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.function.Predicate;

public class GreaterThanPredicate implements OperatorPredicate<PostDTO, SearchFilter> {
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
}
