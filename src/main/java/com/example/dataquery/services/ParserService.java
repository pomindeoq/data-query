package com.example.dataquery.services;

import com.example.dataquery.models.SearchFilter;
import com.example.dataquery.parsers.RegexParser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ParserService {
    private final RegexParser regexParser;

    public List<SearchFilter> parseParameters(String query) {
        return regexParser.parse(query);
    }
}
