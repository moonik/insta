package insta.project.like;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

public class PictureLikesRepositoryImpl implements PictureLikesRepo {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<PictureLikes> findBypicture_id(Long id) {

        Query query = em.createQuery("SELECT pl from PictureLikes pl where pl.picture_id=?1");
        query.setParameter(1, id);
        return query.getResultList();
    }

    @Override
    public Boolean findByPicId(Long id, String owner) {
        Query query = em.createQuery("SELECT pl from PictureLikes pl where pl.picture_id=?1 AND pl.owner=?2");
        query.setParameter(1, id);
        query.setParameter(2, owner);
        List<PictureLikes> pictureLikes = query.getResultList();
        if (pictureLikes.size() != 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public PictureLikes findByPicIdAndOwner(Long id, String owner) {

        Query query = em.createQuery("SELECT pl from PictureLikes pl where pl.picture_id=?1 AND pl.owner=?2");
        query.setParameter(1, id);
        query.setParameter(2, owner);
        List<PictureLikes> pictureLikes = query.getResultList();
        return pictureLikes.get(0);
    }


}
