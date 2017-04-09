package insta.project.comment;

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

    @Override
    public List<Comment> findNewComments(Long id, Long commentId) {
        Query query = em.createQuery("SELECT c from Comment c where c.picture_id=?1 AND c.id>?2 ORDER BY id DESC");
        query.setParameter(1, id);
        query.setParameter(2, commentId);

        return query.getResultList();
    }

    public Boolean checkIfNewComment(Long id, Long commentId) {
        Query query = em.createQuery("SELECT c from Comment c where c.picture_id=?1 ORDER BY id DESC");
        query.setParameter(1, id);

        List<Comment> newComment = query.getResultList();

        return newComment.get(0).getId().equals(commentId);
    }
}
