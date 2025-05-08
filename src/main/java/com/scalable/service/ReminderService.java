package com.scalable.service;

import com.scalable.config.MainConfig;
import com.scalable.dto.reminder.CreateReminderRequest;
import com.scalable.dto.reminder.CreateReminderResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@Service
public class ReminderService {
    @Autowired
    private MainConfig mainConfig;
    @Autowired
    private RestTemplate restTemplate;

    public ResponseEntity<CreateReminderResponse> createReminder(CreateReminderRequest createReminderRequest) {
        String url = UriComponentsBuilder.newInstance()
                .scheme(mainConfig.getReminderService().getScheme())
                .host(mainConfig.getReminderService().getHost())
                .port(mainConfig.getReminderService().getPort())
                .path(mainConfig.getReminderService().getEndpoint().get("createReminderEndpoint"))
                .encode().build().toUriString();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        return restTemplate.exchange(url,
                HttpMethod.POST,
                new HttpEntity<>(createReminderRequest, headers),
                CreateReminderResponse.class);
    }

    public ResponseEntity<List<CreateReminderResponse>> getAllUserReminders(String userId) {
        String url = UriComponentsBuilder.newInstance()
                .scheme(mainConfig.getReminderService().getScheme())
                .host(mainConfig.getReminderService().getHost())
                .port(mainConfig.getReminderService().getPort())
                .path(mainConfig.getReminderService().getEndpoint().get("getAllUserReminderEndpoint"))
                .queryParam("userId", userId)
                .encode().build().toUriString();

        return restTemplate.exchange(
                url,
                HttpMethod.GET,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<List<CreateReminderResponse>>() {}
        );
    }

    public CreateReminderResponse getReminderById(String reminderId) {
        String url = UriComponentsBuilder.newInstance()
                .scheme(mainConfig.getReminderService().getScheme())
                .host(mainConfig.getReminderService().getHost())
                .port(mainConfig.getReminderService().getPort())
                .path(mainConfig.getReminderService().getEndpoint().get("getReminderById"))
                .queryParam("reminderId", reminderId)
                .encode().build().toUriString();

        return restTemplate.getForObject(url, CreateReminderResponse.class);
    }

    public ResponseEntity<Map<String, String>> deleteReminder(String reminderId) {
        String url = UriComponentsBuilder.newInstance()
                .scheme(mainConfig.getReminderService().getScheme())
                .host(mainConfig.getReminderService().getHost())
                .port(mainConfig.getReminderService().getPort())
                .path(mainConfig.getReminderService().getEndpoint().get("deleteReminder"))
                .queryParam("reminderId", reminderId)
                .encode().build().toUriString();

        return restTemplate.exchange(
                url,
                HttpMethod.DELETE,
                HttpEntity.EMPTY,
                new ParameterizedTypeReference<Map<String, String>>() {}
        );
    }
}
