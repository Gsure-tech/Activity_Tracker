package abdul.activity_tracker.services;

import abdul.activity_tracker.dto.UserDto;
import abdul.activity_tracker.exceptions.UserExistException;
import abdul.activity_tracker.model.User;

public interface UserService {
    User signup(UserDto userDto) throws UserExistException;
    UserDto login(String email, String password);
    String logout();

}
