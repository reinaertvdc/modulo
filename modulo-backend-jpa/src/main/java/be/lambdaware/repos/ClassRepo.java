package be.lambdaware.repos;


import be.lambdaware.enums.ClassType;
import be.lambdaware.models.Clazz;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface ClassRepo extends JpaRepository<Clazz, Long> {

    Clazz findById(long id);

    List<Clazz> findAllByType(ClassType type);

}