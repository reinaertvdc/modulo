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
    private CertificatesDAO certificatesDAO;

    @Test
    public void testCertificateDAO() {
        // test autowire
        Assert.assertNotNull(certificatesDAO);
        Logger.getLogger("Test CertificatesDAO").info("CertificatesDAO injected succesfully - pass");

        // test get certificate 1
        CertificatesEntity certificatesEntity = new CertificatesEntity();
        certificatesEntity.setId(1);
        certificatesEntity.setName("Metselaar");
        certificatesEntity.setEnabled(true);
        Assert.assertEquals(certificatesEntity, certificatesDAO.get(1));
        Logger.getLogger("Test CertificatesDAO").info("Expected certificate with ID=1 matches certificate from database - pass");

        // test get certificate 2
        certificatesEntity = new CertificatesEntity();
        certificatesEntity.setId(2);
        certificatesEntity.setName("Elektricien");
        certificatesEntity.setEnabled(true);
        Assert.assertEquals(certificatesEntity, certificatesDAO.get(2));
        Logger.getLogger("Test CertificatesDAO").info("Expected certificate with ID=2 matches certificate from database - pass");

        // create certificate
        certificatesEntity = new CertificatesEntity();
        certificatesEntity.setName("Kassier");
        certificatesEntity.setEnabled(true);

        int insertedId = certificatesDAO.create(certificatesEntity);
        certificatesEntity.setId(insertedId);
        CertificatesEntity insertedEntity = certificatesDAO.get(insertedId);

        Assert.assertEquals(certificatesEntity,insertedEntity);
        Logger.getLogger("Test CertificatesDAO").info("Inserted certificate matches our desired certificate - pass");

        certificatesDAO.delete(insertedId);
        try {
            insertedEntity = certificatesDAO.get(insertedId);
            Assert.fail();
        }
        catch (Exception e) {
            Logger.getLogger("Test CertificateDAO").info("Inserted certificate was deleted succesfully - pass");
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
    private SubCertificateCategoryDAO subCertificateCategoryDAO;

    @Test
    public void testSubCertificateCategoryDAO() {
        // test autowire
        Assert.assertNotNull(subCertificateCategoryDAO);
        Logger.getLogger("Test subCertificateCategoryDAO").info("SubCertificateCategoryDAO injected succesfully - pass");


        SubCertificateCategoryEntity subCertificateCategoryEntity = new SubCertificateCategoryEntity();
        subCertificateCategoryEntity.setId(1);
        subCertificateCategoryEntity.setSubCertificateId(1);
        subCertificateCategoryEntity.setName("Afwerking");
        subCertificateCategoryEntity.setDescription("Leerling kan afwerken");
        subCertificateCategoryEntity.setEnabled(true);
        Assert.assertEquals(subCertificateCategoryEntity, subCertificateCategoryDAO.get(1));
        Logger.getLogger("Test SubCertificateCategoryDAO").info("Expected sub_certificate_category with ID=1 matches subcertificatecategory from database - pass");

//        subCertificateEntity = new SubCertificateEntity();
//        subCertificateEntity.setId(6);
//        subCertificateEntity.setCertificateId(2);
//        subCertificateEntity.setName("Belichting");
//        subCertificateEntity.setDescription("De leerling leert belichten");
//        subCertificateEntity.setEnabled(false);
//        Assert.assertEquals(subCertificateEntity, subCertificateDAO.get(6));
//        Logger.getLogger("Test SubCertificateDAO").info("Expected sub_certificate with ID=6 matches certificate from database - pass");
//
//        subCertificateEntity = new SubCertificateEntity();
//        subCertificateEntity.setCertificateId(1);
//        subCertificateEntity.setName("test name");
//        subCertificateEntity.setCustomName("test custom name");
//        subCertificateEntity.setDescription("test description");
//        subCertificateEntity.setCustomDescription("test custom description");
//        subCertificateEntity.setEnabled(true);
//
//        int insertedId = subCertificateDAO.create(subCertificateEntity);
//        subCertificateEntity.setId(insertedId);
//        Assert.assertEquals(subCertificateEntity, subCertificateDAO.get(insertedId));
//        Logger.getLogger("Test SubCertificateDAO").info("Inserted sub_certificate matches our desired certificate - pass");
//
//        subCertificateDAO.delete(insertedId);
//        try {
//            subCertificateEntity = subCertificateDAO.get(insertedId);
//            Assert.fail();
//        } catch (Exception e) {
//            Logger.getLogger("Test SubCertificateDAO").info("Inserted sub_certificate was deleted succesfully - pass");
//        }
//
//
//        List<SubCertificateEntity> entities = new ArrayList<>();
//
//        subCertificateEntity = new SubCertificateEntity();
//        subCertificateEntity.setId(1);
//        subCertificateEntity.setCertificateId(1);
//        subCertificateEntity.setName("Bekisting");
//        subCertificateEntity.setDescription("De leerling leert bekisten");
//        subCertificateEntity.setEnabled(true);
//        entities.add(subCertificateEntity);
//
//        subCertificateEntity = new SubCertificateEntity();
//        subCertificateEntity.setId(2);
//        subCertificateEntity.setCertificateId(1);
//        subCertificateEntity.setName("Fundering");
//        subCertificateEntity.setDescription("De leerling leert funderen");
//        subCertificateEntity.setEnabled(true);
//        entities.add(subCertificateEntity);
//
//        subCertificateEntity = new SubCertificateEntity();
//        subCertificateEntity.setId(3);
//        subCertificateEntity.setCertificateId(1);
//        subCertificateEntity.setName("Cement");
//        subCertificateEntity.setDescription("De leerling leert cement");
//        subCertificateEntity.setEnabled(true);
//        entities.add(subCertificateEntity);
//
//        Object[] converted = entities.toArray();
//        Object[] arrayFromDatabase = subCertificateDAO.getAllByCertificate(1).toArray();
//
//        Assert.assertArrayEquals(converted,arrayFromDatabase);
//        Logger.getLogger("Test SubCertificateDAO").info("All sub_certificates match the database - pass");
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
        Logger.getLogger("Test StudentBGVScoreDAO").info("Inserted student_bgv_score matches our desired student_bgv_score - pass");


        entity.setScore("A");
        studentBGVScoreDAO.update(entity);
        Assert.assertEquals(entity,studentBGVScoreDAO.get(insertedId));
        Logger.getLogger("Test StudentBGVScoreDAO").info("Updated student_bgv_score matches our desired student_bgv_score - pass");

        studentBGVScoreDAO.delete(insertedId);
        try {
            entity = studentBGVScoreDAO.get(insertedId);
            Assert.fail();
        } catch (Exception e) {
            Logger.getLogger("Test StudentBGVScoreDAO").info("Inserted student_bgv_score was deleted succesfully - pass");
        }
    }


    @Autowired
    private StudentPAVScoreDAO studentPAVScoreDAO;

    @Test
    public void testStudentPAVScoreDAO() {
        // test autowire
        Assert.assertNotNull(studentPAVScoreDAO);
        Logger.getLogger("Test StudentPAVScoreDAO").info("StudentPAVScoreDAO injected succesfully - pass");

        StudentPAVScoreEntity entity = new StudentPAVScoreEntity();
        entity.setId(1);
        entity.setStudentId(1);
        entity.setObjectiveId(1);
        entity.setScore("V");
        entity.setGradedDate(Date.valueOf("2016-04-08"));
        entity.setRemarks("Remarks test");

        Assert.assertEquals(studentPAVScoreDAO.get(1),entity);
        Logger.getLogger("Test StudentPAVScoreDAO").info("Expected student_pav_score with ID=1 matches student_pav_score from database - pass");


        entity = new StudentPAVScoreEntity();
        entity.setStudentId(1);
        entity.setObjectiveId(1);
        entity.setScore("V");
        entity.setGradedDate(Date.valueOf("2016-04-08"));
        entity.setRemarks("Remarks test");
        int insertedId = studentPAVScoreDAO.create(entity);
        entity.setId(insertedId);
        Assert.assertEquals(entity,studentPAVScoreDAO.get(insertedId));
        Logger.getLogger("Test StudentPAVScoreDAO").info("Inserted student_pav_score matches our desired student_pav_score - pass");


        entity.setScore("A");
        studentPAVScoreDAO.update(entity);
        Assert.assertEquals(entity,studentPAVScoreDAO.get(insertedId));
        Logger.getLogger("Test StudentPAVScoreDAO").info("Updated student_pav_score matches our desired student_pav_score - pass");

        studentPAVScoreDAO.delete(insertedId);
        try {
            entity = studentPAVScoreDAO.get(insertedId);
            Assert.fail();
        } catch (Exception e) {
            Logger.getLogger("Test StudentPAVScoreDAO").info("Inserted student_pav_score was deleted succesfully - pass");
        }
    }

    @Autowired
    GradeDAO gradeDAO;

    @Test
    public void testGradeDAO() {
        Assert.assertNotNull(gradeDAO);
        Logger.getLogger("Test GradeDAO").info("GradeDO injected successfully - pass");

        GradeEntity entity = new GradeEntity();
        entity.setId(1);
        entity.setName("Graad 1");
        Assert.assertEquals(entity, gradeDAO.get(1));

        entity = new GradeEntity();
        entity.setName("Test Graad");
        int insertedId = gradeDAO.create(entity);
        entity.setId(insertedId);
        Assert.assertEquals(entity, gradeDAO.get(insertedId));
    }

    @Autowired
    ClassCertificateDAO classCertificateDAO;

    @Test
    public void testClassCertificateDAO() {
        // test autowire
        Assert.assertNotNull(classCertificateDAO);
        Logger.getLogger("Test ClassCertificateDAO").info("ClassCertificateDAO injected succesfully - pass");

        // test get class 1 & certificate 1
        ClassCertificateEntity classCertificateEntity = new ClassCertificateEntity(1,1);
        Assert.assertEquals(classCertificateEntity, classCertificateDAO.get(1,1));
        Logger.getLogger("Test ClassCertificateDAO").info("Expected classCertificate with class_id=1 and certificate_id=1. Matches classCertificate from database - pass");

        // test get class 4 & certificate 2
        classCertificateEntity = new ClassCertificateEntity(4,2);
        Assert.assertEquals(classCertificateEntity, classCertificateDAO.get(4,2));
        Logger.getLogger("Test ClassCertificateDAO").info("Expected classCertificate with class_id=4 and certificate_id=2. Matches classCertificate from database - pass");

        // test get class 2 & certificate 4
        try {
            classCertificateEntity = classCertificateDAO.get(-1,-1);
            Assert.fail();
        } catch (Exception e) {
            Logger.getLogger("Test ClassCertificateDAO").info("Expected null. Matches classCertificate from database - pass");
        }

        // create classCertificate with class_id=2 & certificate_id=2
        classCertificateEntity = new ClassCertificateEntity(2,2);
        classCertificateDAO.create(classCertificateEntity);

        ClassCertificateEntity insertedEntity = classCertificateDAO.get(2,2);
        Assert.assertEquals(classCertificateEntity, insertedEntity);
        Logger.getLogger("Test ClassCertificateDAO").info("Inserted classCertificate matches desired classCertificate - pass");


        // Test getByClass
        List<ClassCertificateEntity> entities = new ArrayList<>();
        classCertificateEntity = new ClassCertificateEntity(2,1);
        entities.add(classCertificateEntity);

        classCertificateEntity = new ClassCertificateEntity(2,2);
        entities.add(classCertificateEntity);

        Object[] converted = entities.toArray();
        Object[] arrayFromDatabase = classCertificateDAO.getByClass(2).toArray();

        Assert.assertArrayEquals(converted, arrayFromDatabase);
        Logger.getLogger("Test ClassCertificateDAO").info("All classCertificates with class_id=2 match the database - pass");


        // Test getByCertificate
        entities = new ArrayList<>();
        classCertificateEntity = new ClassCertificateEntity(2,2);
        entities.add(classCertificateEntity);

        classCertificateEntity = new ClassCertificateEntity(3,2);
        entities.add(classCertificateEntity);

        classCertificateEntity = new ClassCertificateEntity(4,2);
        entities.add(classCertificateEntity);

        converted = entities.toArray();
        arrayFromDatabase = classCertificateDAO.getByCertificate(2).toArray();

        Assert.assertArrayEquals(converted, arrayFromDatabase);
        Logger.getLogger("Test ClassCertificateDAO").info("All classCertificates with certificate_id=2 match the database - pass");

        // Delete classCertificate with class_id=2 & certificate_id=2
        classCertificateDAO.delete(2,2);
        try {
            insertedEntity = classCertificateDAO.get(2,2);
            Assert.fail();
        } catch (Exception e) {
            Logger.getLogger("Test ClassCertificateDAO").info("Inserted classCertificate was deleted successfully - pass");
        }
    }


    @Autowired
    private CompetencesDAO competencesDAO;

    @Test
    public void testCompetencesDAO() {

        // test autowire
        Assert.assertNotNull(competencesDAO);
        Logger.getLogger("Test CompetencesDAO").info("CompetencesDAO injected succesfully - pass");

        // test get competence 1
        CompetencesEntity competence = new CompetencesEntity();
        competence.setId(1);
        competence.setSubCertificateCategoryId(2);
        competence.setName("Gereedschap opruimen");
        competence.setDescription("Leerling ruimt gereedschap op");
        competence.setEnabled(true);
        Assert.assertEquals(competence, competencesDAO.get(1));
        Logger.getLogger("Test CompetencesDAO").info("Expected competence with ID=1 matches competence from database - pass");

        // test get competence 6
        competence =  new CompetencesEntity();
        competence.setId(7);
        competence.setSubCertificateCategoryId(3);
        competence.setName("Afwerk Competentie");
        competence.setDescription("Leerling ruimt werkt volledig af");
        competence.setEnabled(true);
        Assert.assertEquals(competence, competencesDAO.get(7));
        Logger.getLogger("Test CompetencesDAO").info("Expected competence with ID=6 matches competence from database - pass");

        competence =  new CompetencesEntity();
        competence.setId(7);
        competence.setSubCertificateCategoryId(3);
        competence.setName("Afwerk Competentie");
        competence.setDescription("Leerling ruimt werkt volledig af");
        competence.setEnabled(true);

        List<CompetencesEntity> entities = new ArrayList<>();
        entities.add(competence);

        Object[] converted = entities.toArray();
        Object[] arrayFromDatabase = competencesDAO.getBySubCertificateCategory(3).toArray();

        Assert.assertArrayEquals(converted,arrayFromDatabase);

        // create competence
        competence = new CompetencesEntity();
        competence.setSubCertificateCategoryId(1);
        competence.setName("Test Competentie");
        competence.setDescription("Leerling test");
        competence.setEnabled(true);

        int insertedId = competencesDAO.create(competence);
        competence.setId(insertedId);
        CompetencesEntity insertedEntity = competencesDAO.get(insertedId);

        Assert.assertEquals(competence,insertedEntity);
        Logger.getLogger("Test CompetencesDAO").info("Inserted competence matches our desired competence - pass");

        competencesDAO.delete(insertedId);
        try {
            insertedEntity = competencesDAO.get(insertedId);
            Assert.fail();
        }
        catch (Exception e) {
            Logger.getLogger("Test CompetencesDAO").info("Inserted competence was deleted succesfully - pass");
        }

    }


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
}
