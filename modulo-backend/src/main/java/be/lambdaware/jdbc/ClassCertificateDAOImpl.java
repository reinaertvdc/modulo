package be.lambdaware.jdbc;

import be.lambdaware.dao.ClassCertificateDAO;
import be.lambdaware.entities.ClassCertificateEntity;
import be.lambdaware.mappers.ClassCertificateMapper;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

/**
 * Created by Vincent on 07/04/16.
 */
public class ClassCertificateDAOImpl extends AbstractDAOImpl implements ClassCertificateDAO {

    @Override
    public void create(ClassCertificateEntity entity) throws DataAccessException {
        String SQL = "INSERT INTO `class_certificate` (`class_id`, `certificate_id`) VALUES (?, ?)";

        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement statement = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
                statement.setInt(1, entity.getClassId());
                statement.setInt(2, entity.getCertificateId());

                return statement;
            }
        }, holder);

//        return holder.getKey().intValue();
    }

    @Override
    public ClassCertificateEntity getByClass(Integer classId) throws DataAccessException {
//        String SQL = "SELECT * " +
//                "FROM `class_certificate` JOIN `classes` ON  `class_certificate`.`class_id` = `classes`.`id` JOIN `certificates` ON `class_certificate`.`certificate_id` = `certificates`.`id` JOIN  `users` ON `classes`.`teacher_id` = `users`.`id`"  +
//                "WHERE `class_id` = ?";
        String SQL = "SELECT * FROM `class_certificate` WHERE `class_id` = ?";
        ClassCertificateEntity classCertificates = jdbcTemplate.queryForObject(SQL, new ClassCertificateMapper(), classId);
        return classCertificates;
    }

    @Override
    public List<ClassCertificateEntity> getByCertificate(Integer certificateId) throws DataAccessException {
//        String SQL = "SELECT * " +
//                "FROM `class_certificate` JOIN `classes` ON  `class_certificate`.`class_id` = `classes`.`id` JOIN `certificates` ON `class_certificate`.`certificate_id` = `certificates`.`id` JOIN  `users` ON `classes`.`teacher_id` = `users`.`id`"  +
//                "WHERE `certificate_id` = ?";
        String SQL = "SELECT * FROM `class_certificate` WHERE `certificate_id` = ?";
        List<ClassCertificateEntity> classCertificates = jdbcTemplate.query(SQL, new ClassCertificateMapper(), certificateId);
        return classCertificates;
    }

    @Override
    public ClassCertificateEntity get(Integer classId, Integer certificateId) throws DataAccessException {
//        String SQL = "SELECT * " +
//                "FROM `class_certificate` JOIN `classes` ON  `class_certificate`.`class_id` = `classes`.`id` JOIN `certificates` ON `class_certificate`.`certificate_id` = `certificates`.`id` JOIN  `users` ON `classes`.`teacher_id` = `users`.`id`"  +
//                "WHERE `certificate_id` = ? AND `class_id` = ?";
        String SQL = "SELECT * FROM `class_certificate` WHERE `class_id` = ? AND `certificate_id` = ?";
        ClassCertificateEntity classCertificate = jdbcTemplate.queryForObject(SQL, new ClassCertificateMapper(), classId, certificateId);
        return classCertificate;
    }

    @Override
    public List<ClassCertificateEntity> getAll() throws DataAccessException {
//        String SQL = "SELECT * " +
//                "FROM `class_certificate` JOIN `classes` ON  `class_certificate`.`class_id` = `classes`.`id` JOIN `certificate` ON `class_certificate`.`certificate_id` = `certificate`.`id`";
        String SQL = "SELECT * FROM `class_certificate`";
        List<ClassCertificateEntity> classCertificates = jdbcTemplate.query(SQL, new ClassCertificateMapper());
        return classCertificates;
    }

    @Override
    public void delete(Integer classId, Integer certificateId) throws DataAccessException {
        String SQL = "DELETE FROM `class_certificate` WHERE `class_id` = ? AND `certificate_id` = ? ";
        jdbcTemplate.update(SQL, classId, certificateId);
    }
}
