package be.lambdaware.dao;

import be.lambdaware.models.Competence;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by reinaert on 5/18/16.
 */
public interface CompetenceDAO extends JpaRepository<Competence, Long> {

    Competence findById(long id);
}
