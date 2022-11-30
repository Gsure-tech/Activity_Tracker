package abdul.activity_tracker.controller;

import abdul.activity_tracker.dto.TaskDto;
import abdul.activity_tracker.dto.UserDto;
import abdul.activity_tracker.dto.UserResponseDto;
import abdul.activity_tracker.exceptions.ResourceNotFoundException;
import abdul.activity_tracker.exceptions.UserExistException;
import abdul.activity_tracker.model.User;
//import abdul.activity_tracker.services.TaskService;
import abdul.activity_tracker.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

@RequiredArgsConstructor
@Controller
@RequestMapping("api/users")
public class UserController {
    private HttpSession session;
    private final UserService userService;
    //private final TaskService taskService;
    @PostMapping("/signup")
public ResponseEntity<UserResponseDto> signup(@RequestBody UserDto userDto) throws UserExistException {
       userService.signup(userDto);
        UserResponseDto userResponseDto = new UserResponseDto();
        BeanUtils.copyProperties(userDto,userResponseDto);
        return new ResponseEntity<>(userResponseDto, HttpStatus.CREATED);

}
//@GetMapping("/login/{email}/{password}")
//public ResponseEntity <UserDto> login(@PathVariable String email, @PathVariable String password){
//UserDto userDto = userService.login(email,password);
//return new ResponseEntity<>(userDto , HttpStatus.OK);
//    }
    @PostMapping("/login")
    public ResponseEntity<UserResponseDto> login(@RequestBody UserDto userDto){
        UserDto userDetails = userService.login(userDto.getEmail(), userDto.getPassword());

       // if(userDetails != null){
//            UserDto userDto = new UserDto();
//            userDto.setEmail(user.getEmail());
//            userDto.setFirstName(user.getFirstName());
//            userDto.setLastName(user.getLastName());
//            userDto.setGender(user.getGender());
           //session.setAttribute("loginUser", userDto.getEmail());
//            User user = new User();
//            //UserDto userDto = new UserDto();
//       userDto.setEmail(user.getEmail());
//       userDto.setFirstName(user.getFirstName());
//       userDto.setLastName(user.getLastName());
//       userDto.setGender(user.getGender());
        UserResponseDto userResponseDto = new UserResponseDto();
        BeanUtils.copyProperties(userDetails,userResponseDto);
        return new ResponseEntity<>(userResponseDto, HttpStatus.OK);
      //  }
      //  throw new ResourceNotFoundException("User not signed in","please login");
      //return new ResponseEntity<>(null,HttpStatus.BAD_REQUEST);
    }
    @GetMapping("/logout")
    public ResponseEntity <String> logout(){
      userService.logout();
      return new ResponseEntity<>("Logout successful",HttpStatus.OK);
    }

}
