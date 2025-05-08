package com.scalable.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scalable.dto.quotes.QuoteRequest;
import com.scalable.dto.quotes.QuoteResponse;
import com.scalable.dto.reminder.CreateReminderRequest;
import com.scalable.dto.reminder.CreateReminderResponse;
import com.scalable.dto.reminder.GetRemindersResponse;
import com.scalable.dto.user.UserDetailResponse;
import com.scalable.dto.weather.WeatherResponse;
import com.scalable.service.QuoteService;
import com.scalable.service.ReminderService;
import com.scalable.service.UserService;
import com.scalable.service.WeatherService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping
public class FeatureController {
    private UserService userService;
    private WeatherService weatherService;
    private ReminderService reminderService;
    private QuoteService quoteService;
    private ObjectMapper objectMapper;

    @Autowired
    public FeatureController(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

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
    public ResponseEntity<String> getWeather(HttpSession session) {
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
                .body(response.getBody() != null ? response.getBody().toString() : "{}");
    }

    @PostMapping("/reminder")
    public ResponseEntity<String> createReminder(@RequestBody Map<String, String> reminder, HttpSession session) {
        reminder.put("userId", session.getAttribute("userId").toString());
        CreateReminderRequest reminderRequest = objectMapper.convertValue(reminder, CreateReminderRequest.class);
        ResponseEntity<CreateReminderResponse> response = reminderService.createReminder(reminderRequest);

        if (!response.getStatusCode().is2xxSuccessful()) {
            return ResponseEntity
                    .status(response.getStatusCode())
                    .headers(response.getHeaders())
                    .body(response.getBody() != null ? response.getBody().toString() : "{}");
        }

        return ResponseEntity.ok("Reminder Created");
    }

    @GetMapping("/reminder")
    public ResponseEntity<String> getReminder(HttpSession session) {
        ResponseEntity<GetRemindersResponse> response = reminderService.getAllUserReminders(session.getAttribute("userId").toString());

        return ResponseEntity
                .status(response.getStatusCode())
                .headers(response.getHeaders())
                .body(response.getBody() != null ? response.getBody().toString() : "{}");
    }

    @PostMapping("/reminder/delete")
    public ResponseEntity<String> getReminder(@RequestBody String reminderId) {
        ResponseEntity<String> response = reminderService.deleteReminder(reminderId);

        return ResponseEntity
                .status(response.getStatusCode())
                .headers(response.getHeaders())
                .body(response.getBody() != null ? response.getBody().toString() : "{}");
    }

    @GetMapping("/quote")
    public ResponseEntity<String> getQuote(@RequestBody Map<String, String> filter, HttpSession session) {
        QuoteRequest quoteRequest = objectMapper.convertValue(filter, QuoteRequest.class);
        ResponseEntity<QuoteResponse> response = quoteService.getQuotes(quoteRequest);

        return ResponseEntity
                .status(response.getStatusCode())
                .headers(response.getHeaders())
                .body(response.getBody() != null ? response.getBody().toString() : "{}");
    }
}
