package be.lambdaware.dao;


import be.lambdaware.enums.ClassType;
import be.lambdaware.models.Clazz;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface ClassDAO extends JpaRepository<Clazz, Long> {

    Clazz findById(long id);

    List<Clazz> findAllByType(ClassType type);

}