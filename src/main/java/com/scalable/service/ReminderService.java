package com.scalable.service;

import com.scalable.config.MainConfig;
import com.scalable.dto.reminder.CreateReminderRequest;
import com.scalable.dto.reminder.CreateReminderResponse;
import com.scalable.dto.reminder.GetRemindersResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class ReminderService {
    private MainConfig mainConfig;
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

    public ResponseEntity<GetRemindersResponse> getAllUserReminders(String userId) {
        String url = UriComponentsBuilder.newInstance()
                .scheme(mainConfig.getReminderService().getScheme())
                .host(mainConfig.getReminderService().getHost())
                .port(mainConfig.getReminderService().getPort())
                .path(mainConfig.getReminderService().getEndpoint().get("getAllUserReminderEndpoint"))
                .queryParam("userId", userId)
                .encode().build().toUriString();

        return restTemplate.getForEntity(url, GetRemindersResponse.class);
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

    public ResponseEntity<String> deleteReminder(String reminderId) {
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
                HttpEntity.EMPTY,       // no headers, no body
                String.class// URI variable
        );
    }
}
