package com.example.dataquery.models;

import com.example.dataquery.exceptions.constants.ErrorCodes;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public static Map<String, PostDTO> getPosts() {

        PostDTO post1 = PostDTO.builder()
                .id("first-post")
                .title("My First Post")
                .content("Hello World")
                .views(1)
                .timestamp(1555832341)
                .build();

        PostDTO post2 = PostDTO.builder()
                .id("second-post")
                .title("My Second Post")
                .content("Java Tutorial")
                .views(20)
                .timestamp(1555832655)
                .build();

        PostDTO post3 = PostDTO.builder()
                .id("third-post")
                .title("My Third Post")
                .content("Scala Tutorial")
                .views(50)
                .timestamp(1555832955)
                .build();

        return Map.of(post1.getId(), post1, post2.getId(), post2, post3.getId(), post3);
    }
}
