package be.lambdaware.repos;


import be.lambdaware.enums.UserRole;
import be.lambdaware.models.StudentInfo;
import be.lambdaware.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface UserRepo extends JpaRepository<User, Long> {

    //TODO resource: http://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation

    User findById(long id);

    User findByEmail(String email);

    List<User> findAllByFirstNameIgnoreCase(String firstName);

    List<User> findAllByLastNameIgnoreCase(String lastName);

    List<User> findAllByRole(UserRole role);

    List<User> findAllByEnabled(boolean enabled);

    List<User> findAllByParent(User parent);

    List<User> findAllByLastNameContainingIgnoreCase(String lastName);

    List<User> findAllByFirstNameContainingIgnoreCase(String firstName);

    User findByStudentInfo(StudentInfo studentInfo);
}