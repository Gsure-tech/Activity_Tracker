package abdul.activity_tracker.controller;

import abdul.activity_tracker.dto.TaskDto;
import abdul.activity_tracker.exceptions.ResourceNotFoundException;
import abdul.activity_tracker.model.User;
import abdul.activity_tracker.repositories.UserRepository;
import abdul.activity_tracker.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;

@Controller
@RequiredArgsConstructor
@RequestMapping("api/users")
public class TaskController {
   private final TaskService taskService;
   private final UserRepository userRepository;
   private final HttpSession session;
    @PostMapping("/createTask")
    public ResponseEntity<String> createTask (@RequestBody TaskDto taskDto) {
        if (session.getAttribute("loginUser") != null) {
            taskService.createTask(taskDto);
            return new ResponseEntity<>("Task created successful", HttpStatus.CREATED);
        }
       throw new ResourceNotFoundException("User not signed in", "please login");
   // return new ResponseEntity<>("User Not signed in", HttpStatus.BAD_REQUEST);
    }
}
