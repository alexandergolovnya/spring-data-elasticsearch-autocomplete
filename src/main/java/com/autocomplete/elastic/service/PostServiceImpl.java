package com.autocomplete.elastic.service;

import com.autocomplete.elastic.entity.Post;
import com.autocomplete.elastic.repository.PostRepository;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.SuggestionBuilder;
import org.elasticsearch.search.suggest.term.TermSuggestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public List<Post> findByTitleLike(String title) {

        SearchRequest searchRequest = new SearchRequest("post");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        SuggestionBuilder termSuggestionBuilder =
                SuggestBuilders.termSuggestion("title").text("po");
        SuggestBuilder suggestBuilder = new SuggestBuilder();
        suggestBuilder.addSuggestion("suggest_title", termSuggestionBuilder);
        searchSourceBuilder.suggest(suggestBuilder);

        searchRequest.source(searchSourceBuilder);

//        SearchResponse searchResponse = new SearchResponse();
//        Suggest suggest = searchResponse.getSuggest();
//        TermSuggestion termSuggestion = suggest.getSuggestion("suggest_title");
//        for (TermSuggestion.Entry entry : termSuggestion.getEntries()) {
//            for (TermSuggestion.Entry.Option option : entry) {
//                String suggestText = option.getText().string();
//            }
//        }

        return postRepository.findByTitleLike(title);
    }
}
