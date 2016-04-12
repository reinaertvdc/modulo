package be.lambdaware.controllers;

import be.lambdaware.model.AccountModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Created by Vincent on 12/04/16.
 */
@RestController
@RequestMapping("/accountmodel")
public class AccountModelTestController {

    @Autowired
    private ApplicationContext context;

    @Autowired
    private AccountModel accountModel;

//    @Autowired
//    private UserDAO userDAO;

    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<AccountModel> create(@RequestBody AccountModel am) {

//        am.setUserDAO(accountModel.getUserDAO());
//
//        //TODO process when dao.create fails with SQL Exception
//        System.out.println("controller: " + am);
//        am.createInDB();
//        System.out.println("controller: " + am);
//
//        // return model
//        return new ResponseEntity<AccountModel>(am, HttpStatus.OK);
        return null;
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<AccountModel> get(@RequestParam(value="id") Integer userId ) {
        accountModel.getFromDB(userId);
        return new ResponseEntity<AccountModel>(accountModel, HttpStatus.OK);
    }
}
