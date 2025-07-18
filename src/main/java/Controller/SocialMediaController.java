package Controller;

import Service.MessageService;
import Service.AccountService;
import Model.Account;
import Model.Message;
import com.fasterxml.jackson.core.JsonProcessingException;  
import com.fasterxml.jackson.databind.ObjectMapper; 
import io.javalin.Javalin;
import io.javalin.http.Context;
import java.util.List;  


/**
 * TODO: You will need to write your own endpoints and handlers for your controller. The endpoints you will need can be
 * found in readme.md as well as the test cases. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
public class SocialMediaController {
    /**
     * In order for the test cases to work, you will need to write the endpoints in the startAPI() method, as the test
     * suite must receive a Javalin object from this method.
     * @return a Javalin app object which defines the behavior of the Javalin controller.
     */
    private AccountService accountService;  
    private MessageService messageService;  
  
   public SocialMediaController() {  
      this.accountService = new AccountService();  
      this.messageService = new MessageService();  
   }  
  
   
    public Javalin startAPI() {
        Javalin app = Javalin.create();
        app.post("/register", this::registerHandler);  
        app.post("/login", this::loginHandler);  
        app.post("/messages", this::createMessageHandler);  
        app.get("/messages", this::getAllMessagesHandler);  
        app.get("/messages/{message_id}", this::getMessageByIdHandler);  
        app.delete("/messages/{message_id}", this::deleteMessageHandler);  
        app.patch("/messages/{message_id}", this::updateMessageHandler);  
        app.get("/accounts/{account_id}/messages", this::getMessagesByAccountHandler);  
        
        return app;
    }
    
    private void registerHandler(Context ctx) throws JsonProcessingException {  
      ObjectMapper mapper = new ObjectMapper();  
      Account account = mapper.readValue(ctx.body(), Account.class);  
      if (account.getUsername() == null || account.getUsername().isEmpty() || account.getPassword() == null || account.getPassword().length() < 4) {  
        ctx.status(400);  
        return;  
      }  
      Account existingAccount = accountService.getAccountByUsername(account.getUsername());  
      if (existingAccount != null) {  
        ctx.status(400);  
        return;  
      }  
      Account addedAccount = accountService.addAccount(account);  
      if (addedAccount != null) {  
        ctx.json(mapper.writeValueAsString(addedAccount));  
      } else {  
        ctx.status(400);  //if username already exists
      }  
   }  
  
   private void loginHandler(Context ctx) throws JsonProcessingException {  
      ObjectMapper mapper = new ObjectMapper();  
      Account account = mapper.readValue(ctx.body(), Account.class);  
      Account existingAccount = accountService.getAccountByUsernameAndPassword(account.getUsername(), account.getPassword());  
      if (existingAccount != null) {  
        ctx.json(mapper.writeValueAsString(existingAccount));  
      } else {  
        ctx.status(401);  
      }  
   }  
  
   private void createMessageHandler(Context ctx) throws JsonProcessingException {  
    ObjectMapper mapper = new ObjectMapper();  
    Message message = mapper.readValue(ctx.body(), Message.class);  
    if (message.getMessage_text() == null || message.getMessage_text().isEmpty() || message.getMessage_text().length() > 255) {  
       ctx.status(400);  
       return;  
    }  
    Account existingAccount = accountService.getAccountById(message.getPosted_by());  
    if (existingAccount == null) {  
       ctx.status(400);  
       return;  
    }  
    Message addedMessage = messageService.addMessage(message);  
    if (addedMessage != null) {  
       ctx.json(mapper.writeValueAsString(addedMessage));  
    } else {  
       ctx.status(400);  
    }  
 }
  
   private void getAllMessagesHandler(Context ctx) {  
      List<Message> messages = messageService.getAllMessages();  
      ctx.json(messages);  
   }  
  
   private void getMessageByIdHandler(Context ctx) {  
      int messageId = Integer.parseInt(ctx.pathParam("message_id"));  
      Message message = messageService.getMessageById(messageId);  
      if (message != null) {  
        ctx.json(message);  
      } else {  
        ctx.json("");  
      }  
   }  
  
   private void deleteMessageHandler(Context ctx) {  
    int messageId = Integer.parseInt(ctx.pathParam("message_id"));  
    Message deletedMessage = messageService.deleteMessage(messageId);  
    if (deletedMessage != null) {  
       ctx.json(deletedMessage);  
    } else {  
       ctx.result("");  
    }  
    ctx.status(200);  
 }
  
   private void updateMessageHandler(Context ctx) throws JsonProcessingException {  
      int messageId = Integer.parseInt(ctx.pathParam("message_id"));  
      ObjectMapper mapper = new ObjectMapper();  
      Message updatedMessage = mapper.readValue(ctx.body(), Message.class);  
      if (updatedMessage.getMessage_text() == null || updatedMessage.getMessage_text().isEmpty() || updatedMessage.getMessage_text().length() > 255) {  
        ctx.status(400);  
        return;  
      }  
      Message existingMessage = messageService.getMessageById(messageId);  
      if (existingMessage == null) {  
        ctx.status(400);  
        return;  
      }  
      Message updatedExistingMessage = messageService.updateMessage(messageId, updatedMessage);  
      if (updatedExistingMessage != null) {  
        ctx.json(mapper.writeValueAsString(updatedExistingMessage));  
      } else {  
        ctx.status(400);  
      }  
   }  
  
   private void getMessagesByAccountHandler(Context ctx) {  
      int accountId = Integer.parseInt(ctx.pathParam("account_id"));  
      List<Message> messages = messageService.getMessagesByAccount(accountId);  
      ctx.json(messages);  
   }  
   
}