package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.util.List;

public interface AccountDao {
    
    public Account getAccountByUserId(int userId);

    public Account getAccountByUserName(String userName);
    
    public boolean createAccount(int userId);
}
