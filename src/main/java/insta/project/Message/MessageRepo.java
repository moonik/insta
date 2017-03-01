package insta.project.Message;

import java.util.List;

public interface MessageRepo {

    List<Message> findMyConverstations(String currentUser);

    List<Message> findMyMessagesWithUser(String receiver, String sender);

    List<Message> findNewMessages(String currentUser, Long messageId);

    List<Message> findMyNewMessagesWithUser(String receiver, String sender, Long messageId);

    Boolean checkIfNewMessage(String currentUser, Long messageId);

    Boolean checkIfNewMessages(String receiver, String sender, Long messageId);
}
