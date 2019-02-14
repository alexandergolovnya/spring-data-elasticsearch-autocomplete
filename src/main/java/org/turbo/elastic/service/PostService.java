package org.turbo.elastic.service;

import org.turbo.elastic.entity.Post;

import java.util.List;

public interface PostService {
    List<Post> findByTitleLike(String title);
}
