package com.scalable.dto.user;

import lombok.Data;

@Data
public class UserLoginResponse {
    private String token;
    private String message;
    private String userId;
}
