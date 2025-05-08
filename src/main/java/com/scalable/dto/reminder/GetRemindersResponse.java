package com.scalable.dto.reminder;

import lombok.Data;
import java.util.List;

@Data
public class GetRemindersResponse {
    private List<CreateReminderResponse> reminders;
}
