package abdul.activity_tracker.services.serviceImpl;

import abdul.activity_tracker.dto.TaskDto;
import abdul.activity_tracker.enums.Status;
import abdul.activity_tracker.exceptions.ResourceNotFoundException;
import abdul.activity_tracker.model.Task;
import abdul.activity_tracker.model.User;
import abdul.activity_tracker.repositories.TaskRepository;
import abdul.activity_tracker.repositories.UserRepository;
import abdul.activity_tracker.services.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final HttpSession session;



    @Override
    public Task createTask(TaskDto taskDto) {
        User user = userRepository.findById((long)session.getAttribute("loginUser"))
                .orElseThrow(()-> new ResourceNotFoundException("User not Signed in","Sign in"));
        Task task = new Task();
        task.setTaskName(taskDto.getTaskName());
        task.setDescription(taskDto.getDescription());
        task.setStatus(Status.PENDING);
        task.setUser(user);


        return taskRepository.save(task);
    }

    @Override
    public Task viewTask(Long taskId) {
        User user = userRepository.findById((long)session.getAttribute("loginUser")).get();
        Task task = taskRepository.findTaskByUserAndId(user, taskId)
                .orElseThrow(()->new ResourceNotFoundException("No task available", "Task not found"));
        return task;
    }

    @Override
    public List<Task> viewAllTask() {
        User user = userRepository.findById((long)session.getAttribute("loginUser")).get();
        List<Task> task = taskRepository.findAllByUser(user).orElseThrow(()-> new ResourceNotFoundException("No task available","Task not found"));
        return task;
    }


    @Override
    public List<Task> viewPendingTask() {
        User user = userRepository.findById((long)session.getAttribute("loginUser")).get();
        List<Task> pendingTask = taskRepository.findTaskByStatusAndUser(Status.PENDING,user)
                .orElseThrow(()->new ResourceNotFoundException("Task not found", "No pending Task available"));
        return pendingTask;
    }

    @Override
    public List<Task> viewCompletedTask() {
        User user = userRepository.findById((long)session.getAttribute("loginUser")).get();
        List<Task> completedTask = taskRepository.findTaskByStatusAndUser(Status.DONE,user)
                .orElseThrow(()->new ResourceNotFoundException("Task not found", "No Task completed"));
        return completedTask;
    }

    @Override
    public List<Task> viewTaskInProgress() {
        User user = userRepository.findById((long)session.getAttribute("loginUser")).get();
        List<Task> inProgressTask = taskRepository.findTaskByStatusAndUser(Status.IN_PROGRESS,user)
                .orElseThrow(()->new ResourceNotFoundException("Task not found", "None of your task is in progress"));
        return inProgressTask;
    }



    @Override
    public Task changeStatus( TaskDto taskDto) {
        Long taskId = taskDto.getTaskId();
       if(taskRepository.findById(taskId).isPresent()) {
           Task task = taskRepository.findById(taskId).get();
           task.setStatus(taskDto.getStatus());
           if(taskDto.getStatus() == Status.DONE){
               task.setCompletedAt(LocalDateTime.now());
           }else{
               task.setCompletedAt(null);
           }

           taskRepository.save(task);
           return task;
       }
       throw new ResourceNotFoundException("failed to change status");
    }

    @Override
    public Task updateTask(Long taskId,TaskDto taskDto) {
        if(taskRepository.findById(taskId).isPresent()){
            Task existingTask = taskRepository.findById(taskId).get();
            existingTask.setTaskName(taskDto.getTaskName());
            existingTask.setDescription(taskDto.getDescription());
            taskRepository.save(existingTask);
            return existingTask;
        }
        throw new ResourceNotFoundException("failed to update");
    }

    @Override
    public String deleteTask(Long taskId) {
        User user = userRepository.findById((long)session.getAttribute("loginUser")).get();
        taskRepository.deleteById(taskId);
        return "Task deleted successfully";
    }
}
