package com.autocomplete.elastic.controller;

import com.autocomplete.elastic.config.ElasticsearchClient;
import com.autocomplete.elastic.entity.AutoCompleteResponse;
import com.autocomplete.elastic.entity.Post;
import com.autocomplete.elastic.service.PostService;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.suggest.Suggest;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.SuggestionBuilder;
import org.elasticsearch.search.suggest.term.TermSuggestion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/api/search")
public class PostController {

    private final PostService postService;

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping("/suggest/{title}")
    public List<Post> findByTitleLike(@PathVariable final String title) {
        return postService.findByTitleLike(title);
    }

    @GetMapping("/autocomplete")
    public AutoCompleteResponse autoCompleteResponse(@RequestParam(value = "search-entity") String name,
                                                     @RequestParam(value = "search-query") String query) throws IOException {
        SearchRequest searchRequest = new SearchRequest("post"); // name of the index to search
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        String[] includeFields = new String[] {"title"};
        String[] excludeFields = new String[] {};
        searchSourceBuilder.query(QueryBuilders.matchQuery(name, query)).fetchSource(includeFields, excludeFields);
        searchRequest.source(searchSourceBuilder);
        SearchResponse searchResponse = ElasticsearchClient.getClient().search(searchRequest, RequestOptions.DEFAULT);
        SearchHits hits = searchResponse.getHits();
        AutoCompleteResponse<String> autoCompleteResponse = new AutoCompleteResponse<>();
        autoCompleteResponse.setTotalHits(hits.getTotalHits());
        Iterator<SearchHit> iterator = hits.iterator();
        List<String> values = new ArrayList<>();
        autoCompleteResponse.setValues(values);
        int i = 0;
        while (iterator.hasNext()) {
            i++;
            SearchHit hit = iterator.next();
            values.add((String) hit.getSourceAsMap().get("title"));
            if (i == 10) {   // max 10, can be made configurable
                break;
            }
        }

        /**
         * Test code
         */

//        SuggestionBuilder termSuggestionBuilder =
//                SuggestBuilders.termSuggestion(name).text(query);
//        SuggestBuilder suggestBuilder = new SuggestBuilder();
//        suggestBuilder.addSuggestion("suggest_title", termSuggestionBuilder);
//        searchSourceBuilder.suggest(suggestBuilder);
//
//        searchRequest.source(searchSourceBuilder);
//
//        Suggest suggest = searchResponse.getSuggest();
//        TermSuggestion termSuggestion = suggest.getSuggestion("suggest_title");
//        for (TermSuggestion.Entry entry : termSuggestion.getEntries()) {
//            for (TermSuggestion.Entry.Option option : entry) {
//                String suggestText = option.getText().string();
//            }
//        }

        return autoCompleteResponse;
    }
}
