package com.example.dataquery.services;

import com.example.dataquery.models.PostDTO;
import com.example.dataquery.models.SearchCriteria;
import one.util.streamex.StreamEx;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.example.dataquery.models.QueryOperation.*;

@Service
public class FilterService implements IFilterService {
    private final Map<String, PostDTO> posts = new HashMap<>();

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
        for(SearchCriteria param: params ) {
            predicates.add(createPredicate(param));
        }

        return StreamEx.of(posts.values())
                .filter(predicates.stream().reduce(x->true, Predicate::and))
                .collect(Collectors.toList());
    }

    private Predicate<PostDTO> createPredicate(SearchCriteria criteria) {

        return switch (valueOf(criteria.getOperation())) {
            case EQUAL -> post -> post.filterBy(criteria.getKey()).equals(criteria.getValue());
            case AND -> null;
            case OR -> null;
            case NOT -> null;
            case GREATER_THAN -> null;
            case LESS_THAN -> null;
            default -> null;
        };
    }

}
