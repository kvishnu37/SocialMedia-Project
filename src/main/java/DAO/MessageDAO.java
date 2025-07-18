package DAO;
import Model.Message;
import Util.ConnectionUtil;
import java.sql.*;  
import java.util.ArrayList;  
import java.util.List;

public class MessageDAO {
    public List<Message> getAllMessages() {  
        Connection conn = ConnectionUtil.getConnection();  
        List<Message> messages = new ArrayList<>();  
        try {  
          String sql = "SELECT message_id, posted_by, message_text, time_posted_epoch FROM Message";  
          PreparedStatement ps = conn.prepareStatement(sql);  
          ResultSet rs = ps.executeQuery();  
          while (rs.next()) {  
             Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));  
             messages.add(message);  
          }  
        } catch (SQLException e) {  
          System.out.println(e.getMessage());  
        }  
        return messages;  
     }  
    
     public Message insertMessage(Message message) {  
        Connection conn = ConnectionUtil.getConnection();  
        try {  
          String sql = "INSERT INTO Message (posted_by, message_text, time_posted_epoch) VALUES (?, ?, ?)";  
          PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);  
          ps.setInt(1, message.getPosted_by());  
          ps.setString(2, message.getMessage_text());  
          ps.setLong(3, message.getTime_posted_epoch());  
          ps.executeUpdate();  
          ResultSet pkeyResultSet = ps.getGeneratedKeys();  
          if (pkeyResultSet.next()) {  
             int generated_message_id = (int) pkeyResultSet.getLong(1);  
             return new Message(generated_message_id, message.getPosted_by(), message.getMessage_text(), message.getTime_posted_epoch());  
          }  
        } catch (SQLException e) {  
          System.out.println(e.getMessage());  
        }  
        return null;  
     }  
    
     public Message getMessageById(int messageId) {  
        Connection conn = ConnectionUtil.getConnection();  
        try {  
          String sql = "SELECT message_id, posted_by, message_text, time_posted_epoch FROM Message WHERE message_id = ?";  
          PreparedStatement ps = conn.prepareStatement(sql);  
          ps.setInt(1, messageId);  
          ResultSet rs = ps.executeQuery();  
          if (rs.next()) {  
             return new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));  
          }  
        } catch (SQLException e) {  
          System.out.println(e.getMessage());  
        }  
        return null;  
     }  
    
     public Message updateMessage(int messageId, Message updatedMessage) {  
        Connection conn = ConnectionUtil.getConnection();  
        try {  
          String sql = "UPDATE Message SET message_text = ? WHERE message_id = ?";  
          PreparedStatement ps = conn.prepareStatement(sql);  
          ps.setString(1, updatedMessage.getMessage_text());  
          ps.setInt(2, messageId);  
          ps.executeUpdate();  
          return getMessageById(messageId);  
        } catch (SQLException e) {  
          System.out.println(e.getMessage());  
        }  
        return null;  
     }  
    
     public Message deleteMessage(int messageId) {  
      Connection conn = ConnectionUtil.getConnection();  
      try {  
         Message message = getMessageById(messageId);  
         if (message != null) {  
           String sql = "DELETE FROM Message WHERE message_id = ?";  
           PreparedStatement ps = conn.prepareStatement(sql);  
           ps.setInt(1, messageId);  
           ps.executeUpdate();  
           return message;  
         }  
      } catch (SQLException e) {  
         System.out.println(e.getMessage());  
      }  
      return null;  
   }  
    
     public List<Message> getMessagesByAccount(int accountId) {  
        Connection conn = ConnectionUtil.getConnection();  
        List<Message> messages = new ArrayList<>();  
        try {  
          String sql = "SELECT message_id, posted_by, message_text, time_posted_epoch FROM Message WHERE posted_by = ?";  
          PreparedStatement ps = conn.prepareStatement(sql);  
          ps.setInt(1, accountId);  
          ResultSet rs = ps.executeQuery();  
          while (rs.next()) {  
             Message message = new Message(rs.getInt("message_id"), rs.getInt("posted_by"), rs.getString("message_text"), rs.getLong("time_posted_epoch"));  
             messages.add(message);  
          }  
        } catch (SQLException e) {  
          System.out.println(e.getMessage());  
        }  
        return messages;  
     }  
    
}
