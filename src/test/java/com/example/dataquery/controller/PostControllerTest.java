package com.example.dataquery.controller;

import com.example.dataquery.DataQueryApplicationTests;
import com.example.dataquery.exception.GenericException;
import com.example.dataquery.exception.constants.ErrorCodes;
import com.example.dataquery.model.PostDTO;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

class PostControllerTest extends DataQueryApplicationTests {
    @Autowired
    private PostController postController;

    @ParameterizedTest
    @MethodSource("provideSearchQueriesAndResults")
    void searchPosts(String query, List<PostDTO> expectedPosts) {
        List<PostDTO> actualPosts = postController.search(query).getBody();
        assertEquals(expectedPosts, actualPosts);
    }

    private static Stream<Arguments> provideSearchQueriesAndResults() {
        return Stream.of(
                Arguments.of("EQUAL(id,\"second-post\")", List.of(setup_post2())),
                Arguments.of("EQUAL(id,\"second-post\"),LESS_THAN(views,50)", List.of(setup_post2())),
                Arguments.of("EQUAL(id,\"first-post\"),EQUAL(views,1)", List.of(setup_post1())),
                Arguments.of("EQUAL(views,20)", List.of(setup_post2())),
                Arguments.of("NOT(EQUAL(views,20))", List.of(setup_post1(), setup_post3())),
                Arguments.of("NOT(EQUAL(content,\"Hello World\"))", List.of(setup_post2(), setup_post3())),
                Arguments.of("LESS_THAN(views,50)", List.of(setup_post1(), setup_post2())),
                Arguments.of("GREATER_THAN(timestamp,1555832955)", List.of()),
                Arguments.of("AND(EQUAL(content,\"Scala Tutorial\"),EQUAL(views,50))", List.of(setup_post3())),
                Arguments.of("OR(EQUAL(id,\"first-post\"),EQUAL(id,\"second-post\"))", List.of(setup_post1())),
                Arguments.of("OR(EQUAL(views,1),EQUAL(views,100))", List.of(setup_post1()))
        );
    }

    private static PostDTO setup_post1() {
        return PostDTO.builder()
                .id("first-post")
                .title("My First Post")
                .content("Hello World")
                .views(1)
                .timestamp(1555832341)
                .build();
    }

    private static PostDTO setup_post2() {
        return PostDTO.builder()
                .id("second-post")
                .title("My Second Post")
                .content("Java Tutorial")
                .views(20)
                .timestamp(1555832655)
                .build();
    }

    private static PostDTO setup_post3() {
        return PostDTO.builder()
                .id("third-post")
                .title("My Third Post")
                .content("Scala Tutorial")
                .views(50)
                .timestamp(1555832955)
                .build();
    }
}