package com.example.dataquery.parser;

import java.util.List;

public interface Parser<T, V> {
    List<T> parse(V query);
}
