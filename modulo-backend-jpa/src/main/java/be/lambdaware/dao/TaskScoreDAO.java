package be.lambdaware.dao;

import be.lambdaware.models.Task;
import be.lambdaware.models.TaskScore;
import be.lambdaware.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


/**
 * Created by MichielVM on 5/05/2016.
 */
public interface TaskScoreDAO extends JpaRepository<TaskScore, Long> {

    TaskScore findById(long id);
    Long removeByTask(Task task);
    List<TaskScore> findAllByTask(Task task);
    List<TaskScore> findAllByUserOrderByTaskDeadlineAsc(User user);

}
