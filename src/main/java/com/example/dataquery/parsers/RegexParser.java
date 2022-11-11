package com.example.dataquery.parsers;

import com.example.dataquery.exceptions.constants.ErrorCodes;
import com.example.dataquery.models.SearchFilter;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class RegexParser implements Parser<SearchFilter, String> {

    @Override
    public List<SearchFilter> parse(String query) {
        List<SearchFilter> params = new ArrayList<>();
        if (query != null) {
            Pattern pattern = Pattern.compile("\\b(NOT|AND|OR)?\\(?(EQUAL|GREATER_THAN|LESS_THAN)\\((.+?),\"?(.+?)\"?\\)\\)?,");
            Matcher matcher = pattern.matcher(query + ",");
            while (matcher.find()) {
                params.add(new SearchFilter(matcher.group(1), matcher.group(2), matcher.group(3), matcher.group(4)));
            }
            if (params.isEmpty()) {
                throw ErrorCodes.NOT_VALID_QUERY.getException();
            }
        }
        return params;
    }
}
