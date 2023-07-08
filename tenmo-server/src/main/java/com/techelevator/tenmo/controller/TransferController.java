package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDao;
import com.techelevator.tenmo.dao.TransferDao;
import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.*;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.security.Principal;
import java.util.List;

@PreAuthorize("isAuthenticated()")
@RestController
//@RequestMapping("/transfer")
public class TransferController {
    private TransferDao transferDao;
    private AccountDao accountDao;
    private UserDao userDao;

    public TransferController(TransferDao transferDao, AccountDao accountDao, UserDao userDao) {
        this.transferDao = transferDao;
        this.accountDao = accountDao;
        this.userDao = userDao;
    }

    @GetMapping("/user/balance")
    public AccountBalanceDTO getBalance(Principal principal) {
        AccountBalanceDTO balance = transferDao.getBalance(principal.getName());
        return balance;
    }

    @GetMapping("/view-users")
    public List<User> getListOfUsers(Principal principal) {
        List<User> userList = transferDao.getListOfUsers(principal.getName());
        return userList;
    }

    @PostMapping("/send-money")
    public Transfer makeTransfer(@Valid @RequestBody TransferDTO transferDTO, Principal principal) {
        boolean isTransferSuccess;
        Transfer newTransfer = null;
        if(transferDTO.getFromUserId() != transferDTO.getToUserId()) {
            Account fromAccount = accountDao.getAccountByUserId(transferDTO.getFromUserId());
            Account toAccount = accountDao.getAccountByUserId(transferDTO.getToUserId());
            int loggedInUserId = userDao.findIdByUsername(principal.getName());
            //Check if the logged in user is making the transfer
            if (transferDTO.getFromUserId() == loggedInUserId) {
                //checks if transfer amount is less than balance
                if (transferDTO.getAmount() < fromAccount.getBalance()) {
                    transferDao.makeTransfer(fromAccount.getAccountId(), toAccount.getAccountId(), transferDTO.getAmount());
                    isTransferSuccess = true;
                } else {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST," Insufficient balance.");
                }
                if (isTransferSuccess) {
                    newTransfer = setTransfer("Approved","Sent", fromAccount.getAccountId(),
                            toAccount.getAccountId(),transferDTO.getAmount());
//                    int statusId = transferDao.getStatusByName("Approved");
//                    int transferTypeId = transferDao.getTransferType("Sent");
//                    Transfer transfer = new Transfer();
//                    transfer.setFromAccount(fromAccount.getAccountId());
//                    transfer.setToAccount(toAccount.getAccountId());
//                    transfer.setTransferAmount(transferDTO.getAmount());
//                    transfer.setTransferStatusId(statusId);
//                    transfer.setTransferTypeId(transferTypeId);
//
//                    newTransfer = transferDao.createTransfer(transfer);
                }
            }
            else
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Not authorized to make a transfer.");
        }
        else
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "fromAccount and toAccount cannot be the same. Choose a different toAccount.");
        return newTransfer;
    }

    private Transfer setTransfer(String statusName, String transferType, int fromAccountId, int toAccountId,double amount){
        Transfer newTransfer = null;
        int statusId = transferDao.getStatusByName(statusName);
        int transferTypeId = transferDao.getTransferType(transferType);
        Transfer transfer = new Transfer();
        transfer.setFromAccount(fromAccountId);
        transfer.setToAccount(toAccountId);
        transfer.setTransferAmount(amount);
        transfer.setTransferStatusId(statusId);
        transfer.setTransferTypeId(transferTypeId);

        newTransfer = transferDao.createTransfer(transfer);

        return newTransfer;
    }
    @GetMapping("/transfer/view-all")
    public List<Transfer> getTransferList(Principal principal) {
        List<Transfer> transferList = transferDao.getListOfTransfers(principal.getName());
        return transferList;
    }

    @GetMapping("/transfer/detail/{transferId}")
    public Transfer getTransferDetails(@PathVariable int transferId, Principal principal){
        Transfer searchTransfer = transferDao.getTransferDetails(transferId);
        return searchTransfer;
    }
}
