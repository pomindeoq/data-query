package com.example.dataquery.model;

import com.example.dataquery.exception.constants.ErrorCodes;
import lombok.Builder;
import java.util.Map;

@Builder
public record PostDTO(String id, String title, String content, Integer views, Integer timestamp) {

    public Object filterBy(String key) {
        return switch (key) {
            case "id" -> id();
            case "title" -> title();
            case "content" -> content();
            case "views" -> views();
            case "timestamp" -> timestamp();
            default -> throw ErrorCodes.NOT_VALID_PROPERTY.getException();
        };
    }

    public static Map<String, PostDTO> getPosts() {
        PostDTO post1 = new PostDTO("first-post", "My First Post", "Hello World", 1, 1555832341);
        PostDTO post2 = new PostDTO("second-post", "My Second Post", "Java Tutorial", 20, 1555832655);
        PostDTO post3 = new PostDTO("third-post", "My Third Post", "Scala Tutorial", 50, 1555832955);

        return Map.of(post1.id(), post1, post2.id(), post2, post3.id(), post3);
    }
}
