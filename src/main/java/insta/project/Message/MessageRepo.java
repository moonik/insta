package insta.project.Message;

import java.util.List;

public interface MessageRepo {

    /**
     * searches conversations for logged user
     * @param currentUser - logged user
     * @return conversations for logged user
     */
    List<Message> findMyConverstations(String currentUser);

    /**
     * searches messages for logged user from other user
     * @param receiver - user that gets message
     * @param sender - user that sent message
     * @return messages
     */
    List<Message> findMyMessagesWithUser(String receiver, String sender);

    /**
     * searches new conversations with users
     * @param currentUser - logged user
     * @param messageId - last message id
     * @return new messages
     */
    List<Message> findNewMessages(String currentUser, Long messageId);

    /**
     *searches new messages with user
     * @param receiver - user that receives messages
     * @param sender - user that sends messages
     * @param messageId - last message id
     * @return new messages
     */
    List<Message> findMyNewMessagesWithUser(String receiver, String sender, Long messageId);

    /**
     * checks if there are new conversations
     * @param currentUser - logged user
     * @param messageId - last conversation
     * @return new conversations
     */
    Boolean checkIfNewMessage(String currentUser, Long messageId);

    /**
     * checks if there are new messages with user
     * @param receiver
     * @param sender
     * @param messageId - last message id
     * @return
     */
    Boolean checkIfNewMessages(String receiver, String sender, Long messageId);
}
