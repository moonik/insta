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
}
