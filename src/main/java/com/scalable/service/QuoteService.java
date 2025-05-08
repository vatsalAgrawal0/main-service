package com.scalable.service;

import com.scalable.config.MainConfig;
import com.scalable.dto.quotes.QuoteRequest;
import com.scalable.dto.quotes.QuoteResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Map;

@Service
public class QuoteService {
    private MainConfig mainConfig;
    private RestTemplate restTemplate;

    public ResponseEntity<QuoteResponse> getQuotes(QuoteRequest quoteRequest) {
        UriComponentsBuilder builder = UriComponentsBuilder.newInstance()
                .scheme(mainConfig.getReminderService().getScheme())
                .host(mainConfig.getReminderService().getHost())
                .port(mainConfig.getReminderService().getPort())
                .path(mainConfig.getReminderService().getEndpoint().get("getQuotesEndpoint"));

        for (Map.Entry<String, Object> entry : quoteRequest.getQueryParams().entrySet()) {
            builder.queryParam(entry.getKey(), entry.getValue());
        }

        String url = builder.encode().toUriString();

        return restTemplate.getForEntity(url, QuoteResponse.class);
    }
}
