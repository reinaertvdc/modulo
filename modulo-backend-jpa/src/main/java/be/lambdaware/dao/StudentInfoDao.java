/*
 *  Created by Lambdaware as part of the course "Software Engineering" @ Hasselt University.
 */

package be.lambdaware.dao;

// Imports ...

import be.lambdaware.models.StudentInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

@Transactional
public interface StudentInfoDao extends JpaRepository<StudentInfo, Long> {


}