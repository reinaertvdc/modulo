package be.lambdaware;

import be.lambdaware.application.ModuloBackendApplication;
import be.lambdaware.dao.*;
import be.lambdaware.entities.*;
import be.lambdaware.model.AccountModel;
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

    @Test
    public void StudentModelTest() {

    }

}

