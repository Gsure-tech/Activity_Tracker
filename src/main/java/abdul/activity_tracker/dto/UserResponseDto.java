package abdul.activity_tracker.dto;

import abdul.activity_tracker.enums.Gender;
import lombok.Data;

@Data
public class UserResponseDto {
    private String firstName;
    private String lastName;
    private String email;
    private Gender gender;
}
