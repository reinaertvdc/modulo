package be.lambdaware.dao;


import be.lambdaware.models.BGVScore;
import be.lambdaware.models.Grade;
import be.lambdaware.models.StudentInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface BGVScoreDAO extends JpaRepository<BGVScore, Long> {


    BGVScore findById(long id);

    List<BGVScore> findAllByStudentInfoOrderByWeekAsc(StudentInfo studentInfo);


}