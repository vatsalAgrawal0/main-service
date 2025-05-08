package com.scalable.dto.quotes;

import lombok.Data;
import java.util.Map;

@Data
public class QuoteRequest {
    private Map<String, Object> queryParams;
}
