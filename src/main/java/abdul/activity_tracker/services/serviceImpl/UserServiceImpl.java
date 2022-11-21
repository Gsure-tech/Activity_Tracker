package abdul.activity_tracker.services.serviceImpl;

import abdul.activity_tracker.dto.TaskDto;
import abdul.activity_tracker.dto.UserDto;
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
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.time.LocalDateTime;
import java.util.List;
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    private HttpSession session;
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
      User user = userRepository.findByEmailAndPassword(email,password).get();
       UserDto userDto = new UserDto();
       userDto.setEmail(user.getEmail());
       userDto.setFirstName(user.getFirstName());
       userDto.setLastName(user.getLastName());
       userDto.setGender(user.getGender());
       session.setAttribute("loginUser", user.getId());
    return userDto;
      // return userRepository.findByEmailAndPassword(email,password).orElse(null);
    }


    @Override
    public Task createTask(TaskDto taskDto) {
        User user = userRepository.findById((long)session.getAttribute("loginUser")).get();
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
        Task task = taskRepository.findTaskByUserAndId(user, taskId).orElseThrow();
        return task;
    }

    @Override
    public List<Task> viewAllTask() {
        User user = userRepository.findById((long)session.getAttribute("loginUser")).get();
        List<Task> task = taskRepository.findAllByUser(user).orElseThrow();
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
    public List<Task> viewCompletedTask(TaskDto taskDto) {
        User user = userRepository.findById((long)session.getAttribute("loginUser")).get();
        List<Task> completedTask = taskRepository.findTaskByStatusAndUser(Status.DONE,user)
                .orElseThrow(()->new ResourceNotFoundException("Task not found", "No Task completed"));
        return completedTask;
    }

    @Override
    public List<Task> viewTaskInProgress(TaskDto taskDto) {
        User user = userRepository.findById((long)session.getAttribute("loginUser")).get();
        List<Task> inProgressTask = taskRepository.findTaskByStatusAndUser(Status.IN_PROGRESS,user)
                .orElseThrow(()->new ResourceNotFoundException("Task not found", "None of your task is in progress"));
        return inProgressTask;
    }

    @Override
    public Task updateTask(TaskDto taskDto) {
        return null;
    }

    @Override
    public String deleteTask(Long taskId) {
         taskRepository.deleteById(taskId);
         return "Task deleted successfully";
    }
}
