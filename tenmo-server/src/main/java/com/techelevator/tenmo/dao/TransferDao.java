package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;

import java.util.List;

public interface TransferDao {

    public double getBalance(String username);
    public List<User> getListOfUsers();
    public void makeTransfer(Transfer transfer);
    public List<Transfer> getListOfTransfers(String username);
    public Transfer getTransferDetails(String transferId);
}
