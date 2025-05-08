package com.scalable.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scalable.config.MainConfig;
import com.scalable.dto.user.RegisterUserRequest;
import com.scalable.dto.user.RegisterUserResponse;
import com.scalable.dto.user.UserDetailResponse;
import com.scalable.dto.user.UserLoginRequest;
import com.scalable.dto.user.UserLoginResponse;
import com.scalable.service.UserService;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping
public class UserController {
    private final String JWT_TOKEN = "jwtToken";
    @Autowired
    private UserService userService;
    @Autowired
    private MainConfig mainConfig;
    @Autowired
    private ObjectMapper mapper;

    @PostMapping("/register")
    public ResponseEntity<String> register(@RequestBody Map<String, String> credentials) throws JsonProcessingException {
        RegisterUserRequest registerUserRequest = mapper.convertValue(credentials, RegisterUserRequest.class);
        ResponseEntity<RegisterUserResponse> response = userService.registerNewUser(registerUserRequest);

        return ResponseEntity
                .status(response.getStatusCode())
                .headers(response.getHeaders())
                .body(response.getBody() != null ? mapper.writeValueAsString(response.getBody()) : "{}");
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody Map<String, String> credentials, HttpSession session) throws JsonProcessingException {
        UserLoginRequest loginRequest = mapper.convertValue(credentials, UserLoginRequest.class);
        ResponseEntity<UserLoginResponse> response = userService.userLogin(loginRequest);

        if (response.getStatusCode().is2xxSuccessful()) {
            session.setAttribute("userId", response.getBody().getUserId());
            session.setAttribute(JWT_TOKEN, response.getBody().getToken());
        }

        log.info(response.getBody().getUserId());
        log.info(response.getBody().getToken());

        return ResponseEntity
                .status(response.getStatusCode())
                .headers(response.getHeaders())
                .body(response.getBody() != null ? mapper.writeValueAsString(response.getBody()) : "{}");
    }

    @GetMapping("/profile")
    public ResponseEntity<String> profile(HttpSession session) throws JsonProcessingException {
        ResponseEntity<UserDetailResponse> response = userService.getUserDetails(session.getAttribute(JWT_TOKEN).toString());

        return ResponseEntity
                .status(response.getStatusCode())
                .headers(response.getHeaders())
                .body(response.getBody() != null ? mapper.writeValueAsString(response.getBody()) : "{}");
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(HttpSession session) {
        session.invalidate();
        return ResponseEntity.ok("Logged out");
    }
}
