package com.example.dataquery.models;

import com.example.dataquery.exceptions.constants.ErrorCodes;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public class PostDTO {
    private String id;
    private String title;
    private String content;
    private Integer views;
    private Integer timestamp;

    public Object filterBy(String key) {
        return switch (key) {
            case "id" -> this.id;
            case "title" -> this.title;
            case "content" -> this.content;
            case "views" -> this.views;
            case "timestamp" -> this.timestamp;
            default -> throw ErrorCodes.NOT_VALID_PROPERTY.getException();
        };
    }
}
