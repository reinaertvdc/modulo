package be.lambdaware.repos;


import be.lambdaware.models.BGVScore;
import be.lambdaware.models.StudentInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface BGVScoreRepo extends JpaRepository<BGVScore, Long> {


    BGVScore findById(long id);

    List<BGVScore> findAllByStudentInfoOrderByWeekAsc(StudentInfo studentInfo);


}