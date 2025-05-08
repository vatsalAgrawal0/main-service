package com.scalable.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDetailResponse {
    private String _id;
    private String firstName;
    private String lastName;
    private String email;
    private String city;
    private Boolean weatherUpdates;
    private Boolean receiveQuotes;
    private Boolean dailyReminders;
    private String dateRegistered;
}
