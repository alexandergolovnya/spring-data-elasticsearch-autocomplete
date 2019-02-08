package com.autocomplete.elastic.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "post", type = "post", shards = 1, replicas = 0)
public class Post {

    @Id
    private Long id;

    private String title;
    private Integer rank;
}
