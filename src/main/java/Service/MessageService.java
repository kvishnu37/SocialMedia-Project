package Service;
import DAO.MessageDAO;
import Model.Message;
import java.util.List;

public class MessageService {
   private MessageDAO messageDAO;  
  
   public MessageService() {  
      this.messageDAO = new MessageDAO();  
   }  
  
   public Message addMessage(Message message) {  
      return messageDAO.insertMessage(message);  
   }  
  
   public List<Message> getAllMessages() {  
      return messageDAO.getAllMessages();  
   }  
  
   public Message getMessageById(int messageId) {  
      return messageDAO.getMessageById(messageId);  
   }  
  
   public Message updateMessage(int messageId, Message updatedMessage) {  
      return messageDAO.updateMessage(messageId, updatedMessage);  
   }  
  
   public Message deleteMessage(int messageId) {  
      return messageDAO.deleteMessage(messageId);  
   }  
  
   public List<Message> getMessagesByAccount(int accountId) {  
      return messageDAO.getMessagesByAccount(accountId);  
   }  
}
