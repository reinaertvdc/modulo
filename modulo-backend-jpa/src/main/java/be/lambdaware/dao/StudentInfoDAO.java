package be.lambdaware.dao;

// Imports ...

import be.lambdaware.models.StudentInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

@Transactional
public interface StudentInfoDAO extends JpaRepository<StudentInfo, Long> {


}