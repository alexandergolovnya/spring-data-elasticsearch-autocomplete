package com.autocomplete.elastic.repository;

import com.autocomplete.elastic.entity.Post;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;

public interface PostRepository extends ElasticsearchRepository<Post, Long> {

    List<Post> findByTitleLike(String title);
}
