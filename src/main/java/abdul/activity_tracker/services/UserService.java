package abdul.activity_tracker.services;

import abdul.activity_tracker.dto.TaskDto;
import abdul.activity_tracker.dto.UserDto;
import abdul.activity_tracker.exceptions.UserExistException;
import abdul.activity_tracker.model.Task;
import abdul.activity_tracker.model.User;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserService {
    User signup(UserDto userDto) throws UserExistException;
    UserDto login(String email, String password);
    Task createTask(TaskDto taskDto);
    Task viewTask(Long taskId);
    List<Task> viewAllTask();
    List <Task> viewPendingTask();
    List<Task> viewCompletedTask(TaskDto taskDto);
    List <Task> viewTaskInProgress(TaskDto taskDto);
    Task updateTask(TaskDto taskDto);
    String deleteTask(Long taskId);
}
