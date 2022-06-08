package com.example.dataquery.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import one.util.streamex.StreamEx;

@Getter
@AllArgsConstructor
public enum QueryOperation {
    EQUAL("=="),
    AND("&&"),
    OR("||"),
    NOT("!"),
    GREATER_THAN(">"),
    LESS_THAN("<");

    private final String value;

    public static QueryOperation getByValue(final String value) {
        return StreamEx.of(values()).filterBy(QueryOperation::getValue, value).findFirst().orElse(null);
    }
}
