package be.lambdaware;


import be.lambdaware.application.ModuloBackendApplication;
import be.lambdaware.dao.*;
import be.lambdaware.entities.*;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ModuloBackendApplication.class)
@WebAppConfiguration
public class ModuloBackendApplicationTests {

    @Autowired
    private UserDAO userDAO;

    @Test
    public void testUserDAO() {
        // test autowire
        Assert.assertNotNull(userDAO);
        Logger.getLogger("Test UserDAO").info("UserDAO injected succesfully - pass");

        // test get user 11
        UserEntity userEntity = new UserEntity();
        userEntity.setId(11);
        userEntity.setEmail("leerling1@school.be");
        userEntity.setPassword("b109f3bbbc244eb82441917ed06d618b9008dd09b3befd1b5e07394c706a8bb980b1d7785e5976ec049b46df5f1326af5a2ea6d103fd07c95385ffab0cacbc86");
        userEntity.setType("STUDENT");
        Assert.assertEquals(userEntity, userDAO.get(11));
        Logger.getLogger("Test UserDAO").info("Expected user with ID=11 matches user from database - pass");

        // test get user 12
        userEntity = new UserEntity();
        userEntity.setId(12);
        userEntity.setEmail("leerling2@school.be");
        userEntity.setPassword("b109f3bbbc244eb82441917ed06d618b9008dd09b3befd1b5e07394c706a8bb980b1d7785e5976ec049b46df5f1326af5a2ea6d103fd07c95385ffab0cacbc86");
        userEntity.setType("STUDENT");
        Assert.assertEquals(userEntity, userDAO.get(12));
        Logger.getLogger("Test UserDAO").info("Expected user with ID=11 matches user from database - pass");

        // create user
        userEntity = new UserEntity();
        userEntity.setEmail("test@unit.com");
        userEntity.setPassword("test");
        userEntity.setType("STUDENT");

        int insertedId = userDAO.create(userEntity);
        userEntity.setId(insertedId);
        UserEntity insertedEntity = userDAO.get(insertedId);

        Assert.assertEquals(userEntity, insertedEntity);
        Logger.getLogger("Test UserDAO").info("Inserted user matches our desired user - pass");

        userDAO.delete(insertedId);
        try {
            insertedEntity = userDAO.get(insertedId);
            Assert.fail();
        } catch (Exception e) {
            Logger.getLogger("Test UserDAO").info("Inserted user was deleted succesfully - pass");
        }
    }

    @Autowired
    private CertificateDAO certificateDAO;

    @Test
    public void testCertificateDAO() {
        // test autowire
        Assert.assertNotNull(certificateDAO);
        Logger.getLogger("Test CertificateDAO").info("CertificateDAO injected succesfully - pass");

        // test get user 11
        CertificateEntity certificateEntity = new CertificateEntity();
        certificateEntity.setId(1);
        certificateEntity.setName("Metselaar");
        certificateEntity.setEnabled(1);
        Assert.assertEquals(certificateEntity, certificateDAO.get(1));
        Logger.getLogger("Test CertificateDAO").info("Expected certificate with ID=1 matches certificate from database - pass");

        // test get user 12
        certificateEntity = new CertificateEntity();
        certificateEntity.setId(2);
        certificateEntity.setName("Elektricien");
        certificateEntity.setEnabled(1);
        Assert.assertEquals(certificateEntity, certificateDAO.get(2));
        Logger.getLogger("Test CertificateDAO").info("Expected certificate with ID=2 matches certificate from database - pass");

        // create user
        certificateEntity = new CertificateEntity();
        certificateEntity.setName("Kassier");

        int insertedId = certificateDAO.create(certificateEntity);
        certificateEntity.setId(insertedId);
        CertificateEntity insertedEntity = certificateDAO.get(insertedId);

        Assert.assertEquals(certificateEntity, insertedEntity);
        Logger.getLogger("Test CertificateDAO").info("Inserted certificate matches our desired certificate - pass");

        certificateDAO.delete(insertedId);
        try {
            insertedEntity = certificateDAO.get(insertedId);
            Assert.fail();
        } catch (Exception e) {
            Logger.getLogger("Test UserDAO").info("Inserted certificate was deleted succesfully - pass");
        }
    }

