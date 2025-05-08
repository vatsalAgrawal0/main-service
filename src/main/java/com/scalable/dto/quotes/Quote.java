package com.scalable.dto.quotes;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.Instant;

@Data
public class Quote {
    @JsonProperty("_id")
    private String id;

    private String quote;
    private String author;
    private String category;

    private Instant createdDate;

    @JsonProperty("__v")
    private int version;
}
