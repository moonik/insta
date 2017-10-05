package insta.project.message;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

    @Query("SELECT DISTINCT m from Message m where m.receiver=?1 ORDER BY id DESC")
    List<Message> findMyConverstations(String currentUser);

    @Query("SELECT m from Message m where (m.receiver=?1 OR m.sender=?1) AND (m.sender=?2 OR m.receiver=?2) ORDER BY id ASC")
    List<Message> findMyMessagesWithUser(String receiver, String sender);

    @Query("SELECT m FROM Message m where m.receiver=?1 AND m.id>?2 ORDER BY id DESC")
    List<Message> findNewMessages(String currentUser, Long messageId);

    @Query("SELECT m FROM Message m where (m.receiver=?1 OR m.receiver=?3) AND (m.sender=?1 OR m.sender=?3) AND m.id>?2 ORDER BY id ASC")
    List<Message> findMyNewMessagesWithUser(String receiver, String sender, Long messageId);
}
