package insta.project.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("sendTo/{userName}")
    public Message sendMessage(@PathVariable("userName") String userName, @RequestBody MessageDTO messageDTO){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = auth.getName();

        Date date = new Date();

        messageDTO.setReceiver(userName);
        messageDTO.setSender(currentUser);
        messageDTO.setDate(date);
        
        return messageService.sendMessage(messageDTO);
    }

    @GetMapping("myDialogs")
    public List<Message> converstations()
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = auth.getName();

        return messageRepo.findMyConverstations(currentUser);
    }

    @PostMapping("updateConversations")
    public ResponseEntity<List<Message>> getNewMessages(@RequestBody Message lastMessage)
    {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = auth.getName();

        if(!(messageRepo.checkIfNewMessage(currentUser, lastMessage.getId())))
        {
            return new ResponseEntity<List<Message>>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<List<Message>>(messageRepo.findNewMessages(currentUser, lastMessage.getId()), HttpStatus.OK);
    }

}
