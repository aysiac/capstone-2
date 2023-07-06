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
        String query = "SELECT balance FROM account WHERE user_id = " +
                "(SELECT user_id FROM tenmo_user WHERE username =?);";
        try {
           //balanceDTO = jdbcTemplate.queryForObject(query,AccountBalanceDTO.class,username);
            results = jdbcTemplate.queryForRowSet(query, username);
            if(results.next()){
                balanceDTO.setBalance(results.getDouble("balance"));
            }
        }catch (DataAccessException e){

        }
        return balanceDTO.getBalance();
    }
    public List<User> getListOfUsers(String username){
        List<User> userList = new ArrayList<>();
        String query = "SELECT user_id,username FROM tenmo_user WHERE username<>?;";
        SqlRowSet results ;
        try{
            results = jdbcTemplate.queryForRowSet(query, username);
            while (results.next()){
               userList.add(mapToUser(results));
            }
        }catch (DataAccessException ex){
            //TODO
        }

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
    private User mapToUser(SqlRowSet rowSet){
        User user = new User();
        user.setId(rowSet.getLong("user_id"));
        user.setUsername(rowSet.getString("username"));
        return user;
    }
    
}
