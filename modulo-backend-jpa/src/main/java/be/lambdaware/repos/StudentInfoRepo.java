package be.lambdaware.repos;

// Imports ...

import be.lambdaware.models.Certificate;
import be.lambdaware.models.StudentInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface StudentInfoRepo extends JpaRepository<StudentInfo, Long> {

    List<StudentInfo> findAllByCertificate(Certificate certificate);
}