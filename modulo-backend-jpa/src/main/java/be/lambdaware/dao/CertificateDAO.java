package be.lambdaware.dao;


import be.lambdaware.models.Certificate;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface CertificateDAO extends JpaRepository<Certificate, Long> {


    Certificate findById(long id);

    List<Certificate> findAllByEnabledOrderByNameAsc(boolean enabled);
}