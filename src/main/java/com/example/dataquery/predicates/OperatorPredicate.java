package com.example.dataquery.predicates;

import java.util.function.Predicate;

public interface OperatorPredicate<T, V> {
    Predicate<T> build(V filter);
}
