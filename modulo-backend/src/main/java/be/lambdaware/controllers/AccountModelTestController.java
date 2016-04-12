package be.lambdaware.controllers;

import be.lambdaware.dao.UserDAO;
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
    private UserDAO userDAO;


    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<AccountModel> create(@RequestBody AccountModel am) {
        am.setUserDAO(userDAO);
        am.createInDB();
        return new ResponseEntity<AccountModel>(am, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<AccountModel> get(@RequestParam(value="id") Integer userId ) {
        AccountModel accountModel = new AccountModel(userDAO);
        accountModel.getFromDB(userId);
        return new ResponseEntity<AccountModel>(accountModel, HttpStatus.OK);
    }
}
