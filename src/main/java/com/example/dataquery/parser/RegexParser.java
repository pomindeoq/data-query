package com.example.dataquery.parser;

import com.example.dataquery.exception.constants.ErrorCodes;
import com.example.dataquery.model.SearchFilter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class RegexParser implements Parser<SearchFilter, String> {
    private static final Pattern PATTERN = Pattern.compile("\\b(NOT|AND|OR)?\\(?(EQUAL|GREATER_THAN|LESS_THAN)\\((.+?),\"?(.+?)\"?\\)\\)?,");

    @Override
    public List<SearchFilter> parse(String query) {
        if (query == null) {
            return Collections.emptyList();
        }

        Matcher matcher = PATTERN.matcher(query + ",");
        List<SearchFilter> params = new ArrayList<>(4);
        while (matcher.find()) {
            params.add(SearchFilter.builder()
                    .prefix(matcher.group(1))
                    .operator(matcher.group(2))
                    .key(matcher.group(3))
                    .value(matcher.group(4))
                    .build());
        }
        if (params.isEmpty()) {
            throw ErrorCodes.NOT_VALID_QUERY.getException();
        }
        return params;
    }

}
