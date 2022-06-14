package com.example.dataquery.models;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
public class SearchCriteria {
    private String operation;
    private String key;
    private String value;
}
