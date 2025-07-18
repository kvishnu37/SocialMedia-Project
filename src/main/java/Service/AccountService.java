package Service;
import Model.Account;
import DAO.AccountDAO;

public class AccountService {
   private AccountDAO accountDAO;  
  
   public AccountService() {  
      this.accountDAO = new AccountDAO();  
   }  
  
   public Account addAccount(Account account) {  
      return accountDAO.insertAccount(account);  
   }  
  
   public Account getAccountByUsername(String username) {  
      return accountDAO.getAccountByUsername(username);  
   }  
  
   public Account getAccountByUsernameAndPassword(String username, String password) {  
      return accountDAO.getAccountByUsernameAndPassword(username, password);  
   }  
  
   public Account getAccountById(int accountId) {  
      return accountDAO.getAccountById(accountId);  
   }  
}
