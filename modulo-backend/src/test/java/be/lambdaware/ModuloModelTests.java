package be.lambdaware;

import be.lambdaware.application.ModuloBackendApplication;
import be.lambdaware.dao.*;
import be.lambdaware.entities.*;
import be.lambdaware.model.AccountModel;
import be.lambdaware.model.StudentModel;
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

/**
 * Created by Vincent on 12/04/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ModuloBackendApplication.class)
@WebAppConfiguration
public class ModuloModelTests {

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private StudentInfoDAO studentInfoDAO;

    @Test
    public void StudentModelTest() {
        // Get student with userId = 11
        StudentModel studentModel1 = new StudentModel(userDAO, studentInfoDAO);
        studentModel1.getFromDB(11);

        StudentModel studentModel2 = new StudentModel(userDAO, studentInfoDAO);
        studentModel2.setUserEntity(userDAO.get(11));
        studentModel2.setStudentInfoEntity(studentInfoDAO.get(1));

        Assert.assertEquals(studentModel1, studentModel2);
        Logger.getLogger("Test StudentModel").info("Expected studentModel with studentID = 1 and userID=11. Matches studentModel from database - pass");

        // Get student with userId = 12
        studentModel1 = new StudentModel(userDAO, studentInfoDAO);
        studentModel1.getFromDB(12);

        studentModel2 = new StudentModel(userDAO, studentInfoDAO);
        studentModel2.setUserEntity(userDAO.get(12));
        studentModel2.setStudentInfoEntity(studentInfoDAO.get(2));

        Assert.assertEquals(studentModel1, studentModel2);
        Logger.getLogger("Test StudentModel").info("Expected studentModel with studentID = 2 and userID=12. Matches studentModel from database - pass");


        // Create student
        studentModel1 = new StudentModel(userDAO, studentInfoDAO);
        UserEntity userEntity = userDAO.get(15);
        userEntity.setEmail("leerling6@school.be");
        StudentInfoEntity studentInfoEntity = studentInfoDAO.get(5);
//        studentInfoEntity.setId(6);

        studentModel1.setUserEntity(userEntity);
        studentModel1.setStudentInfoEntity(studentInfoEntity);
        studentModel1.createInDB();

        studentModel2.getFromDB(studentModel1.getUserEntity().getId());
        Assert.assertEquals(studentModel1, studentModel2);
        Logger.getLogger("Test StudentModel").info("Created StudentModel. Matches studentModel from database - pass");

        // Update student
        studentModel1.getUserEntity().setEmail("leerling7@school.be");
        studentModel1.updateInDB();

        studentModel2.getFromDB(studentModel1.getUserEntity().getId());
        Assert.assertEquals(studentModel1, studentModel2);
        Logger.getLogger("Test StudentModel").info("Updated StudentModel. Matches studentModel from database - pass");


        // Delete student
        Integer id = studentModel1.getUserEntity().getId();
        studentModel1.deleteFromDB();
        try {
            studentModel1.getFromDB(id);
            Assert.fail();
        } catch (Exception e) {
            Logger.getLogger("Test StudentModel").info("Deleted StudentModel. Matches studentModel from database - pass");
        }
    }



    public void TeacherModelTest() {
        // TODO test TeacherModel
    }
}

