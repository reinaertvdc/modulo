package be.lambdaware.controllers;

import be.lambdaware.dao.CertificatesDAO;
import be.lambdaware.entities.CertificatesEntity;
import be.lambdaware.model.AccountModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Vincent on 12/04/16.
 */
@RestController
@RequestMapping("/accountmodel")
public class AccountModelTestController {

    @Autowired
    private ApplicationContext context;

    @CrossOrigin
    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<AccountModel> create(@RequestBody AccountModel accountModel) {

        //TODO process when dao.create fails with SQL Exception

//        accountModel.createInDB();

        System.out.println(accountModel);


        // return model
        return new ResponseEntity<AccountModel>(accountModel, HttpStatus.OK);
    }

    @CrossOrigin
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<AccountModel> get(@RequestParam(value="id") Integer userId ) {

        System.out.println(userId);

        AccountModel accountModel = new AccountModel();

        accountModel.getFromDB(userId);

        System.out.println(accountModel);

        return new ResponseEntity<AccountModel>(accountModel, HttpStatus.OK);
    }
}
