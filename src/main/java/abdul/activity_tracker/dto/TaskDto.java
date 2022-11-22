package abdul.activity_tracker.dto;

import abdul.activity_tracker.enums.Status;
import lombok.Data;

@Data
public class TaskDto {
    private String taskName;
    private String description;
    private Status status;
}
