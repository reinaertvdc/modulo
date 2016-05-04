package be.lambdaware.dao;

import be.lambdaware.models.Clazz;
import be.lambdaware.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by MichielVM on 4/05/2016.
 */

@Transactional
public interface TaskDAO extends JpaRepository<Task, Long> {

    Task findById(long id);

    Task findByName(String name);

    List<Task> findAllByClazz(Clazz clazz);

}
