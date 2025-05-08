package com.scalable.dto.reminder;

import lombok.Data;

@Data
public class CreateReminderRequest {
    private String userId;
    private String name;
    private String description;
    private String startTime;
    private String endTime;
    private String recurrenceRule;
    private String timezone;
    private String notificationType;
}
