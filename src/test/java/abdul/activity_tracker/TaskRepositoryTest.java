package abdul.activity_tracker;

import abdul.activity_tracker.enums.Status;
import abdul.activity_tracker.model.Task;
import abdul.activity_tracker.model.User;
import abdul.activity_tracker.repositories.TaskRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import javax.swing.text.html.Option;
import java.util.Optional;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@Rollback(false)
public class TaskRepositoryTest {
    @Autowired
    TaskRepository taskRepository;
    @Test
    public void testAddTask(){
        Task task = new Task();
        task.setTaskName("Dinner");
        task.setDescription("Time to take Dinner!!!");
        task.setStatus(Status.PENDING);
        Task savedTask = taskRepository.save(task);

        Assertions.assertThat(savedTask).isNotNull();
        Assertions.assertThat(savedTask.getId()).isGreaterThan(0);
    }
    @Test
    public void testListAllTask(){
        Iterable<Task> tasks = taskRepository.findAll();
        Assertions.assertThat(tasks).hasSizeGreaterThan(0);
        for(Task task:tasks){
            System.out.println(task);
        }
    }
    @Test
    public void testUpdateTask(){
        Long taskId = 11l;
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        Task task = optionalTask.get();
       task.setTaskName("Lunch");
       taskRepository.save(task);
       Task updatedTask = taskRepository.findById(taskId).get();
    }
    @Test
    public void testgetTask(){
        Long taskId = 11l;
        Optional<Task>optionalTask = taskRepository.findById(taskId);
        Assertions.assertThat(optionalTask).isPresent();
        System.out.println(optionalTask.get());
    }
    @Test
    public void testDelete(){
        Long taskId = 11l;
        taskRepository.deleteById(taskId);
        Optional<Task> optionalTask = taskRepository.findById(taskId);
        Assertions.assertThat(optionalTask).isNotNull();
    }

}
