package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.model.*;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/transfer")
public class TransferController {
    private TransferDao transferDao;
    private AccountDao accountDao;

    public TransferController(TransferDao transferDao, AccountDao accountDao) {
        this.transferDao = transferDao;
        this.accountDao = accountDao;
    }

    @GetMapping("/balance/{username}")
    public AccountBalanceDTO getBalance(@PathVariable String username) {
        AccountBalanceDTO balance = transferDao.getBalance(username);
        return balance;
    }

    @GetMapping("/{username}")
    public List<User> getListOfUsers(@PathVariable String username) {
        List<User> userList = transferDao.getListOfUsers(username);
        return userList;
    }

    @PostMapping("/start")
    public void makeTransfer(@RequestBody TransferDTO transferDTO) {
        boolean isTransferSuccess;
        Transfer newTransfer = new Transfer() ;
        Account fromAccount = accountDao.getAccountByUserName(transferDTO.getFromUserName());
        Account toAccount = accountDao.getAccountByUserName(transferDTO.getToUserName());
        if (transferDTO.getAmount() < fromAccount.getBalance()) {
            transferDao.makeTransfer(fromAccount.getAccountId(), toAccount.getAccountId(), transferDTO.getAmount());

            isTransferSuccess = true;

        } else {
            isTransferSuccess = false;
        }
        if(isTransferSuccess){
            int statusId =  transferDao.getStatusByName("Approved");
            int transferTypeId = transferDao.getTransferType("Sent");
            newTransfer.setFromAccount(fromAccount.getAccountId());
            newTransfer.setToAccount(toAccount.getAccountId());
            newTransfer.setTransferAmount(transferDTO.getAmount());
            newTransfer.setTransferStatusId(statusId);
            newTransfer.setTransferTypeId(transferTypeId);
            transferDao.createTransfer(newTransfer);
        }
    }
@GetMapping("/list/{username}")
    public List<Transfer> getTransferList (@PathVariable String username){
        List<Transfer> transferList = transferDao.getListOfTransfers(username);
        return transferList;
    }


}
