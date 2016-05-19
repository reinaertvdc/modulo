package be.lambdaware.repos;


import be.lambdaware.models.PAVScore;
import be.lambdaware.models.StudentInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface PAVScoreRepo extends JpaRepository<PAVScore, Long> {


    PAVScore findById(long id);

    List<PAVScore> findAllByStudentInfoOrderByWeekAsc(StudentInfo studentInfo);


}