    @Autowired
    private SubCertificateDAO subCertificateDAO;

    @Test
    public void testSubCertificateDAO() {
        // test autowire
        Assert.assertNotNull(subCertificateDAO);
        Logger.getLogger("Test SubCertificateDAO").info("SubCertificateDAO injected succesfully - pass");


        SubCertificateEntity subCertificateEntity = new SubCertificateEntity();
        subCertificateEntity.setId(1);
        subCertificateEntity.setCertificateId(1);
        subCertificateEntity.setName("Bekisting");
        subCertificateEntity.setDescription("De leerling leert bekisten");
        subCertificateEntity.setEnabled(true);
        Assert.assertEquals(subCertificateEntity, subCertificateDAO.get(1));
        Logger.getLogger("Test SubCertificateDAO").info("Expected sub_certificate with ID=1 matches sub_certificate from database - pass");

        subCertificateEntity = new SubCertificateEntity();
        subCertificateEntity.setId(6);
        subCertificateEntity.setCertificateId(2);
        subCertificateEntity.setName("Belichting");
        subCertificateEntity.setDescription("De leerling leert belichten");
        subCertificateEntity.setEnabled(false);
        Assert.assertEquals(subCertificateEntity, subCertificateDAO.get(6));
        Logger.getLogger("Test SubCertificateDAO").info("Expected sub_certificate with ID=6 matches sub_certificate from database - pass");

        subCertificateEntity = new SubCertificateEntity();
        subCertificateEntity.setCertificateId(1);
        subCertificateEntity.setName("test name");
        subCertificateEntity.setCustomName("test custom name");
        subCertificateEntity.setDescription("test description");
        subCertificateEntity.setCustomDescription("test custom description");
        subCertificateEntity.setEnabled(true);

        int insertedId = subCertificateDAO.create(subCertificateEntity);
        subCertificateEntity.setId(insertedId);
        Assert.assertEquals(subCertificateEntity, subCertificateDAO.get(insertedId));
        Logger.getLogger("Test SubCertificateDAO").info("Inserted sub_certificate matches our desired sub_certificate - pass");

        subCertificateDAO.delete(insertedId);
        try {
            subCertificateEntity = subCertificateDAO.get(insertedId);
            Assert.fail();
        } catch (Exception e) {
            Logger.getLogger("Test SubCertificateDAO").info("Inserted sub_certificate was deleted succesfully - pass");
        }


        List<SubCertificateEntity> entities = new ArrayList<>();

        subCertificateEntity = new SubCertificateEntity();
        subCertificateEntity.setId(1);
        subCertificateEntity.setCertificateId(1);
        subCertificateEntity.setName("Bekisting");
        subCertificateEntity.setDescription("De leerling leert bekisten");
        subCertificateEntity.setEnabled(true);
        entities.add(subCertificateEntity);

        subCertificateEntity = new SubCertificateEntity();
        subCertificateEntity.setId(2);
        subCertificateEntity.setCertificateId(1);
        subCertificateEntity.setName("Fundering");
        subCertificateEntity.setDescription("De leerling leert funderen");
        subCertificateEntity.setEnabled(true);
        entities.add(subCertificateEntity);

        subCertificateEntity = new SubCertificateEntity();
        subCertificateEntity.setId(3);
        subCertificateEntity.setCertificateId(1);
        subCertificateEntity.setName("Cement");
        subCertificateEntity.setDescription("De leerling leert cement");
        subCertificateEntity.setEnabled(true);
        entities.add(subCertificateEntity);

        Object[] converted = entities.toArray();
        Object[] arrayFromDatabase = subCertificateDAO.getAllByCertificate(1).toArray();

        Assert.assertArrayEquals(converted,arrayFromDatabase);
        Logger.getLogger("Test SubCertificateDAO").info("All sub_certificates match the database - pass");
    }

