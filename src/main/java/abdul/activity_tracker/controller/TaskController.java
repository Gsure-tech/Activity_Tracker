package abdul.activity_tracker.controller;

import abdul.activity_tracker.dto.TaskDto;
import abdul.activity_tracker.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequiredArgsConstructor
@RequestMapping("api/users")
public class TaskController {
   private final TaskService taskService;

    @PostMapping("/createTask")
    public ResponseEntity<String> createTask (@RequestBody TaskDto taskDto){
        taskService.createTask(taskDto);
        return  new ResponseEntity<>("Task created successful", HttpStatus.CREATED);
    }
}
