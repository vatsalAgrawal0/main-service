package com.scalable.dto.reminder;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class CreateReminderResponse {
    private boolean active;
    @JsonProperty("created_at")
    private String createdAt;

    private String description;

    @JsonProperty("end_time")
    private String endTime;

    private String id;

    private String name;

    @JsonProperty("notification_type")
    private String notificationType;

    @JsonProperty("recurrence_rule")
    private String recurrenceRule;

    @JsonProperty("start_time")
    private String startTime;

    private String timezone;

    @JsonProperty("updated_at")
    private String updatedAt;

    @JsonProperty("user_id")
    private String userId;
}
