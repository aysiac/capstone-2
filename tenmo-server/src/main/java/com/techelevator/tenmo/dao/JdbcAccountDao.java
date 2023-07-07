package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

@Component
public class JdbcAccountDao implements AccountDao{
    JdbcTemplate jdbcTemplate ;

    public JdbcAccountDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Account getAccountByUserId(int userId){
        Account userAccount = null;
        String query = "SELECT account_id, user_id, balance FROM account WHERE user_id =?;";
       try{
           SqlRowSet results = jdbcTemplate.queryForRowSet(query,Account.class,userId);
           if(results.next()){
               userAccount = mapToAccount(results);
           }
       }catch (DataAccessException ex){
           //TODO
       }
        return userAccount;
    }

    public Account getAccountByUserName(String userName){
        Account userAccount = null;
        String query = "SELECT account_id, user_id, balance FROM account WHERE user_id =" +
                "(SELECT user_id FROM tenmo_user WHERE username =?);";
        try{
            SqlRowSet results = jdbcTemplate.queryForRowSet(query,userName);
            if(results.next()){
                userAccount = mapToAccount(results);
            }
        }catch (DataAccessException ex){
            //TODO
        }
       
        return userAccount;
    }

    public boolean createAccount(int userId){
        boolean isAccountCreated = false;
        Account newAccount = new Account();
        String query = "Insert INTO account(user_id, balance) values(?,?);";
        try {
            jdbcTemplate.update(query, userId, newAccount.getBalance());
            isAccountCreated = true;
        } catch (DataAccessException exception){
            isAccountCreated = false;
        }
        return isAccountCreated;
    }

    private Account mapToAccount(SqlRowSet rowSet){
        Account account = new Account();
        account.setAccountId(rowSet.getInt("account_id"));
        account.setUserId(rowSet.getInt("user_id"));
        account.setBalance(rowSet.getDouble("balance"));
        return account;
    }
}
