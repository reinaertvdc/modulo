/*
 *  Created by Lambdaware as part of the course "Software Engineering" @ Hasselt University.
 */

package be.lambdaware.dao;


import be.lambdaware.models.Clazz;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

@Transactional
public interface ClassDAO extends JpaRepository<Clazz, Long> {

    Clazz findById(long id);


}