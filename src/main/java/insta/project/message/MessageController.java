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
    private MessageRepository messageRepository;

    @Autowired
    private MessageService messageService;

    @Autowired
    private MessageRepo messageRepo;

    /**
     * sends message to user
     * @param userName - receiver
     * @param messageDTO - object from front end
     * @return new message
     */
    @PostMapping("sendTo/{userName}")
    public Message sendMessage(@PathVariable("userName") String userName, @RequestBody MessageDTO messageDTO){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = auth.getName();

        messageDTO.setReceiver(userName);
        messageDTO.setSender(currentUser);
        messageDTO.setDate(new Date());
        
        return messageService.sendMessage(messageDTO);
    }

    /**
     * gets conversations with users
     * @return logged user's conversations
     */
    @GetMapping("myDialogs")
    public List<Message> converstations()
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = auth.getName();

        return messageRepo.findMyConverstations(currentUser);
    }

    /**
     * checks if there are new conversations
     * @param lastMessage - last message from user
     * @return new messages
     */
    @PostMapping("updateConversations")
    public List<Message> getNewMessages(@RequestBody Message lastMessage)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = auth.getName();

        if(messageRepo.checkIfNewMessage(currentUser, lastMessage.getId()))
        {
            //return new ResponseEntity<List<message>>(HttpStatus.NOT_FOUND);
            return Collections.emptyList();
        }
        //return new ResponseEntity<List<message>>(messageRepo.findNewMessages(currentUser, lastMessage.getId()), HttpStatus.OK);
        return messageRepo.findNewMessages(currentUser, lastMessage.getId());
    }

    /**
     * gets messages from user
     * @param userName - user that you are in conversation
     * @return new messages
     */
    @GetMapping("getMessages/{userName}")
    public List<Message> getMessages(@PathVariable("userName") String userName)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = auth.getName();

        return messageRepo.findMyMessagesWithUser(currentUser, userName);
    }

    /**
     * check if there are new messages from user that you are in conversation
     * @param userName - sender
     * @param lastMessage - last message from sender
     * @return new messages
     */
    @PostMapping("updateMessages/{userName}")
    public List<Message> getNewMessages(@PathVariable("userName") String userName, @RequestBody Message lastMessage)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = auth.getName();

        if(messageRepo.checkIfNewMessages(userName, currentUser, lastMessage.getId()))
        {
            //return new ResponseEntity<List<message>>(HttpStatus.NOT_FOUND);
            return Collections.emptyList();
        }
        //return new ResponseEntity<List<message>>(messageRepo.findMyNewMessagesWithUser(userName, currentUser, lastMessage.getId()), HttpStatus.OK);
        return messageRepo.findMyNewMessagesWithUser(userName, currentUser, lastMessage.getId());
    }

}
