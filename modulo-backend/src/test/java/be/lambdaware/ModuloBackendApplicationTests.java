package be.lambdaware;


import be.lambdaware.application.ModuloBackendApplication;
import be.lambdaware.dao.UserDAO;
import be.lambdaware.entities.UserEntity;
import org.apache.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

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
        UserEntity userEntity= new UserEntity();
        userEntity.setId(11);
        userEntity.setEmail("leerling1@school.be");
        userEntity.setPassword("b109f3bbbc244eb82441917ed06d618b9008dd09b3befd1b5e07394c706a8bb980b1d7785e5976ec049b46df5f1326af5a2ea6d103fd07c95385ffab0cacbc86");
        userEntity.setType("STUDENT");
        Assert.assertEquals(userEntity,userDAO.get(11));
        Logger.getLogger("Test UserDAO").info("Expected user with ID=11 matches user from database - pass");

        // test get user 12
        userEntity= new UserEntity();
        userEntity.setId(12);
        userEntity.setEmail("leerling2@school.be");
        userEntity.setPassword("b109f3bbbc244eb82441917ed06d618b9008dd09b3befd1b5e07394c706a8bb980b1d7785e5976ec049b46df5f1326af5a2ea6d103fd07c95385ffab0cacbc86");
        userEntity.setType("STUDENT");
        Assert.assertEquals(userEntity,userDAO.get(12));
        Logger.getLogger("Test UserDAO").info("Expected user with ID=11 matches user from database - pass");

        // create user
        userEntity= new UserEntity();
        userEntity.setEmail("test@unit.com");
        userEntity.setPassword("test");
        userEntity.setType("STUDENT");

        int insertedId = userDAO.create(userEntity);
        userEntity.setId(insertedId);
        UserEntity insertedEntity = userDAO.get(insertedId);

        Assert.assertEquals(userEntity,insertedEntity);
        Logger.getLogger("Test UserDAO").info("Inserted user matches our desired user - pass");

        userDAO.delete(insertedId);
        try {
            insertedEntity = userDAO.get(insertedId);
            Assert.fail();
        }
        catch (Exception e) {
            Logger.getLogger("Test UserDAO").info("Inserted user was deleted succesfully - pass");
        }
    }

}
