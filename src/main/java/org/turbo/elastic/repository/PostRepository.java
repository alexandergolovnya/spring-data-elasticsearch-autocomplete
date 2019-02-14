package org.turbo.elastic.repository;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.turbo.elastic.entity.Post;

import java.util.List;

public interface PostRepository extends ElasticsearchRepository<Post, Long> {

    List<Post> findByTitleStartingWith(String title);
}
