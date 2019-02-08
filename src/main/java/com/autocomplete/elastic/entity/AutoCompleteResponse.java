package com.autocomplete.elastic.entity;

import lombok.Data;

import java.util.List;

@Data
public class AutoCompleteResponse<T> {

    private Long totalHits;
    private List<T> values;
}
