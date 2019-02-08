package com.autocomplete.elastic.service;

import com.autocomplete.elastic.entity.Post;

import java.util.List;

public interface PostService {
    List<Post> findByTitleLike(String title);
}
