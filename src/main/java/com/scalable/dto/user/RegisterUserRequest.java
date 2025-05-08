package com.scalable.dto.user;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class RegisterUserRequest {
    private String firstName;
    private String lastName;
    private String city;
    private String email;
    private String password;
    private Boolean receiveQuotes;
    private Boolean dailyReminders;
    private Boolean weatherUpdates;
}
