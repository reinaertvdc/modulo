package be.lambdaware.repos;

import be.lambdaware.models.CourseTopic;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

/**
 * Created by jensv on 11-May-16.
 */

@Transactional
public interface CourseTopicRepo extends JpaRepository<CourseTopic, Long> {

    CourseTopic findById(long id);

    CourseTopic findByName(String name);

}
