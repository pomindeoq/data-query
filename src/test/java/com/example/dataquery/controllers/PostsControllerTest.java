package com.example.dataquery.controllers;

import com.example.dataquery.DataQueryApplicationTests;
import com.example.dataquery.exceptions.GenericException;
import com.example.dataquery.exceptions.constants.ErrorCodes;
import com.example.dataquery.models.PostDTO;
import com.example.dataquery.services.FilterService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class PostsControllerTest extends DataQueryApplicationTests {

    @Autowired
    private FilterService filterService;

    @Autowired
    private PostsController postsController;

    @Test
    void searchPostsSingleEqualsIdSecond_success() {
        String query = "EQUAL(id,\"second-post\")";
        List<PostDTO> expectedPosts = List.of(setup_post2());
        List<PostDTO> actualPosts = postsController.search(query).getBody();

        assertEquals(1, actualPosts.size());
        assertEquals(expectedPosts, actualPosts);
    }

    @Test
    void searchPostsDoubleEqualsIdViewsFirst1_success() {
        String query = "EQUAL(id,\"second-post\"),LESS_THAN(views,50)";
        List<PostDTO> expectedPosts = List.of(setup_post2());
        List<PostDTO> actualPosts = postsController.search(query).getBody();

        assertEquals(1, actualPosts.size());
        assertEquals(expectedPosts, actualPosts);
    }

    @Test
    void searchPostsEqualsLessIdViewsSecond50_success() {
        String query = "EQUAL(id,\"first-post\"),EQUAL(views,1)";
        List<PostDTO> expectedPosts = List.of(setup_post1());
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

    @Test
    void searchPostsGreaterThanLastTimestamp_success() {
        String query = "GREATER_THAN(timestamp,1555832955)";
        List<PostDTO> expectedPosts = List.of();
        List<PostDTO> actualPosts = postsController.search(query).getBody();

        assertEquals(0, actualPosts.size());
        assertEquals(expectedPosts, actualPosts);
    }

    @Test
    void searchPostsAndViewsContentScala50_success() {
        String query = "AND(EQUAL(content,\"Scala Tutorial\"),EQUAL(views,50))";
        List<PostDTO> expectedPosts = List.of(setup_post3());
        List<PostDTO> actualPosts = postsController.search(query).getBody();

        assertEquals(1, actualPosts.size());
        assertEquals(expectedPosts, actualPosts);
    }

    @Test
    void searchPostsORidFirstSecond_success() {
        String query = "OR(EQUAL(id,\"first-post\"),EQUAL(id,\"second-post\"))";
        List<PostDTO> expectedPosts = List.of(setup_post1(), setup_post2());
        List<PostDTO> actualPosts = postsController.search(query).getBody();

        assertEquals(2, actualPosts.size());
        assertEquals(expectedPosts, actualPosts);
    }

    @Test
    void searchPostsOR_views_1_OR_100_success() {
        String query = "OR(EQUAL(views,1),EQUAL(views,100))";
        List<PostDTO> expectedPosts = List.of(setup_post1());
        List<PostDTO> actualPosts = postsController.search(query).getBody();

        assertEquals(1, actualPosts.size());
        assertEquals(expectedPosts, actualPosts);
    }

    @Test
    void searchPostsLessThanId_failure() {
        String query = "LESS_THAN(id,\"first-post\")";
        GenericException actualException = assertThrows(GenericException.class, () -> {
            postsController.search(query);
        });
        GenericException expectedException = ErrorCodes.NOT_VALID_OPERATOR_FOR_KEY.getException();
        assertEquals(expectedException.getMessage(), actualException.getMessage());
        assertEquals(expectedException.getCode(), actualException.getCode());
    }

    @Test
    void searchPostsInvalidPropertyView_failure() {
        String query = "EQUAL(view,50)";
        GenericException actualException = assertThrows(GenericException.class, () -> {
            postsController.search(query);
        });
        GenericException expectedException = ErrorCodes.NOT_VALID_PROPERTY.getException();
        assertEquals(expectedException.getMessage(), actualException.getMessage());
        assertEquals(expectedException.getCode(), actualException.getCode());
    }

    @Test
    void searchPostsInvalidOperator_failure() {
        String query = "GREATER_OR_EQUAL(views,50)";
        GenericException actualException = assertThrows(GenericException.class, () -> {
            postsController.search(query);
        });
        GenericException expectedException = ErrorCodes.NOT_VALID_QUERY.getException();
        assertEquals(expectedException.getMessage(), actualException.getMessage());
        assertEquals(expectedException.getCode(), actualException.getCode());
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