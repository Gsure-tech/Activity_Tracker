package abdul.activity_tracker.controller;

import abdul.activity_tracker.dto.TaskDto;
import abdul.activity_tracker.exceptions.ResourceNotFoundException;
import abdul.activity_tracker.model.Task;
import abdul.activity_tracker.repositories.UserRepository;
import abdul.activity_tracker.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.util.List;

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
    @GetMapping("/viewTask/{taskId}")
    public ResponseEntity<Task> viewTask(@PathVariable Long taskId){
            Task task =   taskService.viewTask(taskId);
               return new ResponseEntity<>(task,HttpStatus.OK);
    }
    @GetMapping("/viewAllTask")
    public ResponseEntity <List<Task>> viewAllTask(){
        List<Task> task = taskService.viewAllTask();
        return new ResponseEntity<>(task,HttpStatus.OK);
    }
    @GetMapping("/viewPending")
    public ResponseEntity <List<Task>> viewPending(){
        List<Task> task = taskService.viewPendingTask();
        return new ResponseEntity<>(task,HttpStatus.OK);
    }
    @GetMapping("/viewCompleted")
    public ResponseEntity <List<Task>> viewCompleted(){
        List<Task> task = taskService.viewCompletedTask();
        return new ResponseEntity<>(task,HttpStatus.OK);
    }
    @GetMapping("/viewProgress")
    public ResponseEntity <List<Task>> viewTaskInProgress(){
        List<Task> task = taskService.viewTaskInProgress();
        return new ResponseEntity<>(task,HttpStatus.OK);
    }
    @PutMapping("/movepending/{taskId}")
    public  ResponseEntity<Task> moveToPending (@PathVariable Long taskId, TaskDto taskDto){
        return  new ResponseEntity<>(taskService.moveTaskToPending(taskId,taskDto),HttpStatus.OK);
    }
    @PutMapping("/moveprogress/{taskId}")
    public  ResponseEntity<Task> moveToInProgress (@PathVariable Long taskId, TaskDto taskDto){
        return  new ResponseEntity<>(taskService.moveTaskToInProgress(taskId,taskDto),HttpStatus.OK);
    }
    @PutMapping("/movedone/{taskId}")
    public  ResponseEntity<Task> moveToDone (@PathVariable Long taskId, TaskDto taskDto){
        return  new ResponseEntity<>(taskService.moveTaskToDone(taskId,taskDto),HttpStatus.OK);
    }
    @PutMapping("/update/{taskId}")
    public  ResponseEntity<Task> updateTask (@PathVariable Long taskId, TaskDto taskDto){
        return  new ResponseEntity<>(taskService.updateTask(taskId,taskDto),HttpStatus.OK);
    }
    @DeleteMapping("/delete/{taskId}")
    public ResponseEntity<String> deleteTask(@PathVariable Long taskId){
        taskService.deleteTask(taskId);
        return new ResponseEntity<>("Task deleted successfully", HttpStatus.NO_CONTENT);
    }


}
