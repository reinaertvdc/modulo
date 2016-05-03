package be.lambdaware.dao;


import be.lambdaware.models.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

@Transactional
public interface GradeDAO extends JpaRepository<GradeDAO, Long> {


    Grade findById(long id);
}