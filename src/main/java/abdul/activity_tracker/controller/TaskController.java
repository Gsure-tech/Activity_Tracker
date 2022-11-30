package abdul.activity_tracker.controller;

import abdul.activity_tracker.dto.TaskDto;
import abdul.activity_tracker.dto.TaskResponseDto;
import abdul.activity_tracker.exceptions.ResourceNotFoundException;
import abdul.activity_tracker.model.Task;
import abdul.activity_tracker.repositories.UserRepository;
import abdul.activity_tracker.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("api/users")
public class TaskController {
   private final TaskService taskService;
   private final UserRepository userRepository;
   private final HttpSession session;
    @PostMapping("/createTask")
    public ResponseEntity<TaskResponseDto> createTask (@RequestBody TaskDto taskDto) {
        if (session.getAttribute("loginUser") != null) {
           Task task= taskService.createTask(taskDto);
            TaskResponseDto taskResponseDto = new TaskResponseDto();
            BeanUtils.copyProperties(task,taskResponseDto);
            return new ResponseEntity<>(taskResponseDto, HttpStatus.CREATED);
        }
       throw new ResourceNotFoundException("User not signed in", "please login");
   // return new ResponseEntity<>("User Not signed in", HttpStatus.BAD_REQUEST);
    }
    @GetMapping("/viewTask/{taskId}")
    public ResponseEntity<TaskResponseDto> viewTask(@PathVariable Long taskId){
            Task task =   taskService.viewTask(taskId);
        TaskResponseDto taskResponseDto = new TaskResponseDto();
        BeanUtils.copyProperties(task,taskResponseDto);
        return new ResponseEntity<>(taskResponseDto,HttpStatus.OK);
    }
    @GetMapping("/viewAllTask")
    public ResponseEntity <List<TaskResponseDto>> viewAllTask(){
        List<Task> task = taskService.viewAllTask();
        List <TaskResponseDto> taskResponseDto = new ArrayList<>();
        for(Task tasks: task){
            TaskResponseDto taskResponseDto1 = new TaskResponseDto();
            BeanUtils.copyProperties(tasks,taskResponseDto1);
            taskResponseDto.add(taskResponseDto1);
        }

      //  BeanUtils.copyProperties(task,taskResponseDto);
        return new ResponseEntity(taskResponseDto,HttpStatus.OK);
    }
    @GetMapping("/viewPending")
    public ResponseEntity <List<TaskResponseDto>> viewPending(){
        List<Task> task = taskService.viewPendingTask();
        List <TaskResponseDto> taskResponseDto = new ArrayList<>();
        for(Task tasks: task){
            TaskResponseDto taskResponseDto1 = new TaskResponseDto();
            BeanUtils.copyProperties(tasks,taskResponseDto1);
            taskResponseDto.add(taskResponseDto1);
        }

        //  BeanUtils.copyProperties(task,taskResponseDto);
        return new ResponseEntity(taskResponseDto,HttpStatus.OK);
    }
    @GetMapping("/viewCompleted")
    public ResponseEntity <List<TaskResponseDto>> viewCompleted(){
        List<Task> task = taskService.viewCompletedTask();
        List <TaskResponseDto> taskResponseDto = new ArrayList<>();
        for(Task tasks: task){
            TaskResponseDto taskResponseDto1 = new TaskResponseDto();
            BeanUtils.copyProperties(tasks,taskResponseDto1);
            taskResponseDto.add(taskResponseDto1);
        }

        return new ResponseEntity(taskResponseDto,HttpStatus.OK);
    }
    @GetMapping("/viewProgress")
    public ResponseEntity <List<Task>> viewTaskInProgress(){
        List<Task> task = taskService.viewTaskInProgress();
        List <TaskResponseDto> taskResponseDto = new ArrayList<>();
        for(Task tasks: task){
            TaskResponseDto taskResponseDto1 = new TaskResponseDto();
            BeanUtils.copyProperties(tasks,taskResponseDto1);
            taskResponseDto.add(taskResponseDto1);
        }

        //  BeanUtils.copyProperties(task,taskResponseDto);
        return new ResponseEntity(taskResponseDto,HttpStatus.OK);
    }

    @PutMapping("/changestatus")
    public  ResponseEntity<TaskResponseDto> changeStatus (@RequestBody TaskDto taskDto){
        Task task = taskService.changeStatus(taskDto);
        TaskResponseDto taskResponseDto = new TaskResponseDto();
        BeanUtils.copyProperties(task,taskResponseDto);
        return  new ResponseEntity<>(taskResponseDto,HttpStatus.OK);
    }
    @PutMapping("/update/{taskId}")
    public  ResponseEntity<TaskResponseDto> updateTask (@PathVariable Long taskId, @RequestBody TaskDto taskDto){
       Task task = taskService.updateTask(taskId,taskDto);
        TaskResponseDto taskResponseDto = new TaskResponseDto();
        BeanUtils.copyProperties(task,taskResponseDto);
        return  new ResponseEntity<>(taskResponseDto,HttpStatus.OK);
    }
    @DeleteMapping("/delete/{taskId}")
    public ResponseEntity<String> deleteTask(@PathVariable Long taskId){
        taskService.deleteTask(taskId);
        return new ResponseEntity<>("Task deleted successfully", HttpStatus.NO_CONTENT);
    }


}
