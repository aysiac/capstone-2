package com.techelevator.tenmo.model;


import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;

public class Account {
    private int accountId;
    @Min(value = 0)
    private double balance = 100.00 ;
    private int userId;

    public int getAccountId(){
        return accountId;
    }

    public void setAccountId(int accountId){
        this.accountId = accountId;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public Account(){
        
    }

    public Account(int accountId, double balance, int userId) {
        this.accountId = accountId;
        this.balance = balance;
        this.userId = userId;
    }
}
