package be.lambdaware.dao;


import be.lambdaware.models.BGVScore;
import be.lambdaware.models.Grade;
import be.lambdaware.models.PAVScore;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

@Transactional
public interface PAVScoreDAO extends JpaRepository<PAVScore, Long> {


    PAVScore findById(long id);


}