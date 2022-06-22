package com.example.dataquery.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.apache.commons.lang3.NotImplementedException;

import java.util.NoSuchElementException;

@Getter
@AllArgsConstructor
public enum Operator {
    EQUAL("==") {
        @Override
        public boolean apply(int left, int right) {
            throw new NotImplementedException("method not supported");
        }
    },
    AND("&&") {
        @Override
        public boolean apply(int left, int right) {
            throw new NotImplementedException("method not supported");
        }
    },
    OR("||") {
        @Override
        public boolean apply(int left, int right) {
            throw new NotImplementedException("method not supported");
        }
    },
    NOT("!") {
        @Override
        public boolean apply(int left, int right) {
            throw new NotImplementedException("method not supported");
        }
    },
    GREATER_THAN(">") {
        @Override public boolean apply(int left, int right) {
            return left > right;
        }
    },
    LESS_THAN("<") {
        @Override public boolean apply(int left, int right) {
            return left < right;
        }
    };

    private final String value;

    public static Operator parseOperator(String operator) {
        for (Operator op : values()) {
            if (op.value.equals(operator)) return op;
        }
        throw new NoSuchElementException(String.format("Unknown operator [%s]", operator));
    }

    public abstract boolean apply(int left, int right);
}
