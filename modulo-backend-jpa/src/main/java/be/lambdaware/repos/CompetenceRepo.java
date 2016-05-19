package be.lambdaware.repos;

import be.lambdaware.models.Competence;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by reinaert on 5/18/16.
 */
public interface CompetenceRepo extends JpaRepository<Competence, Long> {

    Competence findById(long id);
}
