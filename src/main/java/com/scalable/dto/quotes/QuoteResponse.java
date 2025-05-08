package com.scalable.dto.quotes;

import lombok.Data;
import java.util.List;

@Data
public class QuoteResponse {
    private List<Quote> quotes;
}
