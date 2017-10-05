package insta.project.message;

public interface MessageRepo {
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
