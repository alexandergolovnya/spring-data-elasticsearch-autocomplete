package org.turbo.elastic.loader;

import org.turbo.elastic.entity.Post;
import org.turbo.elastic.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Component
public class Loader {

    private final ElasticsearchOperations elasticsearchOperations;
    private final PostRepository postRepository;

    @Autowired
    public Loader(ElasticsearchOperations elasticsearchOperations, PostRepository postRepository) {
        this.elasticsearchOperations = elasticsearchOperations;
        this.postRepository = postRepository;
    }

    @PostConstruct
    @Transactional
    public void loadData() {
        elasticsearchOperations.putMapping(Post.class);
        getData().forEach(postRepository::save);
    }

    private List<Post> getData() {
        List<Post> posts = new ArrayList<>();
        posts.add(new Post(1L, "Post 1", 85));
        posts.add(new Post(2L, "Post ranked at 25", 25));
        posts.add(new Post(3L, "Some text to read or not", 100));
        posts.add(new Post(4L, "Some not text to read or not southern", 100));
        posts.add(new Post(5L, "Sociable persons are major active for a company", 100));
        posts.add(new Post(6L, "Southwest economy partners", 100));
        posts.add(new Post(7L, "Southern country on this land", 100));
        posts.add(new Post(8L, "Some not speech to read or not sociable", 100));
        posts.add(new Post(9L, "Some not something and another something", 100));
        posts.add(new Post(10L, "Speech not something and another something", 100));
        posts.add(new Post(11L, "Spock not something and another something", 100));
        posts.add(new Post(12L, "Text and smth", 100));
        return posts;
    }
}
