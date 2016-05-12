package be.lambdaware.dao;

// Imports ...

import be.lambdaware.models.Certificate;
import be.lambdaware.models.StudentInfo;
import be.lambdaware.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface StudentInfoDAO extends JpaRepository<StudentInfo, Long> {

    List<StudentInfo> findAllByCertificate(Certificate certificate);
}