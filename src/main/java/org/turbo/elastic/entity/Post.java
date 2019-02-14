package org.turbo.elastic.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.CompletionField;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.core.completion.Completion;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(indexName = "post", type = "post", shards = 1, replicas = 0)
public class Post {

    @Id
    private Long id;
    private String title;
    private Integer rank;

    @CompletionField(maxInputLength = 100)
    private Completion suggest;

    public Post(Long id, String title, Integer rank) {
        this.id = id;
        this.title = title;
        this.rank = rank;
    }
}
