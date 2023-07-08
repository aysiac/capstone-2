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
import java.util.ArrayList;
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
        if (transferDTO.getFromUserId() != transferDTO.getToUserId()) {
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
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, " Insufficient balance.");
                }
                if (isTransferSuccess) {
                    newTransfer = setTransfer("Approved", "Sent", fromAccount.getAccountId(),
                            toAccount.getAccountId(), transferDTO.getAmount());
                }
            } else
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Not authorized to make a transfer.");
        } else
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "fromAccount and toAccount cannot be the same. Choose a different toAccount.");
        return newTransfer;
    }

    private Transfer setTransfer(String statusName, String transferType, int fromAccountId, int toAccountId, double amount) {
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
    public Transfer getTransferDetails(@PathVariable int transferId, Principal principal) {
        Transfer searchTransfer = transferDao.getTransferDetails(transferId);
        return searchTransfer;
    }

    @PostMapping("/request-transfer")
    public Transfer requestTransfer(@Valid @RequestBody TransferDTO transferDto, Principal principal) {
        Transfer requestTransfer = null;
        if (transferDto.getFromUserId() != transferDto.getToUserId()) {
            Account fromAccount = accountDao.getAccountByUserId(transferDto.getFromUserId());
            Account toAccount = accountDao.getAccountByUserId(transferDto.getToUserId());
            if(transferDto.getAmount() > 0) {
                requestTransfer = setTransfer("Pending", transferDto.getTransferType(), fromAccount.getAccountId(), toAccount.getAccountId(), transferDto.getAmount());
            }
            else
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, " Please enter a valid amount.");
        }
        else
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "fromAccount and toAccount cannot be the same. Choose a different toAccount.");
        return requestTransfer;

    }

    @GetMapping("/pending-request")
    public List<Transfer> pendingRequest(Principal principal) {
        List<Transfer> pendingRequest = new ArrayList<>();
        Account logInUserAccount = accountDao.getAccountByUserName(principal.getName());
        pendingRequest = transferDao.getListOfPendingTransfer(logInUserAccount.getAccountId());

        return pendingRequest;
    }
    @PostMapping("/action-request")
    public Transfer actionRequest(@RequestBody  ActionDTO actionDTO, Principal principal){
        Transfer transfer = null;
        transfer = transferDao.actionRequest(actionDTO.getTransferId(), actionDTO.getActionName());

        return transfer;
    }
}
