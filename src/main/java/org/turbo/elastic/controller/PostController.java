package org.turbo.elastic.controller;

import org.turbo.elastic.config.ElasticsearchConfiguration;
import org.turbo.elastic.entity.Post;
import org.turbo.elastic.repository.PostRepository;
import org.turbo.elastic.service.PostService;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.unit.Fuzziness;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.suggest.SuggestBuilder;
import org.elasticsearch.search.suggest.SuggestBuilders;
import org.elasticsearch.search.suggest.SuggestionBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/search")
public class PostController {

    private final PostService postService;
    private final PostRepository postRepository;
    private final ElasticsearchTemplate elasticsearchTemplate;

    private static final String INDEX_NAME = "post";

    @Autowired
    public PostController(PostService postService, PostRepository postRepository, ElasticsearchTemplate elasticsearchTemplate) {
        this.postService = postService;
        this.postRepository = postRepository;
        this.elasticsearchTemplate = elasticsearchTemplate;
    }

    @GetMapping("/get/{title}")
    public List<Post> findByTitleLike(@PathVariable final String title) {
        return postRepository.findByTitleStartingWith(title);
    }

    @GetMapping("/get")
    public Iterable<Post> findByTitleLike() {
        return postRepository.findAll();
    }

    /**
     * Method returns search response with search hits and suggestions
     *
     * @param fieldToSearchName field of the document to search
     * @param searchQuery prefix used to search for suggestions
     * @return
     * @throws IOException
     */
    @GetMapping("/autocomplete")
    public SearchResponse autoCompleteResponse(@RequestParam(value = "search-entity") String fieldToSearchName,
                                                     @RequestParam(value = "search-query") String searchQuery) throws IOException {

        RestHighLevelClient client = ElasticsearchConfiguration.getClient();

        SuggestionBuilder completionSuggestionBuilder =
                SuggestBuilders.completionSuggestion("suggest").prefix(searchQuery);
        SuggestBuilder suggestBuilder = new SuggestBuilder();
        suggestBuilder.addSuggestion("suggest-title", completionSuggestionBuilder);

        // TODO: 14.02.2019 fix setting suggests to search response 
        
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.suggest(suggestBuilder);
        searchSourceBuilder.query(QueryBuilders.prefixQuery(fieldToSearchName, searchQuery)).fetchSource(true);
        searchSourceBuilder.size(5);
        searchSourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        SearchRequest searchRequest = new SearchRequest();
        searchRequest.indices(INDEX_NAME);
        searchRequest.source(searchSourceBuilder);

        SearchResponse searchResponse = client.search(searchRequest, RequestOptions.DEFAULT);

        // Retrieving suggestions
//        CompletionSuggestion completionSuggestion = searchResponse.getSuggest().getSuggestion("suggest-title");
//        for (CompletionSuggestion.Entry entry : completionSuggestion.getEntries()) {
//            for (CompletionSuggestion.Entry.Option option : entry) {
//                String suggestText = option.getText().string();
//                System.out.println(suggestText);
//            }
//        }

        return searchResponse;
    }

    /**
     * Uses elasticsearchTemplate
     *
     * @param fieldToSearchName field of the document to search
     * @param searchQuery text to search
     * @return
     * @throws IOException
     */
    @GetMapping("/test-autocomplete")
    public SearchResponse testAutoCompleteResponse(@RequestParam(value = "search-entity") String fieldToSearchName,
                                               @RequestParam(value = "search-query") String searchQuery) throws IOException {

        SuggestionBuilder completionSuggestionFuzzyBuilder = SuggestBuilders.completionSuggestion("suggest")
                .prefix(searchQuery, Fuzziness.AUTO);

        return elasticsearchTemplate.suggest(new SuggestBuilder().addSuggestion("test-suggest",completionSuggestionFuzzyBuilder), Post.class);
    }
}
