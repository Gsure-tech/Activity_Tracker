package abdul.activity_tracker.services.serviceImpl;

import abdul.activity_tracker.dto.TaskDto;
import abdul.activity_tracker.dto.UserDto;
import abdul.activity_tracker.dto.UserResponseDto;
import abdul.activity_tracker.enums.Gender;
import abdul.activity_tracker.enums.Status;
import abdul.activity_tracker.exceptions.ResourceNotFoundException;
import abdul.activity_tracker.exceptions.UserExistException;
import abdul.activity_tracker.model.Task;
import abdul.activity_tracker.model.User;
import abdul.activity_tracker.repositories.TaskRepository;
import abdul.activity_tracker.repositories.UserRepository;
import abdul.activity_tracker.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final HttpSession session;
    @Override
    public User signup(UserDto userDto) throws UserExistException {
       User user = new User();
       boolean existByEmail = userRepository.findUserByEmail(userDto.getEmail()).isPresent();
            if(existByEmail){
                throw new UserExistException("The user with " + userDto.getEmail() + " already exist","Use another email");
            }
            user.setFirstName(userDto.getFirstName());
         user.setLastName(userDto.getLastName());
         user.setEmail(userDto.getEmail());
         user.setPassword(userDto.getPassword());
         user.setGender(Gender.valueOf(String.valueOf(userDto.getGender())));

        // user.setGender(userDto.getGender());
        return userRepository.save(user);
    }

    @Override
    public UserDto login(String email, String password) {
      User user = userRepository.findByEmailAndPassword(email,password)
              .orElseThrow(()-> new UserExistException("incorrect email or password","Try again"));
       UserDto userDto = new UserDto();
       userDto.setEmail(user.getEmail());
       userDto.setFirstName(user.getFirstName());
       userDto.setLastName(user.getLastName());
       userDto.setGender(user.getGender());
       session.setAttribute("loginUser", user.getId());
       return userDto;
      // return userRepository.findByEmailAndPassword(email,password).orElse(null);
//  return userRepository.findByEmailAndPassword(email,password)
//          .orElseThrow(()-> new UserExistException("incorrect email or password","try again"));
    }

    @Override
    public String logout() {
        UserDto userDto = new UserDto();
        session.invalidate();
        return "user " + session.getAttribute(userDto.getFirstName() + "has logged out successfully");
    }

}
