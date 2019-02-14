package org.turbo.elastic;

import org.turbo.elastic.entity.Post;
import org.turbo.elastic.service.PostService;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

public class PostServiceTest extends ElasticAutocompleteApplicationTests {

    @Autowired
    private PostService postService;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @Before
    public void before() {
        elasticsearchTemplate.deleteIndex(Post.class);
        elasticsearchTemplate.createIndex(Post.class);
        elasticsearchTemplate.putMapping(Post.class);
        elasticsearchTemplate.refresh(Post.class);
    }
}
