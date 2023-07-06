package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.TransferDao;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("/transfer")
public class TransferController {
    private TransferDao transferDao;
    
    public TransferController() {
    }
    public TransferController (TransferDao transferDao){
        this.transferDao = transferDao;
    }


    @GetMapping("/balance/{username}")
    public double getBalance (@PathVariable String username){
        double balance = transferDao.getBalance(username);
        return balance;
    }


}
