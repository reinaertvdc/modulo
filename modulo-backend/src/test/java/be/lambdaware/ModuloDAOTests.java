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
public class ModuloDAOTests {

    @Autowired
    private CertificateDAO certificateDAO;

    @Test
    public void testCertificateDAO() {
        // test autowire
        Assert.assertNotNull(certificateDAO);
        Logger.getLogger("Test CertificateDAO").info("CertificateDAO injected succesfully - pass");

        // test get certificate 1
        CertificateEntity certificateEntity = new CertificateEntity();
        certificateEntity.setId(1);
        certificateEntity.setName("Buitenschrijnwerker");
        certificateEntity.setEnabled(true);
        Assert.assertEquals(certificateEntity, certificateDAO.get(1));
        Logger.getLogger("Test CertificateDAO").info("Expected certificate with ID=1 matches certificate from database - pass");

        // test get certificate 2
        certificateEntity = new CertificateEntity();
        certificateEntity.setId(2);
        certificateEntity.setName("Werkplaatsbinnenschrijnwerker hout");
        certificateEntity.setEnabled(false);
        Assert.assertEquals(certificateEntity, certificateDAO.get(2));
        Logger.getLogger("Test CertificateDAO").info("Expected certificate with ID=2 matches certificate from database - pass");

        // create certificate
        certificateEntity = new CertificateEntity();
        certificateEntity.setName("Kassier");
        certificateEntity.setEnabled(true);

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
            Logger.getLogger("Test CertificateDAO").info("Inserted certificate was deleted succesfully - pass");
        }
    }

    @Autowired
    ClassDAO classDAO;

    @Test
    public void testClassesDAO() {
        // test autowire
        Assert.assertNotNull(classDAO);
        Logger.getLogger("Test ClassDAO").info("ClassDAO injected succesfully - pass");

        ClassEntity entity = new ClassEntity();
        entity.setId(1);
        entity.setTeacherId(21);
        entity.setName("Metselaar 1");
        entity.setType("BGV");

        Assert.assertEquals(entity, classDAO.get(1));
        Logger.getLogger("Test ClassDAO").info("Expected class with ID=1 matches class from database - pass");

        entity = new ClassEntity();
        entity.setName("Test klas");
        entity.setTeacherId(22);
        entity.setType("PAV");
        int insertedId = classDAO.create(entity);
        entity.setId(insertedId);
        Assert.assertEquals(entity, classDAO.get(insertedId));
        Logger.getLogger("Test ClassDAO").info("Inserted class matches our desired class - pass");

        entity.setName("Test Update Klas");
        entity.setType("BGV");
        classDAO.update(entity);
        Assert.assertEquals(entity, classDAO.get(insertedId));
        Logger.getLogger("Test ClassDAO").info("Updated class matches our desired class - pass");

        classDAO.delete(insertedId);
        try {
            classDAO.get(insertedId);
            Assert.fail();
            ;
        } catch (Exception e) {
            Logger.getLogger("Test ClassDAO").info("Inserted class was deleted successfully - pass");
        }

        List<ClassEntity> entities = new ArrayList<>();
        ClassEntity classEntity = new ClassEntity();
        classEntity.setId(1);
        classEntity.setName("Metselaar 1");
        classEntity.setType("BGV");
        classEntity.setTeacherId(21);
        entities.add(classEntity);

        classEntity = new ClassEntity();
        classEntity.setId(2);
        classEntity.setName("Metselaar 2");
        classEntity.setType("BGV");
        classEntity.setTeacherId(21);
        entities.add(classEntity);

        classEntity = new ClassEntity();
        classEntity.setId(3);
        classEntity.setName("Elektricien 1");
        classEntity.setType("BGV");
        classEntity.setTeacherId(21);
        entities.add(classEntity);

        Object[] fromDatabase = classDAO.getAllByTeacher(21).toArray();
        Object[] objectArray = entities.toArray();

        Assert.assertArrayEquals(objectArray, fromDatabase);
        Logger.getLogger("Test ClassDAO").info("Get all classes by teacher id - pass");
    }

    @Autowired
    private SubCertificateDAO subCertificateDAO;

    @Test
    public void testSubCertificateDAO() {
        // test autowire
        Assert.assertNotNull(subCertificateDAO);
        Logger.getLogger("Test SubCertificateDAO").info("SubCertificateDAO injected succesfully - pass");


        SubCertificateEntity entity = new SubCertificateEntity();
        entity.setId(1);
        entity.setCertificateId(1);
        entity.setName("Machinale houtbewerking");
//        entity.setDescription("De leerling leert bekisten");
        entity.setEnabled(true);
        Assert.assertEquals(entity, subCertificateDAO.get(1));
        Logger.getLogger("Test SubCertificateDAO").info("Expected sub_certificate with ID=1 matches sub_certificate from database - pass");

        entity = new SubCertificateEntity();
        entity.setId(6);
        entity.setCertificateId(2);
        entity.setName("Manuele houtbewerking");
//        entity.setDescription("De leerling leert belichten");
        entity.setEnabled(true);
        Assert.assertEquals(entity, subCertificateDAO.get(6));
        Logger.getLogger("Test SubCertificateDAO").info("Expected sub_certificate with ID=6 matches sub_certificate from database - pass");


        entity = new SubCertificateEntity();
        entity.setCertificateId(1);
        entity.setName("test name");
        entity.setCustomName("test custom name");
//        entity.setDescription("test description");
//        entity.setCustomDescription("test custom description");
        entity.setEnabled(true);

        int insertedId = subCertificateDAO.create(entity);
        entity.setId(insertedId);
        Assert.assertEquals(entity, subCertificateDAO.get(insertedId));
        Logger.getLogger("Test SubCertificateDAO").info("Inserted sub_certificate matches our desired sub_certificate - pass");

        entity.setCustomName("de custom name");
        subCertificateDAO.update(entity);
        Assert.assertEquals(entity, subCertificateDAO.get(insertedId));
        Logger.getLogger("Test SubCertificateDAO").info("Updated sub_certificate_category.custom_name matches our desired custom_name - pass");

        subCertificateDAO.delete(insertedId);
        try {
            entity = subCertificateDAO.get(insertedId);
            Assert.fail();
        } catch (Exception e) {
            Logger.getLogger("Test SubCertificateDAO").info("Inserted sub_certificate was deleted succesfully - pass");
        }


        List<SubCertificateEntity> entities = new ArrayList<>();

        entity = new SubCertificateEntity();
        entity.setId(1);
        entity.setCertificateId(1);
        entity.setName("Machinale houtbewerking");
//        entity.setDescription("De leerling leert bekisten");
        entity.setEnabled(true);
        entities.add(entity);

        entity = new SubCertificateEntity();
        entity.setId(2);
        entity.setCertificateId(1);
        entity.setName("Manuele houtbewerking");
//        entity.setDescription("De leerling leert funderen");
        entity.setEnabled(true);
        entities.add(entity);

        entity = new SubCertificateEntity();
        entity.setId(3);
        entity.setCertificateId(1);
        entity.setName("Plaatsing buitenschrijnwerk hout");
//        entity.setDescription("De leerling leert cement");
        entity.setEnabled(true);
        entities.add(entity);

        entity = new SubCertificateEntity();
        entity.setId(4);
        entity.setCertificateId(1);
        entity.setName("Werkplaatsbuitenschrijnwerk");
//        entity.setDescription("De leerling leert cement");
        entity.setEnabled(true);
        entities.add(entity);

        Object[] converted = entities.toArray();
        Object[] arrayFromDatabase = subCertificateDAO.getAllByCertificate(1).toArray();

        Assert.assertArrayEquals(converted, arrayFromDatabase);
        Logger.getLogger("Test SubCertificateDAO").info("All sub_certificates match the database - pass");
    }


    @Autowired
    private StudentClassDAO studentClassDAO;

    @Test
    public void testStudentClassDAO() {
        // test autowire
        Assert.assertNotNull(studentClassDAO);
        Logger.getLogger("Test StudentClassDAO").info("StudentClassDAO injected succesfully - pass");

        StudentClassEntity entity = new StudentClassEntity();
        entity.setStudentInfoId(4);
        entity.setClassId(3);
        Assert.assertEquals(entity, studentClassDAO.get(4, 3));
        Logger.getLogger("Test StudentClassDAO").info("Expected student_class matches studentclass from database - pass");


        entity = new StudentClassEntity();
        entity.setStudentInfoId(3);
        entity.setClassId(2);
        studentClassDAO.create(entity);
        Assert.assertEquals(entity, studentClassDAO.get(3, 2));
        Logger.getLogger("Test StudentClassDAO").info("Inserted student_class matches our desired studenclass - pass");

        studentClassDAO.delete(3, 2);
        try {
            entity = studentClassDAO.get(3, 2);
            Assert.fail();
        } catch (Exception e) {
            Logger.getLogger("Test StudentClassDAO").info("Inserted sub_certificate_category was deleted succesfully - pass");
        }
    }

    @Autowired
    private GradeClassDAO gradeClassDAO;

    @Test
    public void testGradeClassDAO() {
        // test autowire
        Assert.assertNotNull(gradeClassDAO);
        Logger.getLogger("Test GradeClassDAO").info("GradeClassDAO injected succesfully - pass");

        GradeClassEntity entity = new GradeClassEntity();
        entity.setGradeId(2);
        entity.setClassId(6);
        Assert.assertEquals(entity, gradeClassDAO.get(2, 6));
        Logger.getLogger("Test GradeClassDAO").info("Expected grade_class matches grade_class from database - pass");


        entity = new GradeClassEntity();
        entity.setGradeId(2);
        entity.setClassId(5);
        gradeClassDAO.create(entity);
        Assert.assertEquals(entity, gradeClassDAO.get(2, 5));
        Logger.getLogger("Test GradeClassDAO").info("Inserted grade_class matches our desired grade_class - pass");

        gradeClassDAO.delete(2, 5);
        try {
            entity = gradeClassDAO.get(2, 5);
            Assert.fail();
        } catch (Exception e) {
            Logger.getLogger("Test GradeClassDAO").info("Inserted grade_class was deleted succesfully - pass");
        }
    }


    @Autowired
    private SubCertificateCategoryDAO subCertificateCategoryDAO;

    @Test
    public void testSubCertificateCategoryDAO() {
        // test autowire
        Assert.assertNotNull(subCertificateCategoryDAO);
        Logger.getLogger("Test subCertificateCategoryDAO").info("SubCertificateCategoryDAO injected succesfully - pass");


        SubCertificateCategoryEntity entity = new SubCertificateCategoryEntity();
        entity.setId(1);
        entity.setSubCertificateId(1);
        entity.setName("Afwerking");
//        entity.setDescription("Leerling kan afwerken");
        entity.setEnabled(true);
        Assert.assertEquals(entity, subCertificateCategoryDAO.get(1));
        Logger.getLogger("Test SubCertificateCategoryDAO").info("Expected sub_certificate_category with ID=1 matches subcertificatecategory from database - pass");

        entity = new SubCertificateCategoryEntity();
        entity.setId(6);
        entity.setSubCertificateId(3);
        entity.setName("Ordelijk");
//        entity.setDescription("Leerling kan ordelijk werken");
        entity.setEnabled(true);
        Assert.assertEquals(entity, subCertificateCategoryDAO.get(6));
        Logger.getLogger("Test SubCertificateCategoryDAO").info("Expected sub_certificate_category with ID=6 matches subcertificatecategory from database - pass");


        entity = new SubCertificateCategoryEntity();
        entity.setSubCertificateId(5);
        entity.setName("test name");
        entity.setCustomName("test custom name");
//        entity.setDescription("test description");
//        entity.setCustomDescription("test custom description");
        entity.setEnabled(false);

        int insertedId = subCertificateCategoryDAO.create(entity);
        entity.setId(insertedId);
        Assert.assertEquals(entity, subCertificateCategoryDAO.get(insertedId));
        Logger.getLogger("Test SubCertificateCategoryDAO").info("Inserted sub_certificate_category matches our desired subcertificatecategory - pass");

        entity.setCustomName("de custom name");
        subCertificateCategoryDAO.update(entity);
        Assert.assertEquals(entity, subCertificateCategoryDAO.get(insertedId));
        Logger.getLogger("Test SubCertificateCategoryDAO").info("Updated sub_certificate_category.custom_name matches our desired custom_name - pass");

        subCertificateCategoryDAO.delete(insertedId);
        try {
            entity = subCertificateCategoryDAO.get(insertedId);
            Assert.fail();
        } catch (Exception e) {
            Logger.getLogger("Test SubCertificateCategoryDAO").info("Inserted sub_certificate_category was deleted succesfully - pass");
        }


        List<SubCertificateCategoryEntity> entities = new ArrayList<>();

        entity = new SubCertificateCategoryEntity();
        entity.setId(1);
        entity.setSubCertificateId(1);
        entity.setName("Afwerking");
//        entity.setDescription("Leerling kan afwerken");
        entity.setEnabled(true);
        entities.add(entity);

        entity = new SubCertificateCategoryEntity();
        entity.setId(2);
        entity.setSubCertificateId(1);
        entity.setName("Ordelijk");
//        entity.setDescription("Leerling kan ordelijk werken");
        entity.setEnabled(true);
        entities.add(entity);

        Object[] converted = entities.toArray();
        Object[] arrayFromDatabase = subCertificateCategoryDAO.getAllBySubCertificate(1).toArray();

        Assert.assertArrayEquals(converted, arrayFromDatabase);
        Logger.getLogger("Test SubCertificateCategoryDAO").info("All sub_certificates_categories match the database - pass");
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

        Assert.assertEquals(studentBGVScoreDAO.get(1), entity);
        Logger.getLogger("Test StudentBGVScoreDAO").info("Expected student_bgv_score with ID=1 matches student_bgv_score from database - pass");


        entity = new StudentBGVScoreEntity();
        entity.setStudentId(1);
        entity.setCompetenceId(1);
        entity.setScore("V");
        entity.setGradedDate(Date.valueOf("2016-04-08"));
        entity.setRemarks("Remarks test");
        int insertedId = studentBGVScoreDAO.create(entity);
        entity.setId(insertedId);
        Assert.assertEquals(entity, studentBGVScoreDAO.get(insertedId));
        Logger.getLogger("Test StudentBGVScoreDAO").info("Inserted student_bgv_score matches our desired student_bgv_score - pass");


        entity.setScore("A");
        studentBGVScoreDAO.update(entity);
        Assert.assertEquals(entity, studentBGVScoreDAO.get(insertedId));
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

        Assert.assertEquals(studentPAVScoreDAO.get(1), entity);
        Logger.getLogger("Test StudentPAVScoreDAO").info("Expected student_pav_score with ID=1 matches student_pav_score from database - pass");


        entity = new StudentPAVScoreEntity();
        entity.setStudentId(1);
        entity.setObjectiveId(1);
        entity.setScore("V");
        entity.setGradedDate(Date.valueOf("2016-04-08"));
        entity.setRemarks("Remarks test");
        int insertedId = studentPAVScoreDAO.create(entity);
        entity.setId(insertedId);
        Assert.assertEquals(entity, studentPAVScoreDAO.get(insertedId));
        Logger.getLogger("Test StudentPAVScoreDAO").info("Inserted student_pav_score matches our desired student_pav_score - pass");


        entity.setScore("A");
        studentPAVScoreDAO.update(entity);
        Assert.assertEquals(entity, studentPAVScoreDAO.get(insertedId));
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
        entity.setName("2de graad");
        Assert.assertEquals(entity, gradeDAO.get(1));
        Logger.getLogger("Test GradeDAO").info("Expected grade with ID=1 matches grade from database - pass");


        entity = new GradeEntity();
        entity.setName("Test Graad");
        int insertedId = gradeDAO.create(entity);
        entity.setId(insertedId);
        Assert.assertEquals(entity, gradeDAO.get(insertedId));
        Logger.getLogger("Test GradeDAO").info("Inserted grade matches our desired grade - pass");

        entity.setName("Test Graad 2");
        gradeDAO.update(entity);
        Assert.assertEquals(entity, gradeDAO.get(insertedId));
        Logger.getLogger("Test GradeDAO").info("Updated grade matches our desired grade - pass");

        gradeDAO.delete(insertedId);
        try {
            gradeDAO.get(insertedId);
            Assert.fail();
        } catch (Exception e) {
            Logger.getLogger("Test GradeDAO").info("Deleted grade successfully.");
        }
    }


    @Autowired
    ClassCertificateDAO classCertificateDAO;

    @Test
    public void testClassCertificateDAO() {
        // test autowire
        Assert.assertNotNull(classCertificateDAO);
        Logger.getLogger("Test ClassCertificateDAO").info("ClassCertificateDAO injected succesfully - pass");

        // test get class 1 & certificate 1
        ClassCertificateEntity classCertificateEntity = new ClassCertificateEntity(1, 1);
        Assert.assertEquals(classCertificateEntity, classCertificateDAO.get(1, 1));
        Logger.getLogger("Test ClassCertificateDAO").info("Expected classCertificate with class_id=1 and certificate_id=1. Matches classCertificate from database - pass");

        // test get class 4 & certificate 2
        classCertificateEntity = new ClassCertificateEntity(4, 2);
        Assert.assertEquals(classCertificateEntity, classCertificateDAO.get(4, 2));
        Logger.getLogger("Test ClassCertificateDAO").info("Expected classCertificate with class_id=4 and certificate_id=2. Matches classCertificate from database - pass");

        // test get class 2 & certificate 4
        try {
            classCertificateEntity = classCertificateDAO.get(-1, -1);
            Assert.fail();
        } catch (Exception e) {
            Logger.getLogger("Test ClassCertificateDAO").info("Expected null. Matches classCertificate from database - pass");
        }

        // create classCertificate with class_id=2 & certificate_id=2
        classCertificateEntity = new ClassCertificateEntity(2, 2);
        classCertificateDAO.create(classCertificateEntity);

        ClassCertificateEntity insertedEntity = classCertificateDAO.get(2, 2);
        Assert.assertEquals(classCertificateEntity, insertedEntity);
        Logger.getLogger("Test ClassCertificateDAO").info("Inserted classCertificate matches desired classCertificate - pass");


        // Test getByClass
        List<ClassCertificateEntity> entities = new ArrayList<>();
        classCertificateEntity = new ClassCertificateEntity(2, 1);
        entities.add(classCertificateEntity);

        classCertificateEntity = new ClassCertificateEntity(2, 2);
        entities.add(classCertificateEntity);

//        Object[] converted = entities.toArray();
//        Object[] arrayFromDatabase = classCertificateDAO.getByClass(2);
//
//        Assert.assertArrayEquals(converted, arrayFromDatabase);
//        Logger.getLogger("Test ClassCertificateDAO").info("All classCertificates with class_id=2 match the database - pass");
//
//
//        // Test getByCertificate
//        entities = new ArrayList<>();
//        classCertificateEntity = new ClassCertificateEntity(2,2);
//        entities.add(classCertificateEntity);
//
//        classCertificateEntity = new ClassCertificateEntity(3,2);
//        entities.add(classCertificateEntity);
//
//        classCertificateEntity = new ClassCertificateEntity(4,2);
//        entities.add(classCertificateEntity);
//
//        converted = entities.toArray();
//        arrayFromDatabase = classCertificateDAO.getByCertificate(2).toArray();
//
//        Assert.assertArrayEquals(converted, arrayFromDatabase);
//        Logger.getLogger("Test ClassCertificateDAO").info("All classCertificates with certificate_id=2 match the database - pass");
//
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
    private CompetenceDAO competenceDAO;

    @Test
    public void testCompetencesDAO() {

        // test autowire
        Assert.assertNotNull(competenceDAO);
        Logger.getLogger("Test CompetenceDAO").info("CompetenceDAO injected succesfully - pass");

        // test get competence 1
        CompetenceEntity competence = new CompetenceEntity();
        competence.setId(1);
        competence.setSubCertificateCategoryId(2);
        competence.setName("Gereedschap opruimen");
//        competence.setDescription("Leerling ruimt gereedschap op");
        competence.setEnabled(true);
        Assert.assertEquals(competence, competenceDAO.get(1));
        Logger.getLogger("Test CompetenceDAO").info("Expected competence with ID=1 matches competence from database - pass");

        // test get competence 7
        competence = new CompetenceEntity();
        competence.setId(7);
        competence.setSubCertificateCategoryId(3);
        competence.setName("Afwerk Competentie");
//        competence.setDescription("Leerling ruimt werkt volledig af");
        competence.setEnabled(true);
        Assert.assertEquals(competence, competenceDAO.get(7));
        Logger.getLogger("Test CompetenceDAO").info("Expected competence with ID=7 matches competence from database - pass");

        //test getBySubCertificateCategory
        List<CompetenceEntity> entities = new ArrayList<>();
        entities.add(competence);

        Object[] converted = entities.toArray();
        Object[] arrayFromDatabase = competenceDAO.getBySubCertificateCategory(3).toArray();

        Assert.assertArrayEquals(converted, arrayFromDatabase);

        // create competence
        competence = new CompetenceEntity();
        competence.setSubCertificateCategoryId(1);
        competence.setName("Test Competentie");
//        competence.setDescription("Leerling test");
        competence.setEnabled(true);

        int insertedId = competenceDAO.create(competence);
        competence.setId(insertedId);
        CompetenceEntity insertedEntity = competenceDAO.get(insertedId);

        Assert.assertEquals(competence, insertedEntity);
        Logger.getLogger("Test CompetenceDAO").info("Inserted competence matches our desired competence - pass");

        //Test update
        competence.setName("Update Test");
        competenceDAO.update(competence);
        CompetenceEntity updatedEnity = competenceDAO.get(competence.getId());
        Assert.assertEquals(competence, updatedEnity);
        Logger.getLogger("Test CompetenceDAO").info("Updated competence matches our desired competence - pass");

        //Test delete
        competenceDAO.delete(insertedId);
        try {
            insertedEntity = competenceDAO.get(insertedId);
            Assert.fail();
        } catch (Exception e) {
            Logger.getLogger("Test CompetenceDAO").info("Inserted competence was deleted succesfully - pass");
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
        userEntity.setPassword("pwd");
        userEntity.setFirstName("Hilde");
        userEntity.setLastName("Beerten");
        userEntity.setType("STUDENT");
        Assert.assertEquals(userEntity, userDAO.get(11));
        Logger.getLogger("Test UserDAO").info("Expected user with ID=11 matches user from database - pass");

        // test get user 12
        userEntity = new UserEntity();
        userEntity.setId(12);
        userEntity.setEmail("leerling2@school.be");
        userEntity.setPassword("pwd");
        userEntity.setFirstName("Katrien");
        userEntity.setLastName("Formesyn");
        userEntity.setType("STUDENT");
        Assert.assertEquals(userEntity, userDAO.get(12));
        Logger.getLogger("Test UserDAO").info("Expected user with ID=12 matches user from database - pass");

        // create user
        userEntity = new UserEntity();
        userEntity.setEmail("test@unit.com");
        userEntity.setPassword("test");
        userEntity.setFirstName("TestVoornaam");
        userEntity.setLastName("TestAchternaam");
        userEntity.setType("STUDENT");

        int insertedId = userDAO.create(userEntity);
        userEntity.setId(insertedId);
        UserEntity insertedEntity = userDAO.get(insertedId);

        Assert.assertEquals(userEntity, insertedEntity);
        Logger.getLogger("Test UserDAO").info("Inserted user matches our desired user - pass");


        userEntity.setEmail("changeder@email.com");
        userDAO.update(userEntity);
        Assert.assertEquals(userEntity, userDAO.get(insertedId));
        Logger.getLogger("Test UserDAO").info("Updated user matches our desired user - pass");

        userDAO.delete(insertedId);
        try {
            insertedEntity = userDAO.get(insertedId);
            Assert.fail();
        } catch (Exception e) {
            Logger.getLogger("Test UserDAO").info("Inserted user was deleted succesfully - pass");
        }
    }


    @Autowired
    private CourseTopicDAO courseTopicDAO;

    @Test
    public void testCourseTopicDAO() {
        // test autowire
        Assert.assertNotNull(courseTopicDAO);
        Logger.getLogger("Test CourseTopicDAO").info("CourseTopicDAO injected succesfully - pass");

        // test get course topic 1
        CourseTopicEntity entity = new CourseTopicEntity();
        entity.setId(1);
        entity.setName("Vakthema 1");
        Assert.assertEquals(entity, courseTopicDAO.get(1));
        Logger.getLogger("Test CourseTopicDAO").info("Expected course topic with ID=1 matches course topic from database - pass");

        // test get course topic 2
        entity = new CourseTopicEntity();
        entity.setId(2);
        entity.setName("Vakthema 2");
        Assert.assertEquals(entity, courseTopicDAO.get(2));
        Logger.getLogger("Test CourseTopicDAO").info("Expected course topic with ID=2 matches course topic from database - pass");

        // create course topic
        entity = new CourseTopicEntity();
        entity.setName("vakthema 30");

        int insertedId = courseTopicDAO.create(entity);
        entity.setId(insertedId);
        CourseTopicEntity insertedEntity = courseTopicDAO.get(insertedId);

        Assert.assertEquals(entity, insertedEntity);
        Logger.getLogger("Test CourseTopicDAO").info("Inserted course topic matches our desired course topic - pass");

        //Test update
        entity.setName("Update Test");
        courseTopicDAO.update(entity);
        CourseTopicEntity updatedEnity = courseTopicDAO.get(entity.getId());
        Assert.assertEquals(entity, updatedEnity);
        Logger.getLogger("Test CompetenceDAO").info("Updated competence matches our desired course topic - pass");

        courseTopicDAO.delete(insertedId);
        try {
            insertedEntity = courseTopicDAO.get(insertedId);
            Assert.fail();
        } catch (Exception e) {
            Logger.getLogger("Test CourseTopicDAO").info("Inserted course topic was deleted succesfully - pass");
        }
    }

    @Autowired
    private ObjectiveDAO objectiveDAO;

    @Test
    public void testObjectiveDAO() {
        // test autowire
        Assert.assertNotNull(objectiveDAO);
        Logger.getLogger("Test ObjectiveDAO").info("ObjectiveDAO injected succesfully - pass");

        // test get Objective 1
        ObjectiveEntity entity = new ObjectiveEntity();
        entity.setId(1);
        entity.setGradeId(1);
        entity.setCourseTopicId(1);
        entity.setName("Kent Vakthema 1");
        Assert.assertEquals(entity, objectiveDAO.get(1));
        Logger.getLogger("Test ObjectiveDAO").info("Expected objective with ID=1 matches objective from database - pass");

        // test get Objective 2
        entity = new ObjectiveEntity();
        entity.setId(2);
        entity.setGradeId(2);
        entity.setCourseTopicId(1);
        entity.setName("Kent Vakthema 2");
        Assert.assertEquals(entity, objectiveDAO.get(2));
        Logger.getLogger("Test ObjectiveDAO").info("Expected objective with ID=2 matches objective from database - pass");

        //getByGradeId
        List<ObjectiveEntity> entities = new ArrayList<>();
        entities.add(entity);

        Object[] converted = entities.toArray();
        Object[] arrayFromDatabase = objectiveDAO.getByGradeId(2).toArray();
        Assert.assertArrayEquals(converted, arrayFromDatabase);
        Logger.getLogger("Test ObjectivesDAO").info("Expected ObjectivesList matches ObjectivesList from database - pass");

        //getByCourseTopicId
        entities = new ArrayList<>();
        entity = new ObjectiveEntity();
        entity.setId(1);
        entity.setGradeId(1);
        entity.setCourseTopicId(1);
        entity.setName("Kent Vakthema 1");
        entities.add(entity);

        entity = new ObjectiveEntity();
        entity.setId(2);
        entity.setGradeId(2);
        entity.setCourseTopicId(1);
        entity.setName("Kent Vakthema 2");
        entities.add(entity);

        converted = entities.toArray();
        arrayFromDatabase = objectiveDAO.getByCourseTopicId(1).toArray();
        Assert.assertArrayEquals(converted, arrayFromDatabase);
        Logger.getLogger("Test ObjectivesDAO").info("Expected ObjectivesList matches ObjectivesList from database - pass");

        // create course topic
        entity = new ObjectiveEntity();
        entity.setGradeId(1);
        entity.setCourseTopicId(2);
        entity.setName("wiskunde");

        int insertedId = objectiveDAO.create(entity);
        entity.setId(insertedId);
        ObjectiveEntity insertedEntity = objectiveDAO.get(insertedId);

        Assert.assertEquals(entity, insertedEntity);
        Logger.getLogger("Test ObjectiveDAO").info("Inserted Objective matches our desired Objective - pass");

        //Test update
        entity.setName("Update Test");
        entity.setGradeId(2);
        entity.setCourseTopicId(1);
        entity.setCustomName("taal");
        objectiveDAO.update(entity);
        ObjectiveEntity updatedEnity = objectiveDAO.get(entity.getId());
        Assert.assertEquals(entity, updatedEnity);
        Logger.getLogger("Test ObjectiveDAO").info("Updated Objective matches our desired Objective - pass");

        objectiveDAO.delete(insertedId);
        try {
            insertedEntity = objectiveDAO.get(insertedId);
            Assert.fail();
        } catch (Exception e) {
            Logger.getLogger("Test ObjectiveDAO").info("Inserted Objective was deleted succesfully - pass");
        }
    }


    @Autowired
    private ClassTopicDAO classTopicDAO;

    @Test
    public void testClassTopicsDAO() {

        // test autowire
        Assert.assertNotNull(classTopicDAO);
        Logger.getLogger("Test ClassTopicDAO").info("ClassTopicDAO injected succesfully - pass");

        // test get classTopic 1
        ClassTopicEntity classTopic = new ClassTopicEntity();
        classTopic.setCourseTopicId(1);
        classTopic.setClassId(1);
        Assert.assertEquals(classTopic, classTopicDAO.get(1, 1));
        Logger.getLogger("Test ClassTopicDAO").info("Expected classTopic with ID=1 matches classTopic from database - pass");

        // create classTopic
        classTopic = new ClassTopicEntity();
        classTopic.setCourseTopicId(2);
        classTopic.setClassId(2);

        classTopicDAO.create(classTopic);
        ClassTopicEntity insertedEntity = classTopicDAO.get(2, 2);

        Assert.assertEquals(classTopic, insertedEntity);
        Logger.getLogger("Test ClassTopicDAO").info("Inserted classTopic matches our desired classTopic - pass");

        //Test delete
        classTopicDAO.delete(2, 2);
        try {
            insertedEntity = classTopicDAO.get(2, 2);
            Assert.fail();
        } catch (Exception e) {
            Logger.getLogger("Test ClassTopicDAO").info("Inserted classTopic was deleted succesfully - pass");
        }

    }


    @Autowired
    private StudentInfoDAO studentInfoDAO;

    @Test
    public void testStudentInfoDAO() {

        // test autowire
        Assert.assertNotNull(studentInfoDAO);
        Logger.getLogger("Test StudentInfoDAO").info("StudentInfoDAO injected succesfully - pass");

        // test get studentInfo 1
        StudentInfoEntity entity = new StudentInfoEntity();
        entity.setId(1);
        entity.setUser(11);
        entity.setParent(32);
        entity.setGradeId(1);
        entity.setCertificateId(1);
        entity.setBirthDate(Date.valueOf("2012-01-01"));
        entity.setBirthPlace("Hasselt");
        entity.setNationality("Belgium");
        entity.setNationalId("12345678900");
        entity.setStreet("Straat");
        entity.setHouseNumber("10");
        entity.setPostalCode("3000");
        entity.setCity("Hasselt");
        entity.setPhoneParent("012857496");
        entity.setPhoneCell("085479621");
        entity.setBankAccount("BE67-500-555-9685");

        Assert.assertEquals(entity, studentInfoDAO.get(1));
        Logger.getLogger("Test StudentInfoDAO").info("Expected StudentInfo with ID=1 matches StudentInfo from database - pass");

        // test get studentInfo 2
        entity = new StudentInfoEntity();
        entity.setId(2);
        entity.setUser(12);
        entity.setParent(31);
        entity.setGradeId(1);
        entity.setCertificateId(1);
        entity.setBirthDate(Date.valueOf("2012-01-01"));
        entity.setBirthPlace("Hasselt");
        entity.setNationality("Belgium");
        entity.setNationalId("12345678900");
        entity.setStreet("Straat");
        entity.setHouseNumber("10");
        entity.setPostalCode("3000");
        entity.setCity("Hasselt");
        entity.setPhoneParent("012857496");
        entity.setPhoneCell("085479621");
        entity.setBankAccount("BE67-500-555-9685");

        Assert.assertEquals(entity, studentInfoDAO.get(2));
        Logger.getLogger("Test StudentInfoDAO").info("Expected StudentInfo with ID=2 matches StudentInfo from database - pass");

        //getByUserId
        Assert.assertEquals(entity, studentInfoDAO.getByUserId(12));
        Logger.getLogger("Test StudentInfoDAO").info("Expected StudentInfo by userId matches StudentInfo from database - pass");

        //getByParentId
        List<StudentInfoEntity> entities = new ArrayList<>();

        entity = new StudentInfoEntity();
        entity.setId(1);
        entity.setUser(11);
        entity.setParent(32);
        entity.setGradeId(1);
        entity.setCertificateId(1);
        entity.setBirthDate(Date.valueOf("2012-01-01"));
        entity.setBirthPlace("Hasselt");
        entity.setNationality("Belgium");
        entity.setNationalId("12345678900");
        entity.setStreet("Straat");
        entity.setHouseNumber("10");
        entity.setPostalCode("3000");
        entity.setCity("Hasselt");
        entity.setPhoneParent("012857496");
        entity.setPhoneCell("085479621");
        entity.setBankAccount("BE67-500-555-9685");
        entities.add(entity);

        entity = new StudentInfoEntity();
        entity.setId(4);
        entity.setUser(14);
        entity.setParent(32);
        entity.setGradeId(1);
        entity.setCertificateId(2);
        entity.setBirthDate(Date.valueOf("2012-01-01"));
        entity.setBirthPlace("Hasselt");
        entity.setNationality("Belgium");
        entity.setNationalId("12345678900");
        entity.setStreet("Straat");
        entity.setHouseNumber("10");
        entity.setPostalCode("3000");
        entity.setCity("Hasselt");
        entity.setPhoneParent("012857496");
        entity.setPhoneCell("085479621");
        entity.setBankAccount("BE67-500-555-9685");
        entities.add(entity);


        Object[] converted = entities.toArray();
        Object[] arrayFromDatabase = studentInfoDAO.getByParentId(32).toArray();
        Assert.assertArrayEquals(converted, arrayFromDatabase);
        Logger.getLogger("Test StudentInfoDAO").info("Expected StudentInfoList by parentId matches StudentInfoList from database - pass");


        // create course topic
        entity = new StudentInfoEntity();
        entity.setId(20);
        entity.setUser(41);
        entity.setParent(31);
        entity.setGradeId(1);
        entity.setCertificateId(1);
        entity.setBirthDate(Date.valueOf("2012-01-01"));
        entity.setBirthPlace("Hasselt");
        entity.setNationality("Belgium");
        entity.setNationalId("12345678900");
        entity.setStreet("Straat");
        entity.setHouseNumber("10");
        entity.setPostalCode("3000");
        entity.setCity("Hasselt");
        entity.setPhoneParent("012857496");
        entity.setPhoneCell("085479621");
        entity.setBankAccount("BE67-500-555-9685");


        int insertedId = studentInfoDAO.create(entity);
        entity.setId(insertedId);
        StudentInfoEntity insertedEntity = studentInfoDAO.get(insertedId);

        Assert.assertEquals(entity, insertedEntity);
        Logger.getLogger("Test StudentInfoDAO").info("Inserted StudentInfo matches our desired StudentInfo - pass");

        //Test update
        entity.setBirthDate(Date.valueOf("2012-01-01"));
        entity.setBirthPlace("Hasselt");
        entity.setNationality("Belgium");
        entity.setNationalId("12345678900");
        entity.setStreet("stapelstraat");
        entity.setHouseNumber("20");
        entity.setPostalCode("3800");
        entity.setCity("Sint-Truiden");
        entity.setPhoneParent("55255555");
        entity.setPhoneCell("085475621");
        entity.setBankAccount("BE67-500-555-9225");

        studentInfoDAO.update(entity);
        StudentInfoEntity updatedEnity = studentInfoDAO.get(entity.getId());
        Assert.assertEquals(entity, updatedEnity);
        Logger.getLogger("Test StudentInfoDAO").info("Updated StudentInfo matches our desired StudentInfo - pass");

        studentInfoDAO.delete(insertedId);
        try {
            insertedEntity = studentInfoDAO.get(insertedId);
            Assert.fail();
        } catch (Exception e) {
            Logger.getLogger("Test StudentInfoDAO").info("Inserted StudentInfo was deleted succesfully - pass");
        }
    }
}
