package insta.project.Comment;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

public class CommentRepositoryImpl implements CommentRepo{

    @PersistenceContext
    private EntityManager em;

    public List<Comment> findBypicture_id(Long id){

        Query query = em.createQuery("SELECT c from Comment c where c.picture_id=?1");
        query.setParameter(1, id);
        return query.getResultList();

    }

}