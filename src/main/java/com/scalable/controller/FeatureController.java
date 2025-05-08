package com.scalable.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scalable.dto.quotes.QuoteRequest;
import com.scalable.dto.quotes.QuoteResponse;
import com.scalable.dto.reminder.CreateReminderRequest;
import com.scalable.dto.reminder.CreateReminderResponse;
import com.scalable.dto.user.UserDetailResponse;
import com.scalable.dto.weather.WeatherResponse;
import com.scalable.service.QuoteService;
import com.scalable.service.ReminderService;
import com.scalable.service.UserService;
import com.scalable.service.WeatherService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping
public class FeatureController {
    @Autowired
    private UserService userService;
    @Autowired
    private WeatherService weatherService;
    @Autowired
    private ReminderService reminderService;
    @Autowired
    private QuoteService quoteService;
    @Autowired
    private ObjectMapper mapper;

    @GetMapping("/subscribed-services")
    public ResponseEntity<String> getAllSubscribedService(HttpSession session) throws JsonProcessingException {
        String jwtToken = session.getAttribute("jwtToken").toString();
        UserDetailResponse userDetail = userService.getUserDetails(jwtToken).getBody();

        List<String> subscribedServiceList = new ArrayList<>();

        if (userDetail.getWeatherUpdates()) {
            subscribedServiceList.add("Weather");
        }
        if (userDetail.getDailyReminders()) {
            subscribedServiceList.add("Reminders");
        }
        if (userDetail.getReceiveQuotes()) {
            subscribedServiceList.add("Quotes");
        }

        return ResponseEntity.ok(subscribedServiceList.toString());
    }

    @GetMapping("/weather")
    public ResponseEntity<String> getWeather(HttpSession session) throws JsonProcessingException {
        String jwtToken = session.getAttribute("jwtToken").toString();
        ResponseEntity<UserDetailResponse> userDetailResponse = userService.getUserDetails(jwtToken);

        String city = userDetailResponse.getBody().getCity();
        boolean weatherSubscribedFlag = userDetailResponse.getBody().getWeatherUpdates();

        if (!weatherSubscribedFlag) {
            return ResponseEntity.status(403).body("Forbidden. Not registered to weather service");
        }

        ResponseEntity<WeatherResponse> response = weatherService.getWeatherForCity(city);

        return ResponseEntity
                .status(response.getStatusCode())
                .headers(response.getHeaders())
                .body(response.getBody() != null ? mapper.writeValueAsString(response.getBody()) : "{}");
    }

    @PostMapping("/reminder")
    public ResponseEntity<String> createReminder(@RequestBody Map<String, String> reminder, HttpSession session) throws JsonProcessingException {
        reminder.put("userId", session.getAttribute("userId").toString());
        CreateReminderRequest reminderRequest = mapper.convertValue(reminder, CreateReminderRequest.class);
        ResponseEntity<CreateReminderResponse> response = reminderService.createReminder(reminderRequest);

        if (!response.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity
                    .status(response.getStatusCode())
                    .headers(response.getHeaders())
                    .body(response.getBody() != null ? mapper.writeValueAsString(response.getBody()) : "{}");
        }

        return ResponseEntity.ok("Reminder Created");
    }

    @GetMapping("/reminder")
    public ResponseEntity<String> getReminder(HttpSession session) throws JsonProcessingException {
        ResponseEntity<List<CreateReminderResponse>> response =
                reminderService.getAllUserReminders(session.getAttribute("userId").toString());

        return ResponseEntity
                .status(response.getStatusCode())
                .headers(response.getHeaders())
                .body(response.getBody() != null ? mapper.writeValueAsString(response.getBody()) : "{}");
    }

    @PostMapping("/reminder/delete")
    public ResponseEntity<String> deleteReminder(@RequestParam String reminderId) throws JsonProcessingException {
        ResponseEntity<Map<String, String>> response = reminderService.deleteReminder(reminderId);

        return ResponseEntity
                .status(response.getStatusCode())
                .headers(response.getHeaders())
                .body(response.getBody() != null ? mapper.writeValueAsString(response.getBody()) : "{}");
    }

    @GetMapping("/quotes")
    public ResponseEntity<String> getQuote(@RequestParam Map<String, String> filter, HttpSession session) throws JsonProcessingException {
        QuoteRequest quoteRequest = new QuoteRequest();
        quoteRequest.setQueryParams(filter);
        ResponseEntity<QuoteResponse> response = quoteService.getQuotes(quoteRequest);

        return ResponseEntity
                .status(response.getStatusCode())
                .headers(response.getHeaders())
                .body(response.getBody() != null ? mapper.writeValueAsString(response.getBody()) : "{}");
    }
}
