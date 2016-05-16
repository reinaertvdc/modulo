package be.lambdaware.dao;

import be.lambdaware.models.Objective;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by jensv on 16-May-16.
 */
public interface ObjectiveDAO extends JpaRepository<Objective, Long>  {

    Objective findById(long id);
}
