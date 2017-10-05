package insta.project.message;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class MessageRepositoryImpl implements MessageRepo{

    @PersistenceContext
    private EntityManager em;

    @Override
    public Boolean checkIfNewMessage(String currentUser, Long messageId) {
        Query query = em.createQuery("SELECT m FROM Message m where m.receiver=?1 ORDER BY id DESC");
        query.setParameter(1, currentUser);
        List<Message> newMessages = query.getResultList();
        return newMessages.get(0).getId().equals(messageId);
    }

    @Override
    public Boolean checkIfNewMessages(String receiver, String sender, Long messageId) {
        Query query = em.createQuery("SELECT m FROM Message m where (m.receiver=?1 OR m.receiver=?2) AND (m.sender=?1 OR m.sender=?2) ORDER BY id DESC");
        query.setParameter(1, receiver);
        query.setParameter(2, sender);
        List<Message> newMessages = query.getResultList();
        return newMessages.get(0).getId().equals(messageId);
    }
}
