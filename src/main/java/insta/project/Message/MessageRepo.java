package insta.project.Message;

import java.util.List;

public interface MessageRepo {

    List<Message> findMyConverstations(String currentUser);
    List<Message> findNewMessages(String currentUser, Long messageId);
    Boolean checkIfNewMessage(String currentUser, Long messageId);
}
