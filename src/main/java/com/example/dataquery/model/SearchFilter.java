package com.example.dataquery.model;

import lombok.Builder;

@Builder
public record SearchFilter(String prefix, String operator, String key, String value) {}