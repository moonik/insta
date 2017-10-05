package insta.project.comment;

import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class CommentRepositoryImpl implements CommentRepo{

    @PersistenceContext
    private EntityManager em;

    public Boolean checkIfNewComment(Long id, Long commentId) {
        Query query = em.createQuery("SELECT c from Comment c where c.picture_id=?1 ORDER BY id DESC");
        query.setParameter(1, id);
        List<Comment> newComment = query.getResultList();
        return newComment.get(0).getId().equals(commentId);
    }
}
