package com.scalable.dto.user;

import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
public class RegisterUserResponse {
    private String userId;
    private String message;
}
