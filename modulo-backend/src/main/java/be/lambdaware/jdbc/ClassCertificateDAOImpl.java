package be.lambdaware.jdbc;

import be.lambdaware.dao.ClassCertificateDAO;
import be.lambdaware.entities.CertificateEntity;
import be.lambdaware.entities.ClassCertificateEntity;
import be.lambdaware.entities.ClassEntity;
import be.lambdaware.entities.GradeEntity;
import be.lambdaware.mappers.ClassCertificateMapper;
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
    public void create(ClassCertificateEntity entity) {
        String SQL = "INSERT INTO `class_certificate` (`class_id`, `certificate_id`) VALUES (?, ?)";

        //TODO process result our catch SQL Exception
        GeneratedKeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection con) throws SQLException {
                PreparedStatement statement = con.prepareStatement(SQL, Statement.RETURN_GENERATED_KEYS);
                statement.setInt(1, entity.getClassEntity().getId());
                statement.setInt(2, entity.getCertificateEntity().getId());

                return statement;
            }
        }, holder);

//        return holder.getKey().intValue();
    }

    @Override
    public List<ClassCertificateEntity> getByClass(ClassEntity classEntity) {
        String SQL = "SELECT * " +
                "FROM `class_certificate` JOIN `classes` ON  `class_certificate`.`class_id` = `classes`.`id` JOIN `certificate` ON `class_certificate`.`certificate_id` = `certificate`.`id`"  +
                "WHERE `class_id` = ?";
        List<ClassCertificateEntity> classCertificates = jdbcTemplate.query(SQL, new ClassCertificateMapper(), classEntity.getId());
        //TODO catch SQL Exception
        return classCertificates;
    }

    @Override
    public List<ClassCertificateEntity> getByCertificate(CertificateEntity certificateEntity) {
        String SQL = "SELECT * " +
                "FROM `class_certificate` JOIN `classes` ON  `class_certificate`.`class_id` = `classes`.`id` JOIN `certificate` ON `class_certificate`.`certificate_id` = `certificate`.`id`"  +
                "WHERE `certificate_id` = ?";
        List<ClassCertificateEntity> classCertificates = jdbcTemplate.query(SQL, new ClassCertificateMapper(), certificateEntity.getId());
        //TODO catch SQL Exception
        return classCertificates;
    }

    @Override
    public List<ClassCertificateEntity>get(ClassEntity classEntity, CertificateEntity certificateEntity) {
        String SQL = "SELECT * " +
                "FROM `class_certificate` JOIN `classes` ON  `class_certificate`.`class_id` = `classes`.`id` JOIN `certificate` ON `class_certificate`.`certificate_id` = `certificate`.`id` JOIN  `users` ON `classes`.`teacher_id` = `users`.`id`"  +
                "WHERE `certificate_id` = ? AND `class_id` = ?";
        List<ClassCertificateEntity> classCertificate = jdbcTemplate.query(SQL, new ClassCertificateMapper(), classEntity.getId(), certificateEntity.getId());
        //TODO catch SQL Exception
        return classCertificate;
    }

    @Override
    public List<ClassCertificateEntity> getAll() {
        String SQL = "SELECT * " +
                "FROM `class_certificate` JOIN `classes` ON  `class_certificate`.`class_id` = `classes`.`id` JOIN `certificate` ON `class_certificate`.`certificate_id` = `certificate`.`id`";
        List<ClassCertificateEntity> classCertificates = jdbcTemplate.query(SQL, new ClassCertificateMapper());
        //TODO catch SQL Exception
        return classCertificates;
    }

    @Override
    public void delete(ClassEntity classEntity, CertificateEntity certificateEntity) {
        String SQL = "DELETE FROM `class_certificate` WHERE `class_id` = ? AND `certificate_id` = ? ";
        jdbcTemplate.update(SQL, classEntity.getId(), certificateEntity.getId());
        //TODO catch SQL Exception
    }
}
