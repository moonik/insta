package insta.project.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("api/messages/")
public class MessageController {

    @Autowired
    private MessageService messageService;

    /**
     * sends message to user
     * @param userName - receiver
     * @param messageDTO - object from front end
     * @return new message
     */
    @PostMapping("sendTo/{userName}")
    public Message sendMessage(@PathVariable("userName") String userName, @RequestBody MessageDTO messageDTO){
        return messageService.sendMessage(userName, messageDTO);
    }

    /**
     * gets conversations with users
     * @return logged user's conversations
     */
    @GetMapping("myDialogs")
    public List<Message> converstations() {
        return messageService.converstations();
    }

    /**
     * checks if there are new conversations
     * @param lastMessage - last message from user
     * @return new messages
     */
    @PostMapping("updateConversations")
    public List<Message> getNewMessages(@RequestBody Message lastMessage) {
        return messageService.getNewMessages(lastMessage);
    }

    /**
     * gets messages from user
     * @param userName - user that you are in conversation
     * @return new messages
     */
    @GetMapping("getMessages/{userName}")
    public List<Message> getMessages(@PathVariable("userName") String userName) {
        return messageService.getMessages(userName);
    }

    /**
     * check if there are new messages from user that you are in conversation
     * @param userName - sender
     * @param lastMessage - last message from sender
     * @return new messages
     */
    @PostMapping("updateMessages/{userName}")
    public List<Message> getNewMessages(@PathVariable("userName") String userName, @RequestBody Message lastMessage) {
        return messageService.getNewMessages(userName, lastMessage);
    }

}
