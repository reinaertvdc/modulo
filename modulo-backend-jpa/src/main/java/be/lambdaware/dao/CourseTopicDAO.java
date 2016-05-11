package be.lambdaware.dao;

import be.lambdaware.models.CourseTopic;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

/**
 * Created by jensv on 11-May-16.
 */

@Transactional
public interface CourseTopicDAO extends JpaRepository<CourseTopic, Long> {
}