    @Autowired
    private StudentBGVScoreDAO studentBGVScoreDAO;

    @Test
    public void testStudentBGVScoreDAO() {
        // test autowire
        Assert.assertNotNull(studentBGVScoreDAO);
        Logger.getLogger("Test StudentBGVScoreDAO").info("StudentBGVScoreDAO injected succesfully - pass");

        StudentBGVScoreEntity entity = new StudentBGVScoreEntity();
        entity.setId(1);
        entity.setStudentId(1);
        entity.setCompetenceId(1);
        entity.setScore("V");
        entity.setGradedDate(Date.valueOf("2016-04-08"));
        entity.setRemarks("Remarks test");

        Assert.assertEquals(studentBGVScoreDAO.get(1),entity);
        Logger.getLogger("Test StudentBGVScoreDAO").info("Expected student_bgv_score with ID=1 matches student_bgv_score from database - pass");


        entity = new StudentBGVScoreEntity();
        entity.setStudentId(1);
        entity.setCompetenceId(1);
        entity.setScore("V");
        entity.setGradedDate(Date.valueOf("2016-04-08"));
        entity.setRemarks("Remarks test");
        int insertedId = studentBGVScoreDAO.create(entity);
        entity.setId(insertedId);
        Assert.assertEquals(entity,studentBGVScoreDAO.get(insertedId));
        Logger.getLogger("Test StudentBGVScoreDAO").info("Inserted student_bgv_score matches our desired sub_certificate - pass");


        entity.setScore("A");
        studentBGVScoreDAO.update(entity);
        Assert.assertEquals(entity,studentBGVScoreDAO.get(insertedId));
        Logger.getLogger("Test StudentBGVScoreDAO").info("Updated student_bgv_score matches our desired sub_certificate - pass");

        studentBGVScoreDAO.delete(insertedId);
        try {
            entity = studentBGVScoreDAO.get(insertedId);
            Assert.fail();
        } catch (Exception e) {
            Logger.getLogger("Test StudentBGVScoreDAO").info("Inserted student_bgv_score was deleted succesfully - pass");
        }
    }

    @Autowired
    ClassCertificateDAO classCertificateDAO;

    @Test
    public void testClassCertificateDAO() {
        // test autowire
        Assert.assertNotNull(certificateDAO);
        Logger.getLogger("Test ClassCertificateDAO").info("ClassCertificateDAO injected succesfully - pass");

        // test get class 1 & certificate 1
        ClassCertificateEntity classCertificateEntity = new ClassCertificateEntity(1,1);
        Assert.assertEquals(classCertificateEntity, classCertificateDAO.get(1,1));
        Logger.getLogger("Test ClassCertificateDAO").info("Expected classCertificate with class_id=1 and certificate_id=1. Matches classCertificate from database - pass");

        // test get class 4 & certificate 2
        classCertificateEntity = new ClassCertificateEntity(4,2);
        Assert.assertEquals(classCertificateEntity, classCertificateDAO.get(4,2));
        Logger.getLogger("Test ClassCertificateDAO").info("Expected classCertificate with class_id=4 and certificate_id=2. Matches classCertificate from database - pass");


//        // create user
//        userEntity = new UserEntity();
//        userEntity.setEmail("test@unit.com");
//        userEntity.setPassword("test");
//        userEntity.setType("STUDENT");
//
//        int insertedId = userDAO.create(userEntity);
//        userEntity.setId(insertedId);
//        UserEntity insertedEntity = userDAO.get(insertedId);
//
//        Assert.assertEquals(userEntity, insertedEntity);
//        Logger.getLogger("Test UserDAO").info("Inserted user matches our desired user - pass");
//
//        userDAO.delete(insertedId);
//        try {
//            insertedEntity = userDAO.get(insertedId);
//            Assert.fail();
//        } catch (Exception e) {
//            Logger.getLogger("Test UserDAO").info("Inserted user was deleted succesfully - pass");
//        }
    }
}
