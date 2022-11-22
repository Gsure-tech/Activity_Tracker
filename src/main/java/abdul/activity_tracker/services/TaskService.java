package abdul.activity_tracker.services;

import abdul.activity_tracker.dto.TaskDto;
import abdul.activity_tracker.model.Task;

import java.util.List;

public interface TaskService {

    Task createTask(TaskDto taskDto);
    Task viewTask(Long taskId);
    List<Task> viewAllTask();
    List <Task> viewPendingTask();
    List<Task> viewCompletedTask(TaskDto taskDto);
    List <Task> viewTaskInProgress(TaskDto taskDto);
    Task moveTaskToPending(Long taskId, TaskDto taskDto);
    Task moveTaskToDone(Long taskId, TaskDto taskDto);
    Task updateTask(Long taskId,TaskDto taskDto);
    String deleteTask(Long taskId);
}
