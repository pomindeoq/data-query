package com.example.dataquery.parsers;

import com.example.dataquery.models.SearchFilter;

import java.util.List;

public interface Parser<T, V> {
    List<T> parse(V query);
}
