package insta.project.Message;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {

    @Autowired
    private MessageRepository messageRepository;

    public Message sendMessage(MessageDTO messageDTO)
    {
        Message message = new Message (messageDTO.getSender(), messageDTO.getReceiver(), messageDTO.getText_message(), messageDTO.getDate());

        return messageRepository.save(message);
    }

}
