package abdul.activity_tracker.repositories;

import abdul.activity_tracker.enums.Status;
import abdul.activity_tracker.model.Task;
import abdul.activity_tracker.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepository extends JpaRepository<Task,Long> {
    Optional<Task> findTaskByUserAndId (User user, Long Id);
    Optional <List<Task>> findAllByUser(User user);
    Optional <List<Task>> findTaskByStatusAndUser(Status status, User user);

}
