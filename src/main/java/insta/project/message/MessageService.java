package insta.project.message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private MessageRepo messageRepo;

    /**
     * saves message to data base
     * @param messageDTO - object from controller
     * @return saves message
     */
    public Message sendMessage(String userName, MessageDTO messageDTO){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = auth.getName();
        Message message = new Message (currentUser, userName, messageDTO.getText_message(), new Date());
        return messageRepository.save(message);
    }

    public List<Message> converstations() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = auth.getName();
        return messageRepository.findMyConverstations(currentUser);
    }

    public List<Message> getNewMessages(Message lastMessage) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = auth.getName();

        if(messageRepo.checkIfNewMessage(currentUser, lastMessage.getId())) {
            //return new ResponseEntity<List<message>>(HttpStatus.NOT_FOUND);
            return Collections.emptyList();
        }
        //return new ResponseEntity<List<message>>(messageRepo.findNewMessages(currentUser, lastMessage.getId()), HttpStatus.OK);
        return messageRepository.findNewMessages(currentUser, lastMessage.getId());
    }

    public List<Message> getMessages(String userName) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = auth.getName();
        return messageRepository.findMyMessagesWithUser(currentUser, userName);
    }

    public List<Message> getNewMessages(String userName, Message lastMessage) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String currentUser = auth.getName();

        if(messageRepo.checkIfNewMessages(userName, currentUser, lastMessage.getId()))
        {
            //return new ResponseEntity<List<message>>(HttpStatus.NOT_FOUND);
            return Collections.emptyList();
        }
        //return new ResponseEntity<List<message>>(messageRepo.findMyNewMessagesWithUser(userName, currentUser, lastMessage.getId()), HttpStatus.OK);
        return messageRepository.findMyNewMessagesWithUser(userName, currentUser, lastMessage.getId());
    }
}
