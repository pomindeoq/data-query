package com.example.dataquery.services;

import com.example.dataquery.models.PostDTO;
import com.example.dataquery.models.SearchCriteria;
import one.util.streamex.StreamEx;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.*;
import java.util.function.Predicate;

import static com.example.dataquery.models.QueryOperation.*;

@Service
public class FilterService implements IFilterService {
    PostDTO post1 = PostDTO.builder()
            .id("first-post")
            .title("My First Post")
            .content("Hello World")
            .views(1)
            .timestamp(1555832341)
            .build();

    PostDTO post2 = PostDTO.builder()
            .id("second")
            .title("second-post")
            .content("Java Tutorial")
            .views(20)
            .timestamp(1555832655)
            .build();

    PostDTO post3 = PostDTO.builder()
            .id("third")
            .title("My Third Post")
            .content("Scala Tutorial")
            .views(50)
            .timestamp(1555832955)
            .build();


    Map<String, PostDTO> posts = Map.of(
            post1.getId(), post1,
            post2.getId(), post2,
            post3.getId(), post3
    );

    @Override
    public List<PostDTO> searchPosts(final List<SearchCriteria> params) {
        if (params.isEmpty()) {
            return new ArrayList<>(posts.values());
        } else {
            return filterWithParams(params);
        }
    }

    @Override
    public void post(final PostDTO post) {
        posts.put(post.getId(), post);
    }

    private List<PostDTO> filterWithParams(List<SearchCriteria> params) {
        List<Predicate<PostDTO>> predicates = new ArrayList<>();
        for (SearchCriteria param : params) {
            predicates.add(createPredicate(param));
        }

        return StreamEx.of(posts.values())
                .filter(predicates.stream().reduce(x -> true, Predicate::and))
                .toList();
    }

    private Predicate<PostDTO> createPredicate(SearchCriteria criteria) {
        return switch (valueOf(criteria.getOperation())) {
            case EQUAL -> post -> doEqualsOperation(criteria, post);
            case GREATER_THAN, LESS_THAN -> post -> doGreaterOrLessOperation(criteria, post);
            default -> throw new IllegalStateException("Unexpected value: " + valueOf(criteria.getOperation()));
        };
    }

    private boolean doGreaterOrLessOperation(SearchCriteria criteria, PostDTO post) {
        if(!NumberUtils.isParsable(criteria.getValue())) {
            throw new RuntimeException("Operator for this key is not valid");
        }
        try {
            return switch (valueOf(criteria.getOperation())) {
                case GREATER_THAN -> greaterThan(Integer.parseInt(String.valueOf(post.filterBy(criteria.getKey()))),
                        Integer.parseInt(criteria.getValue()));
                case LESS_THAN -> lessThan(Integer.parseInt(String.valueOf(post.filterBy(criteria.getKey()))),
                        Integer.parseInt(criteria.getValue()));
                default -> throw new IllegalStateException("Unexpected value: " + valueOf(criteria.getOperation()));
            };
        } catch (NumberFormatException ex) {
            System.out.println(ex);
            throw ex;
        }
    }

    private boolean doEqualsOperation(SearchCriteria criteria, PostDTO post) {
        String value = criteria.getValue();
        Object parsedValue = NumberUtils.isParsable(value) ? Integer.parseInt(value) : value;
        boolean equals = post.filterBy(criteria.getKey()).equals(parsedValue);
        Optional<String> notPrefix = Optional.ofNullable(criteria.getPrefix())
                .filter(NOT.toString()::equals);
        return notPrefix.isPresent() != equals;
    }


    private boolean lessThan(Integer x, Integer y) {
        return x < y;
    }

    private boolean greaterThan(Integer x, Integer y) {
        return x > y;
    }
}
