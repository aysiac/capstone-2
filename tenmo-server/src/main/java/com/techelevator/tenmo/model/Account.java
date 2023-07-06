package com.techelevator.tenmo.model;


public class Account {
    private int accountId;
    private double balance = 1000.00 ;

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
