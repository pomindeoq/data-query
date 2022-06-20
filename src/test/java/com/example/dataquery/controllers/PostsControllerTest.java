package com.example.dataquery.controllers;

import com.example.dataquery.DataQueryApplication;
import com.example.dataquery.DataQueryApplicationTests;
import com.example.dataquery.models.PostDTO;
import com.example.dataquery.services.FilterService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;;

@ExtendWith(MockitoExtension.class)
class PostsControllerTest extends DataQueryApplicationTests {

    @Autowired
    private FilterService filterService;

    @Autowired
    private PostsController postsController;

    @Test
    void searchPostsSingleEqualsTitleSecond_success() {
        String query = "EQUAL(id,\"second-post\")";
        List<PostDTO> expectedPosts = List.of(setup_post2());
        List<PostDTO> actualPosts = postsController.search(query).getBody();

        assertEquals(1, actualPosts.size());
        assertEquals(expectedPosts, actualPosts);
    }

    @Test
    void searchPostsSingleEqualsViews20_success() {
        String query = "EQUAL(views,20)";
        List<PostDTO> expectedPosts = List.of(setup_post2());
        List<PostDTO> actualPosts = postsController.search(query).getBody();

        assertEquals(1, actualPosts.size());
        assertEquals(expectedPosts, actualPosts);
    }

    @Test
    void searchPostsNotEqualsViews20_success() {
        String query = "NOT(EQUAL(views,20))";
        List<PostDTO> expectedPosts = List.of(setup_post1(), setup_post3());
        List<PostDTO> actualPosts = postsController.search(query).getBody();

        assertEquals(2, actualPosts.size());
        assertEquals(expectedPosts, actualPosts);
    }

    @Test
    void searchPostsNotEqualsContentHello_success() {
        String query = "NOT(EQUAL(content,\"Hello World\"))";
        List<PostDTO> expectedPosts = List.of(setup_post2(), setup_post3());
        List<PostDTO> actualPosts = postsController.search(query).getBody();

        assertEquals(2, actualPosts.size());
        assertEquals(expectedPosts, actualPosts);
    }

    @Test
    void searchPostsLessThanViews50_success() {
        String query = "LESS_THAN(views,50)";
        List<PostDTO> expectedPosts = List.of(setup_post1(), setup_post2());
        List<PostDTO> actualPosts = postsController.search(query).getBody();

        assertEquals(2, actualPosts.size());
        assertEquals(expectedPosts, actualPosts);
    }

    private PostDTO setup_post1() {
        return PostDTO.builder()
                .id("first-post")
                .title("My First Post")
                .content("Hello World")
                .views(1)
                .timestamp(1555832341)
                .build();
    }

    private PostDTO setup_post2() {
        return PostDTO.builder()
                .id("second-post")
                .title("My Second Post")
                .content("Java Tutorial")
                .views(20)
                .timestamp(1555832655)
                .build();
    }

    private PostDTO setup_post3() {
        return PostDTO.builder()
                .id("third-post")
                .title("My Third Post")
                .content("Scala Tutorial")
                .views(50)
                .timestamp(1555832955)
                .build();
    }
}