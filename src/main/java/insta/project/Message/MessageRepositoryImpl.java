package insta.project.Message;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

public class MessageRepositoryImpl implements MessageRepo{

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Message> findMyConverstations(String currentUser) {

        Query query = em.createQuery("SELECT DISTINCT m from Message m where m.receiver=?1 ORDER BY id DESC");
        query.setParameter(1, currentUser);

        return query.getResultList();


    }

    @Override
    public List<Message> findMyMessagesWithUser(String receiver, String sender) {
        Query query = em.createQuery("SELECT m from Message m where (m.receiver=?1 OR m.sender=?1) AND (m.sender=?2 OR m.receiver=?2) ORDER BY id ASC");
        query.setParameter(1, receiver);
        query.setParameter(2, sender);

        return query.getResultList();
    }

    @Override
    public List<Message> findNewMessages(String currentUser, Long messageId) {
        Query query = em.createQuery("SELECT m FROM Message m where m.receiver=?1 AND m.id>?2 ORDER BY id DESC");
        query.setParameter(1, currentUser);
        query.setParameter(2, messageId);

        return query.getResultList();
    }

    @Override
    public List<Message> findMyNewMessagesWithUser(String receiver, String sender, Long messageId) {
        Query query = em.createQuery("SELECT m FROM Message m where (m.receiver=?1 OR m.sender=?1) AND (m.id>?2 AND m.sender=?3 OR m.receiver=?3) ORDER BY id ASC");
        query.setParameter(1, receiver);
        query.setParameter(2, messageId);
        query.setParameter(3, sender);


        return query.getResultList();
    }

    @Override
    public Boolean checkIfNewMessage(String currentUser, Long messageId) {
        Query query = em.createQuery("SELECT m FROM Message m where m.receiver=?1 ORDER BY id DESC");
        query.setParameter(1, currentUser);

        List<Message> newMessages = query.getResultList();

        if(newMessages.get(0).getId().equals(messageId))
        {
            return false;
        }

        return true;
    }

    @Override
    public Boolean checkIfNewMessages(String receiver, String sender, Long messageId) {
        Query query = em.createQuery("SELECT m FROM Message m where (m.receiver=?1 OR m.sender=?1) AND (m.sender=?2 OR m.sender=?2) ORDER BY id DESC");
        query.setParameter(1, receiver);
        query.setParameter(2, sender);

        List<Message> newMessages = query.getResultList();

        if(newMessages.get(0).getId().equals(messageId))
        {
            return false;
        }

        return true;
    }
}
