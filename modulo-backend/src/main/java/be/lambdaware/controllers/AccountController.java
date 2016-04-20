package be.lambdaware.controllers;

import be.lambdaware.dao.StudentInfoDAO;
import be.lambdaware.dao.UserDAO;
import be.lambdaware.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

/**
 * Created by MichielVM on 18/04/2016.
 */
@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private UserDAO userDAO;
    @Autowired
    private StudentInfoDAO studentInfoDAO;


    @CrossOrigin
    @RequestMapping(value = "/all", method = RequestMethod.GET)
    public ResponseEntity<ArrayList<AccountModel>> getAll() {
        ArrayList<AccountModel> accounts = AccountModel.getAll(userDAO);
        for(AccountModel account : accounts)
            account.getUserEntity().setPassword(null);
        return new ResponseEntity<ArrayList<AccountModel>>(accounts, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/{accountId}", method = RequestMethod.DELETE)
    public boolean delete(@PathVariable Integer accountId ) {
        AccountModel accountModel = new AccountModel(userDAO);
        accountModel.getFromDB(accountId);
        accountModel.deleteFromDB();
        return true;
    }

    @CrossOrigin
    @RequestMapping(value = "/admin", method = RequestMethod.POST)
    public ResponseEntity<AdminModel> createAdmin(@RequestBody AdminModel admin) {
        admin.setUserDAO(userDAO);
        admin.createInDB();
        return new ResponseEntity<AdminModel>(admin, HttpStatus.OK);
    }
    @CrossOrigin
    @RequestMapping(value = "/parent", method = RequestMethod.POST)
    public ResponseEntity<ParentModel> createParent(@RequestBody ParentModel parent) {
        parent.setUserDAO(userDAO);
        parent.createInDB();
        return new ResponseEntity<ParentModel>(parent, HttpStatus.OK);
    }
    @CrossOrigin
    @RequestMapping(value = "/teacher", method = RequestMethod.POST)
    public ResponseEntity<TeacherModel> createTeacher(@RequestBody TeacherModel teacher) {
        teacher.setUserDAO(userDAO);
        teacher.createInDB();
        return new ResponseEntity<TeacherModel>(teacher, HttpStatus.OK);
    }
    @CrossOrigin
    @RequestMapping(value = "/student", method = RequestMethod.POST)
    public ResponseEntity<StudentModel> createStudent(@RequestBody StudentModel student) {
        student.setUserDAO(userDAO);
        student.setStudentInfoDAO(studentInfoDAO);
        student.createInDB();
        return new ResponseEntity<StudentModel>(student, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/{accountEmail}/", method = RequestMethod.GET)
    public ResponseEntity<AccountModel> getByEmail(@PathVariable String accountEmail) {
        AccountModel accountModel = new AccountModel(userDAO);
        accountModel.getFromDBByEmail(accountEmail);
        return new ResponseEntity<AccountModel>(accountModel, HttpStatus.OK);
    }
}