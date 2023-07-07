package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
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

    public AccountBalanceDTO getBalance(String username){
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
        return balanceDTO;
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
    
    public void makeTransfer(int fromAccountId, int toAccountId, double amount){
        String fromQuery = "UPDATE account SET balance = (balance-?) WHERE account_id=?;";
        String toQuery = "UPDATE account SET balance = (balance +?) WHERE account_id=?;";
        try {
            jdbcTemplate.update(fromQuery, amount, fromAccountId);
            jdbcTemplate.update(toQuery, amount, toAccountId);
        } catch (DataAccessException ex){
            //TODO
        }
        
    }

    public void createTransfer(Transfer newTransfer){
        String query = "INSERT INTO transfer(transfer_status_id,transfer_type_id, from_account, to_account,transfer_amount)" +
                "VALUES(?,?,?,?,?);";
        try
        {
            jdbcTemplate.update(query,newTransfer.getTransferStatusId(),newTransfer.getTransferTypeId(),
                    newTransfer.getFromAccount(),newTransfer.getToAccount(),newTransfer.getTransferAmount());
        }catch (DataAccessException ex){
            //TODO
        }

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

    public int getStatusByName(String statusName){
        Integer statusId;
       String query = "SELECT transfer_status_id FROM transfer_status WHERE transfer_status_name =?;";
        statusId = jdbcTemplate.queryForObject(query, Integer.class,statusName);
       if(statusId == null){
           statusId = -1;
       }
        return statusId;
    }

    public int getTransferType(String typeName){
        Integer typeId;
        String query = "SELECT transfer_type_id FROM transfer_type WHERE transfer_type_name =?;";
        typeId = jdbcTemplate.queryForObject(query, Integer.class,typeName);
        if(typeId == null){
            typeId = -1;
        }
        return typeId;
    }

    private User mapToUser(SqlRowSet rowSet){
        User user = new User();
        user.setId(rowSet.getLong("user_id"));
        user.setUsername(rowSet.getString("username"));
        return user;
    }
    
}
