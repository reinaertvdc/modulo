package be.lambdaware.controllers;

import be.lambdaware.dao.UserDAO;
import be.lambdaware.model.AccountModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.ws.rs.Path;
import java.util.ArrayList;

/**
 * Created by MichielVM on 18/04/2016.
 */
@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private UserDAO userDAO;


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
    @RequestMapping(value = "/{accountEmail}/", method = RequestMethod.GET)
    public ResponseEntity<AccountModel> getByEmail(@PathVariable String accountEmail) {
        AccountModel accountModel = new AccountModel(userDAO);
        System.out.println(accountEmail);
        accountModel.getFromDBByEmail(accountEmail);
        System.out.println(accountModel);
        return new ResponseEntity<AccountModel>(accountModel, HttpStatus.OK);
    }
}