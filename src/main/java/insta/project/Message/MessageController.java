package insta.project.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/messages/")
public class MessageController {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private MessageService messageService;

    @PostMapping("sendTo/{userName}")
    public Message sendMessage(@PathVariable("userName") String userName, @RequestBody MessageDTO messageDTO){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = auth.getName();

        messageDTO.setReceiver(userName);
        messageDTO.setSender(currentUser);

        return messageService.sendMessage(messageDTO);
    }
}
