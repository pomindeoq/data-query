package com.example.dataquery.services;

import ch.qos.logback.classic.Logger;
import com.example.dataquery.exceptions.constants.ErrorCodes;
import com.example.dataquery.models.Operator;
import com.example.dataquery.models.PostDTO;
import com.example.dataquery.models.SearchCriteria;
import lombok.Builder;
import one.util.streamex.StreamEx;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.example.dataquery.models.Operator.*;

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

    Map<String, PostDTO> posts = new HashMap<>() {{
        put(post1.getId(), post1);
        put(post2.getId(), post2);
        put(post3.getId(), post3);
    }};

    public List<SearchCriteria> buildParams(String query) {
        List<SearchCriteria> params = new ArrayList<>();
        if (query != null) {
            Pattern pattern = Pattern.compile("\\b(NOT|AND|OR)?\\(?(EQUAL|GREATER_THAN|LESS_THAN)\\((.+?),\"?(.+?)\"?\\)\\)?,");
            Matcher matcher = pattern.matcher(query + ",");
            while (matcher.find()) {
                params.add(new SearchCriteria(matcher.group(1), matcher.group(2), matcher.group(3), matcher.group(4)));
            }
            if (params.isEmpty()) {
                throw ErrorCodes.NOT_VALID_QUERY.getException();
            }
        }
        return params;
    }

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

        Optional<String> orPrefix = Optional.ofNullable(params.get(0).getPrefix())
                .filter(OR.toString()::equals);

        Predicate<PostDTO> predicatesOR = StreamEx.of(predicates).reduce(x -> false, Predicate::or);
        Predicate<PostDTO> predicatesAND = StreamEx.of(predicates).reduce(x -> true, Predicate::and);

        return StreamEx.of(posts.values())
                .filter(orPrefix.isPresent() ? predicatesOR : predicatesAND)
                .toList();
    }

    private Predicate<PostDTO> createPredicate(SearchCriteria criteria) {
        return switch (valueOf(criteria.getOperator())) {
            case EQUAL -> post -> doEqualsOperation(criteria, post);
            case GREATER_THAN, LESS_THAN -> post -> doGreaterOrLessOperation(criteria, post);
            default -> throw ErrorCodes.NOT_SUPPORTED_OPERATOR.getException();
        };
    }

    private boolean doGreaterOrLessOperation(SearchCriteria criteria, PostDTO post) {
        if (!NumberUtils.isParsable(criteria.getValue())) {
            throw ErrorCodes.NOT_VALID_OPERATOR_FOR_KEY.getException();
        }
        try {
            return Operator.parseOperator(valueOf(criteria.getOperator()).getValue()).apply(
                    Integer.parseInt(String.valueOf(post.filterBy(criteria.getKey()))),
                    Integer.parseInt(criteria.getValue()));
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
}
