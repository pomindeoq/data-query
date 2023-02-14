package com.example.dataquery.service;

import com.example.dataquery.model.SearchFilter;
import com.example.dataquery.parser.RegexParser;
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
