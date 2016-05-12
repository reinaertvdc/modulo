package be.lambdaware.dao;

import be.lambdaware.models.Clazz;
import be.lambdaware.models.CourseTopic;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

/**
 * Created by jensv on 11-May-16.
 */

@Transactional
public interface CourseTopicDAO extends JpaRepository<CourseTopic, Long> {

    CourseTopic findById(long id);

    CourseTopic findByName(String name);

    List<CourseTopic> findAllByClazz(Clazz clazz);

}
