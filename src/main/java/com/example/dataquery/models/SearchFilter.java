package com.example.dataquery.models;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class SearchFilter {
    private String prefix;
    private String operator;
    private String key;
    private String value;
}
