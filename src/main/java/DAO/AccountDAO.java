package DAO;

import Model.Account;
import Util.ConnectionUtil;
import java.sql.*;  
import java.util.ArrayList;  
import java.util.List;


public class AccountDAO {
    public List<Account> getAllAccounts() {  
        Connection conn = ConnectionUtil.getConnection();  
        List<Account> accounts = new ArrayList<>();  
        try {  
          String sql = "SELECT account_id, username, password FROM Account";  
          PreparedStatement preparedStatement = conn.prepareStatement(sql);  
          ResultSet rs = preparedStatement.executeQuery();  
          while (rs.next()) {  
             Account account = new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));  
             accounts.add(account);  
          }  
        } catch (SQLException e) {  
          System.out.println(e.getMessage());  
        }  
        return accounts;  
     }  
    
     public Account insertAccount(Account account) {  
        Connection conn = ConnectionUtil.getConnection();  
        try {  
          String sql = "INSERT INTO Account (username, password) VALUES (?, ?)";  
          PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);  
          ps.setString(1, account.getUsername());  
          ps.setString(2, account.getPassword());  
          ps.executeUpdate();  
          ResultSet pkeyResultSet = ps.getGeneratedKeys();  
          if (pkeyResultSet.next()) {  
             int generated_account_id = (int) pkeyResultSet.getLong(1);  
             return new Account(generated_account_id, account.getUsername(), account.getPassword());  
          }  
        } catch (SQLException e) {  
          System.out.println(e.getMessage());  
        }  
        return null;  
     }  
    
     public Account getAccountByUsername(String username) {  
        Connection conn = ConnectionUtil.getConnection();  
        try {  
          String sql = "SELECT account_id, username, password FROM Account WHERE username = ?";  
          PreparedStatement ps = conn.prepareStatement(sql);  
          ps.setString(1, username);  
          ResultSet rs = ps.executeQuery();  
          if (rs.next()) {  
             return new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));  
          }  
        } catch (SQLException e) {  
          System.out.println(e.getMessage());  
        }  
        return null;  
     }  
    
     public Account getAccountByUsernameAndPassword(String username, String password) {  
        Connection conn = ConnectionUtil.getConnection();  
        try {  
          String sql = "SELECT account_id, username, password FROM Account WHERE username = ? AND password = ?";  
          PreparedStatement ps = conn.prepareStatement(sql);  
          ps.setString(1, username);  
          ps.setString(2, password);  
          ResultSet rs = ps.executeQuery();  
          if (rs.next()) {  
             return new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));  
          }  
        } catch (SQLException e) {  
          System.out.println(e.getMessage());  
        }  
        return null;  
     }  

     public Account getAccountById(int accountId) {  
      Connection conn = ConnectionUtil.getConnection();  
      try {  
        String sql = "SELECT account_id, username, password FROM Account WHERE account_id = ?";  
        PreparedStatement ps = conn.prepareStatement(sql);  
        ps.setInt(1, accountId);  
        ResultSet rs = ps.executeQuery();  
        if (rs.next()) {  
           return new Account(rs.getInt("account_id"), rs.getString("username"), rs.getString("password"));  
        }  
      } catch (SQLException e) {  
        System.out.println(e.getMessage());  
      }  
      return null;  
   }  
}
