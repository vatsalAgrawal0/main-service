package com.scalable.service;

import com.scalable.config.MainConfig;
import com.scalable.dto.quotes.QuoteRequest;
import com.scalable.dto.quotes.QuoteResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Service
public class QuoteService {
    @Autowired
    private MainConfig mainConfig;
    @Autowired
    private RestTemplate restTemplate;

    public ResponseEntity<QuoteResponse> getQuotes(QuoteRequest quoteRequest) {
        UriComponentsBuilder builder = UriComponentsBuilder.newInstance()
                .scheme(mainConfig.getQuoteService().getScheme())
                .host(mainConfig.getQuoteService().getHost())
                .port(mainConfig.getQuoteService().getPort())
                .path(mainConfig.getQuoteService().getEndpoint().get("getQuotesEndpoint"));

        for (Map.Entry<String, String> entry : quoteRequest.getQueryParams().entrySet()) {
            builder.queryParam(entry.getKey(), entry.getValue());
        }

        String url = builder.encode().toUriString();

        return restTemplate.getForEntity(url, QuoteResponse.class);
    }
}
