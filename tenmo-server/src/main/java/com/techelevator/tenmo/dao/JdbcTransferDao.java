package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.AccountBalanceDTO;
import com.techelevator.tenmo.model.Transfer;
import com.techelevator.tenmo.model.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcTransferDao  implements TransferDao{
    JdbcTemplate jdbcTemplate ;

    public JdbcTransferDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public double getBalance(String username){
        AccountBalanceDTO balanceDTO = new AccountBalanceDTO();
        SqlRowSet results;

        double balance=0.00;
        String query = "SELECT balance FROM account WHERE user_id = " +
                "(SELECT user_id FROM tenmo_user WHERE username =?);";
        try {
           //balanceDTO = jdbcTemplate.queryForObject(query,AccountBalanceDTO.class,username);
            results = jdbcTemplate.queryForRowSet(query, username);
            if(results.next()){

                balance = results.getDouble("balance");
            }
        }catch (DataAccessException e){

        }
        return balanceDTO.getBalance();
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
