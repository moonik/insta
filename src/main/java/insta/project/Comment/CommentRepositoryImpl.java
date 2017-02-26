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

    @Override
    public Comment findNewComments(Long id, Comment lastComment) {
        Query query = em.createQuery("SELECT c from Comment c where c.picture_id=?1 ORDER BY id DESC");
        query.setParameter(1, id);

        List<Comment> newComment = query.getResultList();

        return newComment.get(0);
    }

    @Override
    public Comment findLastComment(Long id) {
        Query query = em.createQuery("SELECT c from Comment c where c.picture_id=?1 ORDER BY id DESC");
        query.setParameter(1, id);

        List<Comment> newComment = query.getResultList();

        return newComment.get(0);
    }

    public Boolean checkIfNewComment(Long id, Comment lastComment) {
        Query query = em.createQuery("SELECT c from Comment c where c.picture_id=?1 ORDER BY id DESC");
        query.setParameter(1, id);

        List<Comment> newComment = query.getResultList();

        if(newComment.get(0).getId().equals(lastComment.getId()))
        {
            return false;
        }

        return true;
    }


}
