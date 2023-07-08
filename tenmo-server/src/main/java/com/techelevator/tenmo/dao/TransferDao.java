package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.AccountBalanceDTO;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;

import java.util.List;

public interface TransferDao {

    public AccountBalanceDTO getBalance(String username);
    public List<User> getListOfUsers(String username);
    public void makeTransfer(int fromAccountId, int toAccountId, double amount);
    public Transfer createTransfer(Transfer newTransfer);
    public List<Transfer> getListOfTransfers(String username);
    public Transfer getTransferDetails(int transferId);
    public int getStatusByName(String statusName);
    public int getTransferType(String typeName);
}
