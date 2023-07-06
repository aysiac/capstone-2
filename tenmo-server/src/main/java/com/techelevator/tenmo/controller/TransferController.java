package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.AccountBalanceDTO;
import com.techelevator.tenmo.model.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/transfer")
public class TransferController {
    private TransferDao transferDao;

    public TransferController (TransferDao transferDao){
        this.transferDao = transferDao;
    }

    @GetMapping("/balance/{username}")
    public double getBalance (@PathVariable String username){
        double balance = transferDao.getBalance(username);
        return balance;
    }
    @GetMapping("/{username}")
    public List<User> getListOfUsers(@PathVariable String username){
        List<User> userList = transferDao.getListOfUsers(username)  ;
        return userList;
    }
}
