package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.ArrayList;
import java.util.List;

public class JdbcTransferDao  implements TransferDao{
    JdbcTemplate jdbcTemplate = new JdbcTemplate();
    public double getBalance(String username){
        double balance=0.00;
        
        String query = "SELECT balance FROM account WHERE user_id = " +
                "(SELECT user_id FROM tenmo_user WHERE username ILIKE ?; );";
        try {
            balance = jdbcTemplate.queryForObject(query, double.class, username);
        }catch (DataAccessException e){
           balance = 0;
        }
        return balance;
    }
    public List<User> getListOfUsers(){
        List<User> userList = new ArrayList<>();
        //TODO
        return userList;
    }
    public void makeTransfer(Transfer transfer){
        //TODO
    }
    public List<Transfer> getListOfTransfers(String username){
        List<Transfer> transferList = new ArrayList<>();
        //TODO
        return transferList;
    }
    public Transfer getTransferDetails(String transferId){
        Transfer transfer = null;
        //TODO
        return transfer;
    }
    
    
}
