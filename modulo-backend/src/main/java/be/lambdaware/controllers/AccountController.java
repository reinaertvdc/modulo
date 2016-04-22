package be.lambdaware.controllers;

import be.lambdaware.dao.StudentInfoDAO;
import be.lambdaware.dao.UserDAO;
import be.lambdaware.entities.StudentInfoEntity;
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
    @RequestMapping(value = "/parents", method = RequestMethod.GET)
    public ResponseEntity<ArrayList<ParentModel>> getParents() {
        ArrayList<AccountModel> accounts = AccountModel.getAll(userDAO);
        ArrayList<ParentModel> parents = new ArrayList<ParentModel>();
        for(AccountModel account : accounts)
            if(account.getUserEntity().getType().equals("PARENT")) {
                ParentModel parent = new ParentModel(userDAO);
                parent.getFromDB(account.getUserEntity().getId());
                parents.add(parent);
            }
        return new ResponseEntity<ArrayList<ParentModel>>(parents, HttpStatus.OK);
    }


    @CrossOrigin
    @RequestMapping(value = "/{userId}", method = RequestMethod.GET)
    public ResponseEntity<AccountModel> get(@PathVariable Integer userId) {
        AccountModel account = new AccountModel(userDAO);
        account.getFromDB(userId);
        return new ResponseEntity<AccountModel>(account, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/student/{userId}", method = RequestMethod.GET)
    public ResponseEntity<StudentModel> getStudent(@PathVariable Integer userId) {
        StudentModel student = new StudentModel(userDAO, studentInfoDAO);
        student.getFromDB(userId);
        return new ResponseEntity<StudentModel>(student, HttpStatus.OK);
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
    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<AccountModel> update(@RequestBody AccountModel accountModel) {
        AccountModel old = new AccountModel(userDAO);
        old.getFromDB(accountModel.getUserEntity().getId());
        accountModel.setUserDAO(userDAO);
        accountModel.getUserEntity().setPassword( old.getUserEntity().getPassword());
        accountModel.updateInDB();
        return new ResponseEntity<AccountModel>(accountModel, HttpStatus.OK);
    }
    @CrossOrigin
    @RequestMapping(value = "/admin", method = RequestMethod.PUT)
    public ResponseEntity<AdminModel> updateAdmin(@RequestBody AdminModel admin) {
        // remove studentinfoentity if it was a student
        int userId = admin.getUserEntity().getId();
        AccountModel accountModel = new AccountModel(userDAO);
        accountModel.getFromDB(userId);
        if(accountModel.getUserEntity().getType().equals("STUDENT")) {
            StudentInfoEntity info = studentInfoDAO.getByUserId(userId);
            studentInfoDAO.delete(info.getId());
        }
        // update user
        admin.setUserDAO(userDAO);
        admin.updateInDB();
        return new ResponseEntity<AdminModel>(admin, HttpStatus.OK);
    }
    @CrossOrigin
    @RequestMapping(value = "/parent", method = RequestMethod.PUT)
    public ResponseEntity<ParentModel> updateParent(@RequestBody ParentModel parent) {
        // remove studentinfoentity if it was a student
        int userId = parent.getUserEntity().getId();
        AccountModel accountModel = new AccountModel(userDAO);
        accountModel.getFromDB(userId);
        if(accountModel.getUserEntity().getType().equals("STUDENT")) {
            StudentInfoEntity info = studentInfoDAO.getByUserId(userId);
            studentInfoDAO.delete(info.getId());
        }
        // update user
        parent.setUserDAO(userDAO);
        parent.updateInDB();
        return new ResponseEntity<ParentModel>(parent, HttpStatus.OK);
    }
    @CrossOrigin
    @RequestMapping(value = "/teacher", method = RequestMethod.PUT)
    public ResponseEntity<TeacherModel> updateTeacher(@RequestBody TeacherModel teacher) {
        // remove studentinfoentity if it was a student
        int userId = teacher.getUserEntity().getId();
        AccountModel accountModel = new AccountModel(userDAO);
        accountModel.getFromDB(userId);
        if(accountModel.getUserEntity().getType().equals("STUDENT")) {
            StudentInfoEntity info = studentInfoDAO.getByUserId(userId);
            studentInfoDAO.delete(info.getId());
        }
        // update user
        teacher.setUserDAO(userDAO);
        teacher.updateInDB();
        return new ResponseEntity<TeacherModel>(teacher, HttpStatus.OK);
    }
    @CrossOrigin
    @RequestMapping(value = "/student", method = RequestMethod.PUT)
    public ResponseEntity<StudentModel> updateStudent(@RequestBody StudentModel student) {
        student.getStudentInfoEntity().setUser(student.getStudentInfoEntity().getId());
        student.setUserDAO(userDAO);
        student.setStudentInfoDAO(studentInfoDAO);
        student.deleteFromDB();
        student.createInDB();

        //TODO mapping between student and others... Update?
        return new ResponseEntity<StudentModel>(student, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(value = "/email/{accountEmail}/", method = RequestMethod.GET)
    public ResponseEntity<AccountModel> getByEmail(@PathVariable String accountEmail) {
        AccountModel accountModel = new AccountModel(userDAO);
        accountModel.getFromDBByEmail(accountEmail);
        return new ResponseEntity<AccountModel>(accountModel, HttpStatus.OK);
    }
}