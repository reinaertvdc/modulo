/*
 *  Created by Lambdaware as part of the course "Software Engineering" @ Hasselt University.
 */

package be.lambdaware.dao;


import be.lambdaware.models.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;

@Transactional
public interface CertificateDAO extends JpaRepository<Certificate, Long> {


    Certificate findById(long id);
}